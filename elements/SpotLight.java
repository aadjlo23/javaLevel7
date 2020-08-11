package elements;

import primitives.*;

import static primitives.Util.*;

/**
 * this class spot light in of light in cartesian 3D coordinate system
 */
public class SpotLight extends PointLight {
    protected Vector _direction;

    //***************** Constructors **********************//

    /**
     * constructor for spot light
     *
     * @param intensity light intensity
     * @param position  location  of the light
     * @param direction of light
     * @param kC        coefficient of quadratic attenuation of the light in the distance
     * @param kL        coefficient of linear weakening of the light in the distance
     * @param kQ        coefficient of exponential weakening of the light at a distance
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this._direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        Vector l = getL(p);
        double dl = _direction.dotProduct(l);
        if (alignZero(dl) <= 0) return Color.BLACK; // behind the spot
        return super.getIntensity(p).scale(dl);
    }
}
