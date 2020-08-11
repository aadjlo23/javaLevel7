package unittests;

import org.junit.Test;
import primitives.Color;
import renderer.ImageWriter;

/**
 * in this class we test image writer
 * @author aaron
 */
public class ImageWriterTest {

    /**
     * we test the creation of an image 10*16 squared 1000*1600 unit light witch resolutions 500*800
     */
    @Test
    public void writeToImage() {
        Color green = new Color(0, 150, 0);
        Color red = new Color(255, 0, 0);
        ImageWriter img = new ImageWriter("img1", 1600, 1000, 800, 500);
        for (int col = 0; col< 800; col++) {
            for (int row = 0; row < 500; row++) {
                if (col % 50== 0 || row % 50  == 0)
                    img.writePixel(col, row, red.getColor());
                else
                    img.writePixel(col, row, green.getColor());

            }
        }
        img.writeToImage();

    }
}