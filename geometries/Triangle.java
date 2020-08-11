package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class Triangle represents a triangle in 3D cartesian coordinate system
 */
public class Triangle extends Polygon {

    /**
     * this is a basic triangle constructor that get 3 points and build a triangle from them
     * in did 3 points are already a triangle
     * this constructor uses the constructor of the parent class Polygon
     * The Triangle will not have it's own color (initialized to BLACK)
     *
     * @param p1 1st point
     * @param p2 2nd point
     * @param p3 3rd point
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3);
    }

    /**
     * this is a basic triangle constructor that get 3 points and build a triangle from them
     * in did 3 points are already a triangle
     * this constructor uses the constructor of the parent class Polygon
     *
     * @param emission the emission light of the triangle
     * @param p1       1st point
     * @param p2       2nd point
     * @param p3       3rd point
     */
    public Triangle(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        this(emission, new Material(0, 0, 0), p1, p2, p3);
    }

    /**
     * this is a basic triangle constructor that get 3 points and build a triangle from them
     * in did 3 points are already a triangle
     * this constructor uses the constructor of the parent class Polygon
     *
     * @param emission the emission light of the triangle
     * @param p1       1st point
     * @param p2       2nd point
     * @param p3       3rd point
     */
    public Triangle(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, material, p1, p2, p3);
    }

    @Override
    public String toString() {
        String result = "";
        for (Point3D p : _vertices) {
            result += p.toString();
        }
        return result;
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> intersections = _plane.findIntersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.getP0();
        Vector v = ray.getDirection();
        //we creat the tree vector
        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double side1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(side1)) return null;
        double side2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(side2)) return null;
        double side3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(side3)) return null;
        intersections.get(0)._geometry = this;

        if ((side1 > 0 && side2 > 0 && side3 > 0) || (side1 < 0 && side2 < 0 && side3 < 0)) return intersections;

        return null;
    }
}