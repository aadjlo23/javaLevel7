package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this Interface represent thr actions of light
 */
public interface LightSource {
    /**
     * get the Intensity of the lamp
     *
     * @param p the palce of the Intensity
     * @return the Intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * return the length of vector of light
     *
     * @param p -place of light
     * @return the direction of the light point
     */
    public Vector getL(Point3D p);

    double getDistance(Point3D point);
}
