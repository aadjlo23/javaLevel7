package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.Arrays;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * the object of this class is to create pixel matrix of picture basic on scene with 3D model
 */
public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * constant for the size of the ray sources moving for shading rays
     */
    private static final double DELTA = 0.1;
    /**
     * Build Render object with a scene and image writer
     *
     * @param imgWr Image Writer object
     * @param sc    scene object
     */
    public Render(ImageWriter imgWr, Scene sc) {
        _scene = sc;
        _imageWriter = imgWr;
    }

    /**
     * this function crat a image with improvements
     */

    public void renderImageAdvanced(boolean onOrOff ) {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        //Nx and Ny are the number of pixels in the rows and columns of the view plane
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        //width and height are the width and height of the image.
        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();

        double distance = _scene.getDistance();


        for (int row = 0; row < nY; ++row)
            for (int column = 0; column < nX; ++column) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, column, row, distance, width, height);
                GeoPoint closestPoint = findClosestIntersection(ray);
                if (onOrOff) {
                    List<Ray> rayList = camera.constructBeamThroughPixel(nX, nY, column, row, distance, width, height);
                    _imageWriter.writePixel(column, row, closestPoint == null ? background : averageColor(rayList).getColor());

                }
                else{

                }
            }
    }
    /**
     * this func create an image
     */
    public void renderImage() {
        Camera camera = _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        java.awt.Color background = _scene.getBackground().getColor();

        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();

        double width = _imageWriter.getWidth();
        double height = _imageWriter.getHeight();
        double distance = _scene.getDistance();

        Ray ray;
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j) {
                //creating a new ray for every pixel
                ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                //List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                GeoPoint closestPoint = findClosestIntersection(ray);
                // if no have intersection on this ray so paint background
                if (closestPoint == null)
                    _imageWriter.writePixel(j, i, background);
                else {
                    //GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint, ray).getColor());
                }
            }
    }

    /**
     * the function should find the intercetion most close
     *
     * @param ray ray
     * @return the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        GeoPoint closestPoint = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        Point3D ray_p0 = ray.getP0();
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
        if (intersections == null)
            return null;
        for (GeoPoint gp : intersections) {
            double distance = ray_p0.distance(gp.getPoint());
            if (distance < closestDistance) {
                closestPoint = gp;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }


    /**
     * function to draw a grid on our image by pixel
     * @param interval number that the pixels are multiple of this number, are part of the grid.
     */
    public void printGrid(int interval, java.awt.Color color) {
        int nY = _imageWriter.getNy();
        int nX = _imageWriter.getNx();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(j, i, color);
            }
        }
    }


    /**
     * Calc the color intensity in a intersection point
     *
     * @param geoPoint geo point
     * @param inRay    ray
     * @return the color
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay) {
        return calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }
    /**
     * Calculate the color intensity in a point with a phong model approach
     *
     * @return the color intensity
     */

    private Color calcColor(GeoPoint gp, Ray ray, int lvl, double k) {
        Color result = gp._geometry.getEmission(); // we add the emission light
        List<LightSource> lights = _scene.getLight();// list of the light
        Vector v = ray.getDirection();//vector formed between the camera and the point of the geometry
        Vector n = gp._geometry.getNormal(gp._point);
        Material material = gp._geometry.getMaterial();//material of the geo
        int nShininess = material.getNshininess();
        double kd = material.getKd();
        double ks = material.getKs();
        if (_scene.getLight() != null) {//if there is light in the scene
            for (LightSource lightSource : lights) {// we go over all the lights
                Vector l = lightSource.getL(gp._point);//vector that impact the geometry
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));
                if (nl * nv > 0) {
                    double ktr = transparency(lightSource, l, n, gp);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color iL = lightSource.getIntensity(gp._point).scale(ktr);
                        result = result.add(calcDiffusive(kd, l, n, iL),
                                calcSpecular(ks, l, n, v, nShininess, iL));
                    }
                }
            }
        }
        if (lvl == 1) return Color.BLACK; //not use recursive
        double kr = material.getKr(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay =  constructReflectedRay(gp._point, ray, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                result = result.add(calcColor(reflectedPoint, reflectedRay, lvl - 1, kkr).scale(kr));
        }
        double kt = material.getKt(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(n, gp, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                result = result.add(calcColor(refractedPoint, refractedRay, lvl - 1, kkt).scale(kt));
        }
        return result;
    }
    /**
     * this function calculate the level of transparency
     *
     * @param light lightsource
     * @param l  l
     * @param n  n
     * @param geopoint gp
     * @return the level
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint._point, lightDirection, n);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null) return 1.0;
        double lightDistance = light.getDistance(geopoint._point);
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp._point.distance(geopoint._point) - lightDistance) <= 0) {
                ktr *= gp._geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }


    /**
     *
     * @param kS factor reduces the specular light.
     * @param l direction vector from light source to intersection point on geometry.
     * @param n normal vector from geometry.
     * @param v direction vector
     * @param nShininess level of shininess (for calculate the specular light)
     * @param lightIntensity color of light from light source
     * @return specular light (color).
     */

    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {

        double p = nShininess;

        Vector r = l.add(n.scale(-2 *l.dotProduct(n))); // nl must not be zero!
        double minusVr = - alignZero(r.dotProduct(v));
        if (minusVr <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return lightIntensity.scale(kS* Math.pow(minusVr, p));
    }
    /**
     *this function calculate the diffusive ligh
     * @param kD the factor of the diffusive light
     * @param l the vector of the light source
     * @param n the normal vector to the object
     * @param lightIntensity the intensity of the light
     * @return the diffusive light
     */
    private Color calcDiffusive(double kD, Vector l,Vector n,  Color lightIntensity) {
        return lightIntensity.scale(kD * Math.abs(l.dotProduct(n)));
    }
    /**
    /**
     * Create the image file in jpeg format
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }
    /**
     * Returns reflected Ray
     *
     * @param point the point of the ray
     * @param ray the ray
     * @param n the vector
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Ray ray, Vector n){
        Vector v = ray.getDirection();
        Vector reflectedDirection = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(point, reflectedDirection, n);
    }
    /**
     * Calculates reflected color on point according to Phong model.
     * Calls for recursive helping function.
     *
     * @param geopoint
     * @param inRay
     * @return
     */
    private Color calcColorAdvanced(GeoPoint geopoint, Ray inRay) {
        return calcColor(geopoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(
                _scene.getAmbientLight().getIntensity());
    }
    /**
     * this func calc the refracted ray
     *
     * @param n the vector
     * @param point point
     * @param inRay ray
     * @return the ref ray
     */
    private Ray constructRefractedRay(Vector n, GeoPoint point, Ray inRay) {
        return new Ray(point._point, inRay.getDirection(), n);
    }

    /**
     * Calculate the average of a color in a pixel
     *
     * @param rayBeam
     * @return
     */
    private Color averageColor(List<Ray> rayBeam) {
        java.awt.Color background = _scene.getBackground().getColor();
        Color color = new Color(0, 0, 0);
        for (Ray ray : rayBeam) {
            color = findClosestIntersection(ray) == null ? color.add(_scene.getBackground())
                    : color.add(calcColorAdvanced(findClosestIntersection(ray), ray));
        }

        return color.reduce(rayBeam.size());
    }
}

