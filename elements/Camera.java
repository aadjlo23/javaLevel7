package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * camera class represents camera in 3D Cartesian coordinate
 *
 * @author aaron
 */
public class Camera {
    private Point3D _p0;
    private Vector _vTo;
    private Vector _vUp;
    private Vector _vRight;

    /**
     * a simple constuctor for camera
     *
     * @param p0-the  place of the camera
     * @param vTo     where the vector point outgoing from the camera
     * @param vUp-the vector vertical to vto
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        //if the vectors are not orthogonal, throw exception.
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("the vector must be orthogonal");

        this._p0 = p0;
        this._vTo = vTo.normalized();
        this._vUp = vUp.normalized();

        _vRight = this._vTo.crossProduct(this._vUp).normalize();
    }

    /**
     * @return the position of the camera
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return the position of vector camera
     */
    public Vector getVto() {
        return _vTo;
    }

    /**
     * @return the vector up of camera
     */
    public Vector getVup() {
        return _vUp;
    }

    /**
     * @return the vector right of camera
     */
    public Vector getVright() {
        return _vRight;
    }

    /**
     * the func should create ray witch point
     *
     * @param nX             number         of pixels in the x axis
     * @param nY             number         of pixels in the y axis
     * @param j              horizontal index of pixel (from left to right)
     * @param i              vertical        index of pixel (from up to down)
     * @param screenDistance the distance between the _p0 and pc where the image are located
     * @param screenWidth    width of the screen
     * @param screenHeight   height of the screen
     * @return ray where outgoing construct Ray Through Pixel
     */
    public Ray constructRayThroughPixel(double nX, double nY, int i, int j, double screenDistance, double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) throw new IllegalArgumentException("distance cannot be 0");
        //image center
        Point3D screenCenter = _p0.add(_vTo.scale(screenDistance));
        //ratio (pixel height&width)
        double ry = screenHeight / nY;
        double rx = screenWidth / nX;
        //pixel[i,j] center
        //multiplying of x value of pixel with the pixel width. and adding half of the width to get the distance till the center.
        double xPixel = (i - nX / 2.0) * rx + rx / 2.0;
        double yPixel = (j - nY / 2.0) * ry + ry / 2.0;
        Point3D pij = screenCenter;
        if (xPixel != 0) pij = pij.add(_vRight.scale(xPixel));
        if (yPixel != 0) pij = pij.add(_vUp.scale(-yPixel));
        //direction vector to pixel center
        Vector direction = pij.subtract(_p0);
        return new Ray(_p0, direction);
    }

    @Override
    public String toString() {
        return "Camera{" +
                "_p0=" + _p0 +
                ", _vUp=" + _vUp +
                ", _vTo=" + _vTo +
                ", _vRight=" + _vRight +
                '}';
    }
}
