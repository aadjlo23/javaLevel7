package unittests;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;

/**
 *Test method for {@link geometries.Cylinder}.
 *  * Cylinder test class test cylinder in 3D Cartesian coordinate
 *  @author Aaron
 */
public class CylinderTest {
    // ============ Equivalence Partitions Tests ==============

    /**
     * this func should test BVA of get normal
     *
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //we build the objects we will use for our tests
        Ray ray = new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1));
        Cylinder c2 = new Cylinder(ray, 1, 1);
        Point3D p1 = new Point3D(0, 1, 1);
        Point3D p2 = new Point3D(0, 0.5, 1);

        // TC01:Test cylinder getNormal return the correct answer point on the body of cylinder
        Cylinder c1 = new Cylinder(ray, 1, 15);

        assertEquals(new Vector(0, 1, 0), c1.getNormal(p1));

        //TC02: Test when height and the vec of Cylinder is same equals (on the top base)
        assertEquals(ray.getDirection(), c2.getNormal(p2));


        // TC03:Test when orthogonal, on the down base
        Point3D p3 = new Point3D(0, 0.5, 0);
        assertEquals(ray.getDirection(), c1.getNormal(p3));


        //TC04:Test when point is the center of top base
        assertEquals(ray.getDirection(), c1.getNormal(ray.getP0()));


        // TC05:Test when point is the center of down base
        Point3D p4 = new Point3D(0, 0, 15);
        assertEquals(ray.getDirection(), c1.getNormal(p4));


    }


}
