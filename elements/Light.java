package elements;

import primitives.Color;

/**
 * this class represent the light in our moodel of 3D
 */
public abstract class Light {
    protected Color _intensity;

    /**
     * constructor for the abstract class light to initialize Lights color intensity
     *
     * @param intensity this is the color of the light in this abstract class
     */
    public Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * the getter of the intensity of the light
     *
     * @return color Intensity
     */
    public Color getIntensity() {
        return _intensity;
    }


}
