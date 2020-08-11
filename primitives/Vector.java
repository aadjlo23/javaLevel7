package primitives;

/**
 * Class Vector represents Euclidean 3D vector in Cartesian coordinate system
 */
public class Vector {
    private Point3D _head;

    /**
     * this is a basic constructor it receives three numbers and set them as the point head of the vector
     *
     * @param x 1st coordinate
     * @param y 2nd coordinate
     * @param z 3rd coordinates
     */
    public Vector(double x, double y, double z) {
        _head = new Point3D(x, y, z);
        if (_head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("It's not possible to have point head (0,0,0)");
    }

    /**
     * this is a basic constructor it receives a point already constructed and set it as the head of the vector
     *
     * @param p the point set as vector head
     */
    public Vector(Point3D p) {
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("It's not possible to have point head (0,0,0)");
        _head = p;
    }

    /**
     * Copy constructor for Point3D
     *
     * @param other source Vector to copy from
     */
    public Vector(Vector other) {
        _head = other._head;
    }

    /**
     * @return the vector
     */
    public Point3D getHead() {
        return _head;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head_vector=" + _head +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Vector)) return false;
        Vector vector = (Vector) obj;
        return _head.equals(vector._head);
    }

    /**
     * The function performs Cross Product (vector multiplication) operation
     * on this vector with another one: this X v
     * and creates new vector which is the result of the operation
     *
     * @param v 2nd vector for the operation
     * @return new vector with cross-product operation result
     * @throws IllegalArgumentException when the vectors are co-directed (the angle is either 0 or 180)
     *                                  the professor dan modify the comment here
     */
    public Vector crossProduct(Vector v) {
        double x1 = this._head._x._coord;
        double y1 = this._head._y._coord;
        double z1 = this._head._z._coord;
        double x2 = v._head._x._coord;
        double y2 = v._head._y._coord;
        double z2 = v._head._z._coord;
        return new Vector(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }

    /**
     * this function allows us to compute the square of the length of this vector
     *
     * @return a number equal to the square of the length of our vector
     */
    public double lengthSquared() {
        double x = this._head._x._coord;
        double y = this._head._y._coord;
        double z = this._head._z._coord;
        return x * x + y * y + z * z;
    }

    /**
     * this function allows us to receive the length of the vector
     *
     * @return a number equal to the length of our vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * this function multiplies the specified vector by the specified Double
     * and returns the result as a Vector
     *
     * @param mult the specified double
     * @return a new vector equal to the old one multiplied by the double
     */
    public Vector scale(double mult) {
        return new Vector(
                _head._x._coord * mult,
                _head._y._coord * mult,
                _head._z._coord * mult);
    }

    /**
     * The function performs Dot Product (Scalar Product) operation
     * on this vector with another one: this X v
     * and creates new vector which is the result of the operation
     *
     * @param v 2nd vector for this operation
     * @return new vector with dot product operation result
     */
    public double dotProduct(Vector v) {
        return ((this._head._x._coord * v._head._x._coord)
                + (this._head._y._coord * v._head._y._coord)
                + (this._head._z._coord * v._head._z._coord));
    }

    /**
     * This function normalize a given vector.
     * A normalized vector maintains its direction but its Length becomes 1.
     * A vector is normalized by dividing the vector by its own Length.
     * the professor modifie the comment here
     *
     * @return the vector normalized
     */
    public Vector normalize() {
        double mult = 1d / this.length();
        this._head = new Point3D(
                _head._x._coord * mult,
                _head._y._coord * mult,
                _head._z._coord * mult);
        return this;
    }

    /**
     * this function create a new vector and set it as the given vector normalized
     *
     * @return the given vector normalized in a new vector
     */
    public Vector normalized() {
        return new Vector(this.normalize());
    }

    /**
     * this function performs the addition of two vectors
     *
     * @param a the vector we add to our vector
     * @return new vector containing the result of the addition
     */
    public Vector add(Vector a) {
        return new Vector(this._head.add(a));
    }

    /**
     * this function performs the subtraction of two vectors and return a point as result
     *
     * @param a the vector we subtract to our vector
     * @return a point who is the result of the substraction
     */
    public Vector subtract(Vector a) {
        return this._head.subtract(a._head);
    }
}
