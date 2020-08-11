package unittests;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


import static org.junit.Assert.*;
/**
 * Test method for {@link geometries.Sphere}.
 * sphere test class test sphere in 3D Cartesian coordinate
 * we test the sphere of cylinder
 * @author aaron
 */
public class SphereTest {
    /**
     * the test if we get the good of center of the sphere
     */
    @Test
    public void get_center() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:we test the get center of sphere by comparing the result of the func and the expected result:
        try {
            Point3D centerPoint = new Point3D(2,3,4);
            double radius = 2;
            Sphere mySphere = new Sphere(radius, centerPoint);
            Point3D result = mySphere.getCenter();
            Point3D expResult = centerPoint;

            assertEquals(result, expResult);
        } catch (IllegalArgumentException e) {
        }
    }//if the test worked it means the result and expected results are equal and then get center works

    /**
     * we test we get the good get normal
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        //TC01:we test the get normal of sphere the same way than the other func(result and expected result)
        try {
            Sphere sp = new Sphere(1.0, new Point3D(0, 0, 1));
            assertEquals(new Vector(0, 0, 1), sp.getNormal(new Point3D(0, 0, 2)));
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * we test the intersection with ray and sphere
     */
    @Test
    public void findIntersections() {
        // ============ Equivalence Partitions Tests ==============

        //TC01 no intersections ray is outside of the sphere
        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        Sphere sphere =
                new Sphere(1, new Point3D(0, 2, -4));
        List<Intersectable.GeoPoint> intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        //TC02 ray start before and cross the sphere twice: 2 intersections
        //(si ca ne marche pas penser a changer l'ordre des points)
        ray = new Ray(new Point3D(-3, 1, 0), new Vector(6, 0, 0));
        sphere = new Sphere(2, new Point3D(0, 0, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must not be empty", intersectionsList);
        assertEquals("must be equal to 2", 2, intersectionsList.size());
        if (intersectionsList.get(0).getPoint().getX() > intersectionsList.get(1).getPoint().getX()) {
            intersectionsList = List.of(intersectionsList.get(1), intersectionsList.get(0));
        }
        assertEquals("must be equal", new Point3D(-1.732050807569, 1, 0), intersectionsList.get(0).getPoint());
        assertEquals("must be equal", new Point3D(1.732050807569, 1, 0), intersectionsList.get(1).getPoint());


        //TC03 ray start inside sphere:1 intersection
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(5, new Point3D(0, -1, -1));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 1, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, -5.898979485566), intersectionsList.get(0).getPoint());

        //TC04 ray starts outside sphere after it, due to direction- no intersection
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(2, new Point3D(0, 1, 4));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside
        ray = new Ray(new Point3D(-2, 0, 0), new Vector(2, 0, 2));
        sphere = new Sphere(2, new Point3D(0, 0, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 1, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, 2), intersectionsList.get(0).getPoint());

        // TC12; Ray starts at sphere and goes outside
        ray = new Ray(new Point3D(2, 0, 0), new Vector(2.29, 4.67, 0));
        sphere = new Sphere(2, new Point3D(0, 0, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);


        // **** Group: Ray's line goes through the center
        //TC13 ray start at the center of the sphere
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(5, new Point3D(0, 0, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 1, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, -5), intersectionsList.get(0).getPoint());

        //TC14 ray starts on sphere surface, to the outside:
        //no intersection because we don't take in account the ray head
        ray = new Ray(new Point3D(0, 0, 3), new Vector(0, 0, 5));
        sphere = new Sphere(2, new Point3D(0, 0, 1));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty",intersectionsList);

        //TC15 ray starts on sphere surface, to the inside:
        //1 intersection because we don't take in account the ray head
        ray = new Ray(new Point3D(0, 0, 4), new Vector(0, 0, 1));
        sphere = new Sphere(2, new Point3D(0, 0, 6));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 1, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, 8), intersectionsList.get(0).getPoint());


        //TC16 ray starts outside of the sphere to the outside but on a line aligned with the center: no intersections
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(5, new Point3D(0, 0, 6));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        //TC17 ray starts outside of the sphere to the inside cross twice the sphere and pass by center: 2 intersections
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(5, new Point3D(0, 0, -6));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 2, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, -1), intersectionsList.get(0).getPoint());
        assertEquals("must be equal", new Point3D(0, 0, -11), intersectionsList.get(1).getPoint());

        //TC18 ray starts on sphere surface, to the outside on the same line that the center: 1 intersection
        ray = new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1));
        sphere = new Sphere(5, new Point3D(0, 0, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNotNull("must be not empty", intersectionsList);
        assertEquals("must be equal to one", 1, intersectionsList.size());
        assertEquals("must be equal", new Point3D(0, 0, 5), intersectionsList.get(0).getPoint());

        // **** Group: Special cases
        //TC19 ray is on a line that vertical to radius- ray starts on the radius line
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(1, new Point3D(0, 2, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        // **** Group: Ray's line is tangent to the sphere
        //TC16 ray is on the tangent line- ray starts before intersection: 1 intersection
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(1, new Point3D(0, 1, -1));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        //TC17 ray is on the tangent line- ray starts on intersection: 1 intersection
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(1, new Point3D(0, 1, 0));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

        //TC18 ray is on the tangent line- ray starts after intersection: no intersections
        ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, -1));
        sphere = new Sphere(1, new Point3D(0, 1, 1));
        intersectionsList = sphere.findIntersections(ray);
        assertNull("must be empty", intersectionsList);

    }
}