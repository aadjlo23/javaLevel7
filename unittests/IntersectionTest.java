package unittests;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.Assert.*;

/**
 * we test the Intersection between package geometry and ray to the camera in 3D Cartesian coordinate
 *
 * @author aaron
 */
public class IntersectionTest {
    Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
    Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));

    /**
     * the func finds amount of intersections between a geometry and a camera
     * @param geometry the geometry to intersect in the test
     * @param expected expected amount of intersections
     * @param cam      the camera to generate the rays from
     */
    private void intersections(Intersectable geometry, int expected, Camera cam) {
        List<GeoPoint> results = null;
        int count = 0;
        int nX = 3;
        int nY = 3;
        for (int i = 0; i < nY; ++i) {
            for (int j = 0; j < nX; ++j) {
                results = geometry.findIntersections(cam.constructRayThroughPixel(nX, nY, j, i, 1, 3, 3));
                ;
                if (results != null)
                    count += results.size();
            }
        }
        assertEquals("not good", expected, count);
    }

    /**
     * we test the intersection between camera and sphere
     */
    @Test
    public void constructRayThroughPixelWithSphere() {
        // TC01 test of intersection with sphere when all the sphere are out from the screen 3*3
        intersections(new Sphere(1, new Point3D(0, 0, 3)), 2, cam1);

        // TC02 test of intersection with sphere when the all screen are in sphere
        intersections(new Sphere(2.5, new Point3D(0, 0, 2.5)), 18, cam2);

        //TC03:part of screen are in the sphere
        intersections(new Sphere(2, new Point3D(0, 0, 2)), 10, cam2);

        //TC04:the sphere around the screen
        intersections(new Sphere(4, new Point3D(0, 0, 2)), 9, cam2);

        //TC05: the sphere is behind the screen
        intersections(new Sphere(0.5, new Point3D(0, 0, -1)), 0, cam2);
    }

    /**
     * we test all the intersection between camera and plane on screen 3*3
     */
    @Test
    public void constructRayThroughPixelWithPlane() {
        //TC01: Plane is parallel to view Plane (9 points)
        intersections(new Plane(new Point3D(0, 0, 3), new Vector(0, 0, 1)), 9, cam1);

        //TC02: Plane is not parallel to view Plane(9 points)
        intersections(new Plane(new Point3D(0.5, -1.5, 2), new Point3D(0, 0, 2.5), new Point3D(0, 1.5, 3)),
                9, cam1);

        //TC03: Plane is not parallel to view Plane (6 points)
        intersections(new Plane(new Point3D(0.5, -2, 2), new Point3D(0, 0, 4), new Point3D(0, 2, 6))
                , 6, cam1);
    }

    /**
     * we test all the intersection between camera and Triangle on screen 3*3
     */
    @Test
    public void constructRayThroughPixelWithTriangle() {
        //TC01: when triangle are smaller than the screen
        intersections(new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2)),
                1, cam1);

        //TC02: when the triangle is bigger than the screen
        intersections(new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2)),
                2, cam1);

    }
}
