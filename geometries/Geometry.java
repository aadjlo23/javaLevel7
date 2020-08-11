package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Abstract class for geometry, includes object's color (emission), and it's material
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission;
    protected Material _material;

    /**
     * constructor just without Material
     *
     * @param emission the emission light of the geometry
     */
    public Geometry(Color emission) {
        this(emission, new Material(0, 0, 0));
    }

    /**
     * constructor
     *
     * @param emission the emission light of the geometry
     */
    public Geometry(Color emission, Material material) {
        this._emission = emission;
        this._material = material;
    }

    /**
     * default constructor
     * initiate the emission light with the Color.Black
     */
    public Geometry() {
        this(Color.BLACK);

    }

    /**
     * the getter for the emission light on the geometry
     *
     * @return the emission light
     */
    public Color getEmission() {
        return _emission;
    }

    /**
     * @return material of the geometry object
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * getNormal function to get the normal vector (unit vector - length=1) from a point on a geometry surface
     *
     * @param p a point on geometry surface
     * @return the normal vector
     */
    public abstract Vector getNormal(Point3D p);
}