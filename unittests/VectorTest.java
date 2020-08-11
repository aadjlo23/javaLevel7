package unittests;

import org.junit.Test;

import static org.junit.Assert.*;

import primitives.Point3D;
import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * @author aaron
 */
public class    VectorTest {
    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void crossProduct() {
        Vector v1, v2, v3, vr;
        Vector excepted;

        // ============ Equivalence Partitions Tests ==============
        //TC01: test of cross product witch sharp angle between
        v1 = new Vector(5, -2, 3);
        v2 = new Vector(-4, 5, 7);
        excepted = v1.crossProduct(v2);
        vr = new Vector(-29, -47, 17);
        assertEquals(excepted, vr);

        //TC02: test of cross product witch blunt angle between
        v1 = new Vector(-2, 2, 2);
        v2 = new Vector(0, 2, 2);
        excepted = v1.crossProduct(v2);
        vr = new Vector(0, 4, -4);
        assertEquals(excepted, vr);

        // ============ Boundary Value Analysis Tests ==============
        // TC11: cross product witch the same diretion
        try {
            v1 = new Vector(4, 8, 12);
            v2 = new Vector(2, 4, 6);
            v1.crossProduct(v2);
            fail("cross product witch the same diretion");
        } catch (IllegalArgumentException e) {
        }

        // TC12: cross product when the vectors have the opposite direction
        try {
            v1 = new Vector(4, 8, 12);
            v2 = new Vector(-4, -8, -12);
            v1.crossProduct(v2);
            fail("cross product when the vectors have the opposite direction");
        } catch (IllegalArgumentException e) {
        }

        //  TC13 test cross product of orthogonal vectors
        v1 = new Vector(1, 2, 3);
        v3 = new Vector(0, 3, -2);
        vr = v1.crossProduct(v3);
        assertEquals(v1.length() * v3.length(), vr.length(), 0.000001);
        assertEquals(0, vr.dotProduct(v1), 0.0000001);
        assertEquals(0, vr.dotProduct(v3), 0.0000001);
    }

    /**
     * test for the size of length in squared
     */
    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:when the vector normal vector
        assertEquals(25d, new Vector(0, 3, 4).lengthSquared(), 0.0000001);
    }

    /**
     * test for find the length vector
     */
    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: normal test
        assertEquals(5d, new Vector(3, 4, 0).length(), 0.0000001);
    }

    /**
     * test for dot prouduct vector
     */
    @Test
    public void dotProduct() {
        Vector v1, v2;
        // ============ Equivalence Partitions Tests ==============
        // TC07:test the vectors with a sharp angle (so dot product his positive )
        v1 = new Vector(-2, 2, 2);
        v2 = new Vector(0, 2, 2);
        assertEquals(8d, v1.dotProduct(v2), 0.0000001);

        //TC06:test vectors with a blunt angle(so dot product his negative )
        v1 = new Vector(5, -2, 3);
        v2 = new Vector(-4, 5, 7);
        double temp = -9;
        assertEquals(-9d, v1.dotProduct(v2), 0000001);

        // ============ Boundary Value Analysis Tests ==============
        //TC02:test for orthogonal vectors
        v1 = new Vector(3, 2, -7);
        v2 = new Vector(1, 2, 1);
        assertEquals(0, v1.dotProduct(v2), 0.0000001);

        //TC03:test for vectors with opposite directions
        v1 = new Vector(1, 2, 3);
        v2 = new Vector(-1, -2, -3);
        assertEquals(-14d, v1.dotProduct(v2), 0.0000001);

        //TC04:test for vectors with the same direction
        v1 = new Vector(1, 2, 4);
        v2 = new Vector(2, 4, 8);
        assertEquals(42, v1.dotProduct(v2), 0.0000001);
    }

    /**
     * test for vector normalize
     */
    @Test
    public void normalize() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:normal test
        Vector v1 = new Vector(4, 3, 0);
        Vector vr = v1.normalize();
        assertSame(v1, vr);
        Vector v2 = new Vector(0.8, 0.6, 0);
        assertEquals(v2, vr);
    }

    /**
     * test for vector normalized
     */
    @Test
    public void normalized() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:normal test
        Vector v1 = new Vector(4, 3, 0);
        Vector vr = v1.normalized();
        assertNotSame(v1, vr);
        Vector v2 = new Vector(0.8, 0.6, 0);
        assertEquals(v2, vr);
    }

    /**
     * test for vector mult be scale
     */
    @Test
    public void scale() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(4, 5, 6);
        // TC01:simple test
        double num = 5.68852;
        Vector result = new Vector((4 * num), (5 * num), (6 * num));
        Vector excepted = v1.scale(num);
        assertEquals(excepted, result);

        // ============ Boundary Value Analysis Tests ==============
        //TC02: mult be zero
        try {
            v1.scale(0);
            fail();
        } catch (IllegalArgumentException error) {
        }
    }

    /**
     * test add for vector
     */
    @Test
    public void add() {
        Vector v1, v2, result, expected;

        // ============ Equivalence Partitions Tests ==============
        //TC01: normal test
        v1 = new Vector(0, 3, 4);
        v2 = new Vector(0, 5, 4);
        expected = new Vector(0, 8, 8);
        result = v1.add(v2);
        assertEquals(expected, result);

        // ============ Boundary Value Analysis Tests ==============
        //TC02: Add v and -v
        try {
            new Vector(1, 2, 3).add(new Vector(-1, -2, -3));
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    /**
     * we test the subract in vector
     */
    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: normal test
        Vector v1 = new Vector(1, 2, 4);
        Vector v2 = new Vector(1, 5, 2);
        Vector expected = new Vector(0, -3, 2);
        Vector result = v1.subtract(v2);
        assertEquals(expected, result);

        // ============ Boundary Value Analysis Tests ==============
        //TC02: v - v
        Vector v = new Vector(1, 2, 3);
        try {
            v.subtract(v);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
}