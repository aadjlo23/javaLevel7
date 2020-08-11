package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;


/**
 * class with list of shape.
 */
public class Geometries implements Intersectable {
    private List<Intersectable> intersectableList = new LinkedList<>();

    /**
     * constructor received many geometries and adding them to the list
     *
     * @param geometries geometries to add the list
     */
    /* *********constructor*******/
    public Geometries(Intersectable... geometries) {
        for (Intersectable g : geometries)
            intersectableList.add(g);
    }

    /**
     * @return list of point 3D of intersectable
     */
    public List<Intersectable> getIntersectableList() {
        return intersectableList;
    }

    /**
     * function who adding geometry shape to list of the class
     *
     * @param geometry geometry shape
     */
    public void add(Intersectable... geometry) {
        for (Intersectable g : geometry)
            intersectableList.add(g);
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        List<Intersectable.GeoPoint> intersections = null;
        for (Intersectable geo : intersectableList) {
            List<GeoPoint> geoIntersections = geo.findIntersections(ray);
            if (geoIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }
}
