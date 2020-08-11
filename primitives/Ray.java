package primitives;
import static primitives.Util.*;

/**
 * the class ray represents 3D ray in cartesian coordinate system
 */
public class Ray {
    private static final double DELTA = 0.1;
    private Point3D _p0;
    private Vector _direction;

    /**
     * this is the basic constructor of ray it receive a point and a vector and
     * create a new ray from this point and the vector normalized for his direction
     * @param p the base point of the ray
     * @param v the vector that will give the direction of the ray
     */
    public Ray(Point3D p, Vector v) {
        this._direction = v.normalized();
        this._p0 = p;
    }
    /**
     * this is the basic constructor of ray it receive a point a vector and a light direction and
     * create a new ray from this point and the vector normalized for his direction and the light dir
     *
     * @param p0  point
     * @param dir light dir
     * @param n   normal
     */
    public Ray(Point3D p0, Vector dir, Vector n) {
        _direction = dir.normalized();
        double nv = n.dotProduct(dir);
        Vector delta = n.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = p0.add(delta);
    }


    /**
     * Get a point on the ray at a given distance from the ray head
     * @param distance from the ray head
     * @return the point
     */
    public Point3D getTargetPoint(double distance) {
        return _p0.add(_direction.scale(distance));
    }

    /**
     * the getter for the direction vector
     * @return the direction vector
     */
    public Vector getDirection() {
        return _direction;
    }

    /**
     * the getter for the point of the ray
     * @return the point of the ray
     */
    public Point3D getP0() {
        return _p0;
    }

    @Override
    public String toString() {
        return "Ray{" + "P1= " + _p0 + ", _direction=" + _direction + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _direction.equals(ray._direction);
    }

}
