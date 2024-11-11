package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.repository.ImageRepository;
import ch.supsi.editor2d.service.IImageRepository;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NegativeTest {

    private final String PBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private final String PGMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PGM/j.pgm")).toURI()).toString();
    private final String PPMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PPM/j.ppm")).toURI()).toString();
    private final IImageRepository imageRepository = ImageRepository.getInstance();
    private final Negative negativeFilter = new Negative();

    public NegativeTest() throws URISyntaxException {
    }

    @Test
    public void negative2x2MatrixTest() {
        int height = 2;
        int width = 2;
        PixelWrapper[][] inputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f)},
                {new PixelWrapper(1.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 1.0f, 0.0f)}
        };

        ImageWrapper inputImage = new PPMImageWrapper(height, width, inputMatrix, 255);

        // Applica il filtro negativo
        ImageWrapper outputImage = negativeFilter.apply(inputImage);

        PixelWrapper[][] expectedOutputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(1.0f, 1.0f, 1.0f), new PixelWrapper(0.0f, 0.0f, 0.0f)},
                {new PixelWrapper(0.0f, 1.0f, 1.0f), new PixelWrapper(1.0f, 0.0f, 1.0f)}
        };

        assertEquals(outputImage.getHeight(), height);
        assertEquals(outputImage.getWidth(), width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "Mismatch (" + y + ", " + x + ")");
            }
        }
    }
    @Test
    public void negativePBMImageTest() throws IOException {
        final int expectedHeight = 10;
        final int expectedWidth = 6;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };

        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                expectedGrip[y][x] = pattern[y][x] == 0 ? black : white;
            }
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(PBMAsciiPath, "PBM");
        ImageWrapper outputImage = negativeFilter.apply(inputImage);

        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight);
        assertEquals(outputImage.getWidth(), expectedWidth);

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "Mismatch (" + y + ", " + x + ")");
            }
        }
    }

    @Test
    public void negativeJPGMImageTest() throws IOException {
        final int expectedHeight = 7;
        final int expectedWidth = 24;
        final int maxGrayValue = 15;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 15, 15, 15, 0, 0, 0, 15, 15, 15, 0, 0, 0, 15, 15, 15, 0, 0, 0, 15, 15, 15, 0, 0},
                {0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0},
                {0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0, 0, 15, 0, 15, 0, 0},
                {0, 15, 15, 15, 15, 15, 0, 15, 15, 15, 15, 15, 0, 15, 15, 15, 15, 15, 0, 15, 15, 15, 15, 15},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float normalizedGrayValue = (((float) 255 / maxGrayValue) / 255.0f) * pattern[y][x];
                expectedGrip[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(PGMAsciiPath, "PGM");
        PGMImageWrapper outputImage = (PGMImageWrapper) negativeFilter.apply(inputImage);

        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight);
        assertEquals(outputImage.getWidth(), expectedWidth);

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "Mismatch (" + y + ", " + x + ")");
            }
        }
    }

    @Test
    public void negativeJPPMImageTest() throws IOException {
        final int expectedHeight = 10;
        final int expectedWidth = 6;
        final int maxColorValue = 255;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][][] pattern = {
                {{0, 255, 255}, {128, 255, 64}, {0, 255, 255}, {128, 255, 64},{0, 255, 255}, {128, 255, 64}},
                {{255, 0, 255}, {255, 128, 64}, {255, 0, 255}, {255, 128, 64},{255, 0, 255}, {255, 128, 64}},
                {{255, 255, 0}, {0, 0, 0}, {255, 255, 0}, {0, 0, 0},{255, 255, 0}, {0, 0, 0}},
                {{0, 0, 255}, {64, 64, 64},{0, 0, 255}, {64, 64, 64},{0, 0, 255}, {64, 64, 64}},
                {{0, 255, 0}, {128, 128, 128}, {0, 255, 0}, {128, 128, 128},{0, 255, 0}, {128, 128, 128}},
                {{255, 0, 0}, {255, 255, 255}, {255, 0, 0}, {255, 255, 255},{255, 0, 0}, {255, 255, 255}},
                {{0, 0, 0}, {64, 64, 64}, {255, 0, 0}, {64, 64, 64}, {255, 255, 0}, {64, 64, 64}},
                {{128, 128, 0}, {128, 255, 64}, {255, 0, 0}, {255, 128, 64}, {0, 255, 0}, {128, 255, 64}},
                {{255, 0, 0}, {255, 128, 64}, {0, 255, 255}, {128, 255, 64}, {0, 0, 0}, {64, 255, 64}},
                {{255, 0, 0}, {255, 128, 64}, {255, 0, 255}, {128, 64, 64}, {128, 255, 0}, {128, 255, 64}}
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float red = pattern[y][x][0] / (float) maxColorValue;
                float green = pattern[y][x][1] / (float) maxColorValue;
                float blue = pattern[y][x][2] / (float) maxColorValue;
                expectedGrip[y][x] = new PixelWrapper(red, green, blue);
            }
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(PPMAsciiPath, "PPM");
        PPMImageWrapper outputImage = (PPMImageWrapper) negativeFilter.apply(inputImage);

        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight);
        assertEquals(outputImage.getWidth(), expectedWidth);

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "Mismatch (" + y + ", " + x + ")");
            }
        }
    }

}
