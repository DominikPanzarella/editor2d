package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlipSideSideTest {
    @Test
    public void testApply01() {
        PixelWrapper[][] pixels = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.5f, 0.5f, 0.5f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.2f, 0.2f, 0.2f), new PixelWrapper(0.6f, 0.6f, 0.6f), new PixelWrapper(0.8f, 0.8f, 0.8f) },
                { new PixelWrapper(0.1f, 0.1f, 0.1f), new PixelWrapper(0.3f, 0.3f, 0.3f), new PixelWrapper(0.9f, 0.9f, 0.9f) }
        };

        ImageWrapper image = new PGMImageWrapper(3,3, pixels,1);

        FlipSideSide flipSideSide = new FlipSideSide();
        ImageWrapper flippedImage = flipSideSide.apply(image);

        PixelWrapper[][] expectedPixels = {
                { new PixelWrapper(1.0f, 1.0f, 1.0f), new PixelWrapper(0.5f, 0.5f, 0.5f), new PixelWrapper(0.0f, 0.0f, 0.0f) },
                { new PixelWrapper(0.8f, 0.8f, 0.8f), new PixelWrapper(0.6f, 0.6f, 0.6f), new PixelWrapper(0.2f, 0.2f, 0.2f) },
                { new PixelWrapper(0.9f, 0.9f, 0.9f), new PixelWrapper(0.3f, 0.3f, 0.3f), new PixelWrapper(0.1f, 0.1f, 0.1f) }
        };

        for (int i = 0; i < expectedPixels.length; i++) {
            for (int j = 0; j < expectedPixels[0].length; j++) {
                PixelWrapper expectedPixel = expectedPixels[i][j];
                PixelWrapper actualPixel = flippedImage.getData()[i][j];

                assertEquals(expectedPixel.getRed(), actualPixel.getRed(),
                        "Red value mismatch at (" + i + "," + j + ")");
                assertEquals(expectedPixel.getGreen(), actualPixel.getGreen(),
                        "Green value mismatch at (" + i + "," + j + ")");
                assertEquals(expectedPixel.getBlue(), actualPixel.getBlue(),
                        "Blue value mismatch at (" + i + "," + j + ")");
            }
        }
        assert(!image.getData().equals(flippedImage.getData()));
        assert(!Arrays.deepEquals(image.getData(), flippedImage.getData()));
    }
}
