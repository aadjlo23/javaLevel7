package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * this interface for all geometries that are able to intersect from a ray to their entity
 *
 * @ auther aaron
 */
public interface Intersectable {
    /**
     * The function looks for intersection points between a ray and a geometry. <br/>
     * If there are no intersections, the function returns null.
     *
     * @param ray the ray that intersects the object
     * @return a list of all intersection of GeoPoint
     */
    default List<GeoPoint> findIntersections(Ray ray) {
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**
     * filter shadow not in the other side
     *
     * @param ray ray
     * @param maxDistance max of the dist
     * @return the geo point closest
     */
    List<GeoPoint> findIntersections(Ray ray, double maxDistance);

    /**
     * class include intersection point and its geometry
     * this is a static helping class
     */
    public static class GeoPoint {

        public Geometry _geometry;
        public Point3D _point;

        /**
         * constructor for GeoPoint
         *
         * @param geometry the geometry we work with
         * @param point    the point we work with
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this._geometry = geometry;
            this._point = point;
        }

        /**
         * getter for the point
         *
         * @return point
         */
        public Point3D getPoint() {
            return _point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(_geometry, geoPoint._geometry) &&
                    Objects.equals(_point, geoPoint._point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + _geometry +
                    ", point=" + _point +
                    '}';
        }

    }

}

