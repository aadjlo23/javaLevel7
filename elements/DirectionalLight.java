package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this is the class for the direction of light in cartesian 3D coordinate system
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * a simple constructor for directional light with it's intensity and direction,
     *
     * @param intensity intensity of the light
     * @param direction direction vector
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this._direction = direction.normalized();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }
    @Override
    public double getDistance(Point3D point){
        return Double.POSITIVE_INFINITY;
    };

}
