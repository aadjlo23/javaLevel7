package primitives;

/**
 * class Point3d represents a point in cartesian coordinate system
 */
public class Point3D {
    /**
     * This is point in center of Cartesian coordinate system - point (0,0,0)
     */
    public static Point3D ZERO = new Point3D(0, 0, 0);
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    /**
     * this is the constructor of a point in cartesian coordinate system with three numbers x,y,z that will become coordinate
     *
     * @param x 1st coordinate
     * @param y 2nd coordinate
     * @param z 3rd coordinate
     */
    public Point3D(double x, double y, double z) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
        this._z = new Coordinate(z);
    }

    /**
     * @return cordinate x
     */
    public double getX() {
        return _x._coord;
    }

    /**
     * @return cordinate y
     */
    public double getY() {
        return _y._coord;
    }

    /**
     * @return cordinate z
     */
    public double getZ() {
        return _z._coord;
    }

    /**
     * this is the function that will allow us to calculate the square of the distance between two points
     *
     * @param p this is the second point we will receive in parameter
     * @return the square of the distance between our point and the point p
     */
    public double distanceSquared(Point3D p) {
        double dx = this._x._coord - p._x._coord;
        double dy = this._y._coord - p._y._coord;
        double dz = this._z._coord - p._z._coord;
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * this function calculate the distance between two points
     *
     * @param p the second point
     * @return the distance between our points and the point p
     */
    public double distance(Point3D p) {
        return Math.sqrt(this.distanceSquared(p));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Point3D)) return false;
        Point3D oth = (Point3D) obj;
        return _x.equals(oth._x) && _y.equals(oth._y) && _z.equals(oth._z);
    }

    /**
     * the function allows us to compute an addition between a point and a vector and it will return a point for result
     *
     * @param v the vector we add to our point
     * @return a point3d as a result of the addition
     */

    public Point3D add(Vector v) {
        Point3D head = v.getHead();
        return new Point3D(_x._coord + head._x._coord,
                this._y._coord + head._y._coord,
                this._z._coord + head._z._coord);
    }

    /**
     * the function allows us to compute a subtraction of a point from another one the result will be a vector
     *
     * @param other the point we subtract to our current point
     * @return a new vector as the result of the subtraction
     */
    public Vector subtract(Point3D other) {
        return new Vector(this._x._coord - other._x._coord,
                this._y._coord - other._y._coord,
                this._z._coord - other._z._coord);
    }

    @Override
    public String toString() {
        return "(" +
                _x +
                ", " + _y +
                ", " + _z +
                ')';
    }
}