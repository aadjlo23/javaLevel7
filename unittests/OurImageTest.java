package unittests;
import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.*;
import org.junit.Test;
import primitives.*;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class OurImageTest {
    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void OurImageTest() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //z
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)),
                new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 60, 0.5, 0.2), // )
                        28, new Point3D(-50, 30, 50)),
                new Sphere(new Color(java.awt.Color.magenta), new Material(0.2, 0.2, 20, 0.1, 0.2), // )
                        10, new Point3D(-50, 30, 20)),
                new Triangle(new Color(0,128,0), new Material(0.5, 0.5, 60), //
                        new Point3D(-45, 35, 80), new Point3D(-10, 35, 105), new Point3D(35, -75, 55)), //
                new Sphere(new Color(java.awt.Color.red), new Material(0.2, 0.2, 90, 0, 0.6), // )
                        33, new Point3D(-50, -50, 50)));

        scene.addLights(

                new SpotLight(new Color(800, 400, 400), //
                        new Point3D(50, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
                new PointLight(new Color(127, 255, 212), new Point3D(-400, 120, 50),1,1,1),
                new SpotLight(new Color(100, 250, 100),
                        new Point3D(-20, 20, 100), new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("OurImage", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void OurImageTestAdvace() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //z
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)),
                new Sphere(new Color(java.awt.Color.black), new Material(0.2, 0.2, 60, 0.5, 0.2), // )
                        28, new Point3D(-50, 30, 50)),
                new Sphere(new Color(java.awt.Color.magenta), new Material(0.2, 0.2, 20, 0.1, 0.2), // )
                        10, new Point3D(-50, 30, 20)),
                new Triangle(new Color(0,128,0), new Material(0.5, 0.5, 60), //
                        new Point3D(-45, 35, 80), new Point3D(-10, 35, 105), new Point3D(35, -75, 55)), //
                new Sphere(new Color(java.awt.Color.red), new Material(0.2, 0.2, 90, 0, 0.6), // )
                        33, new Point3D(-50, -50, 50)));

        scene.addLights(

                new SpotLight(new Color(800, 400, 400), //
                        new Point3D(50, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7),
                new PointLight(new Color(127, 255, 212), new Point3D(-400, 120, 50),1,1,1),
                new SpotLight(new Color(100, 250, 100),
                        new Point3D(-20, 20, 100), new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("OurImageAdv", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImageAdvanced(true);
        render.writeToImage();
    }
}
