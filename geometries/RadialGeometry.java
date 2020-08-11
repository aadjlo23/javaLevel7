package geometries;

import primitives.Color;
import primitives.Material;


/**
 * this is an abstract class who represents a radial geometry shape
 */
public abstract class RadialGeometry extends Geometry {
    protected double _radius;

    /**
     * this is simple constructor build the shapes witch radius and default color are black
     *
     * @param radius raduis of Radial Geometry
     */
    public RadialGeometry(double radius) {
        this(radius, Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * this is simple constructor build the shapes witch radius and default color are black and material is null
     *
     * @param radius   raduis of Radial Geometry
     * @param emission color of the Radial Geometry
     */
    public RadialGeometry(double radius, Color emission) {
        this(radius, Color.BLACK, new Material(0, 0, 0));
    }

    /**
     * this is simple constructor build the shapes witch radius
     *
     * @param radius   radius of Radial Geometry
     * @param emission color of the Radial Geometry
     * @param material the material that represent the objet
     */
    public RadialGeometry(double radius, Color emission, Material material) {
        super(emission, material);
        _radius = radius;

    }

    /**
     * a simple func that return radius
     *
     * @return radius
     */
    public double getRadius() {
        return _radius;
    }

    /**
     * this func return the radius in string
     *
     * @return radius
     */
    public String toString() {
        return "RadialGeometry {radius=" + this._radius;
    }

}