package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.repository.ImageRepository;
import ch.supsi.editor2d.service.IImageRepository;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotateClockwiseTest
{
    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private final String JPPMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PPM/j.ppm")).toURI()).toString();
    private final String JPGMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PGM/j.pgm")).toURI()).toString();
    private final RotateClockwise rotateClockwise = new RotateClockwise();
    private final IImageRepository imageRepository = ImageRepository.getInstance();

    public RotateClockwiseTest() throws URISyntaxException {
    }

    @Test
    public void rotate2x2MatrixTest(){
        int height = 2;
        int width = 2;
        PixelWrapper[][] inputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(0.0f,0.0f,0.0f), new PixelWrapper(1,1,1)},
                {new PixelWrapper(1,0.f,0.f), new PixelWrapper(0,1.0f,0)}
        };

        ImageWrapper inputImage = new PPMImageWrapper(height,width, inputMatrix, 255);

        RotateClockwise rotateClockwise = new RotateClockwise();
        ImageWrapper outputImage = rotateClockwise.apply(inputImage);

        PixelWrapper[][] expectedOutputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(1.0f,0.0f,0.0f), new PixelWrapper(0.0f,0.0f,0.0f)},
                {new PixelWrapper(0.0f,1.0f,0.f), new PixelWrapper(1.0f,1.0f,1.0f)}
        };

        assertEquals(outputImage.getHeight(), height);
        assertEquals(outputImage.getWidth(), width);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "I pixel alla posizione (" + y + ", " + x + ") non corrispondono");
            }
        }
    }

    @Test
    public void rotate4x3MatrixTest() {
        int height = 4;
        int width = 3;

        PixelWrapper[][] inputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(1, 0, 0), new PixelWrapper(1, 1, 0), new PixelWrapper(1, 1, 1)},
                {new PixelWrapper(0, 1, 0), new PixelWrapper(0, 1, 1), new PixelWrapper(1, 0, 1)},
                {new PixelWrapper(0, 0, 1), new PixelWrapper(0, 1, 0), new PixelWrapper(0, 0, 0)},
                {new PixelWrapper(1, 1, 0), new PixelWrapper(1, 0, 1), new PixelWrapper(0, 1, 1)}
        };

        ImageWrapper inputImage = new PPMImageWrapper(height, width, inputMatrix, 255);

        ImageWrapper outputImage = rotateClockwise.apply(inputImage);

        // Matrice attesa dopo la rotazione oraria
        PixelWrapper[][] expectedOutputMatrix = new PixelWrapper[][]{
                {new PixelWrapper(1, 1, 0), new PixelWrapper(0, 0, 1), new PixelWrapper(0, 1, 0), new PixelWrapper(1, 0, 0)},
                {new PixelWrapper(1, 0, 1), new PixelWrapper(0, 1, 0), new PixelWrapper(0, 1, 1), new PixelWrapper(1, 1, 0)},
                {new PixelWrapper(0, 1, 1), new PixelWrapper(0, 0, 0), new PixelWrapper(1, 0, 1), new PixelWrapper(1, 1, 1)}
        };

        assertEquals(outputImage.getHeight(), width); // L'altezza dopo la rotazione diventa la larghezza
        assertEquals(outputImage.getWidth(), height); // La larghezza dopo la rotazione diventa l'altezza

        // Confrontiamo i singoli elementi delle matrici
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "I pixel alla posizione (" + y + ", " + x + ") non corrispondono");
            }
        }
    }

    @Test
    public void rotateJPBMImageTest() throws IOException {
        // !!! height and width are inverted
        final int expectedHeight = 6;
        final int expectedWidth = 10;

        /**
         * 0 --> white --> new PixelWrapper(1.0f, 1.0f, 1.0f);
         * 1 --> black --> new PixelWrapper(0.0f, 0.0f, 0.0f);
         */

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);

        for(int y=0; y<expectedHeight; y++){
            for(int x=0; x<expectedWidth; x++)
                expectedGrip[y][x] = (pattern[y][x]==0) ? white : black;
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(JPBMAsciiPath, "PBM");
        ImageWrapper outputImage = rotateClockwise.apply(inputImage);

        // Matrice attesa dopo la rotazione oraria
        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight); // L'altezza dopo la rotazione diventa la larghezza
        assertEquals(outputImage.getWidth(), expectedWidth); // La larghezza dopo la rotazione diventa l'altezza

        // Confrontiamo i singoli elementi delle matrici
        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "I pixel alla posizione (" + y + ", " + x + ") non corrispondono");
            }
        }

    }


    @Test
    public void rotateJPGMImageTest() throws IOException {
        final int expectedHeight = 24;
        final int expectedWidth = 7;
        final int maxGrayValue = 15;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {0,0,0,0,0,0,0},
                {0,3,3,3,3,3,0},
                {0,0,0,3,0,3,0},
                {0,0,0,3,0,3,0},
                {0,0,0,0,0,3,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,7,7,7,7,7,0},
                {0,7,0,7,0,7,0},
                {0,7,0,7,0,7,0},
                {0,7,0,0,0,7,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,11,11,11,11,11,0},
                {0,11,0,11,0,11,0},
                {0,11,0,11,0,11,0},
                {0,11,0,0,0,11,0},
                {0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0},
                {0,15,15,15,15,15,0},
                {0,0,0,15,0,15,0},
                {0,0,0,15,0,15,0},
                {0,0,0,15,15,15,0},
                {0,0,0,0,0,0,0},
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float normalizedGrayValue = (((float) 255 /maxGrayValue) / 255.0f) * pattern[y][x];
                expectedGrip[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(JPGMAsciiPath, "PGM");
        PPMImageWrapper outputImage = (PPMImageWrapper) rotateClockwise.apply(inputImage);

        // Matrice attesa dopo la rotazione oraria
        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight); // L'altezza dopo la rotazione diventa la larghezza
        assertEquals(outputImage.getWidth(), expectedWidth); // La larghezza dopo la rotazione diventa l'altezza
        assertEquals(outputImage.getColorScale(), maxGrayValue);

        // Confrontiamo i singoli elementi delle matrici
        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "I pixel alla posizione (" + y + ", " + x + ") non corrispondono");
            }
        }
    }

    @Test
    public void rotateJPPMImageTest() throws IOException {
        final int expectedHeight = 6;
        final int expectedWidth = 10;
        final int maxColorValue = 255;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][][] pattern = {
                {{255, 255, 255}, {255, 0, 0}, {255, 255, 255}, {255, 0, 0}, {255, 255, 255}, {255, 0, 0}, {255,255,255}, {255,0,0}, {255,255,255}, {255,0,0}},
                {{128, 128, 128}, {0, 255, 0}, {128, 128, 128}, {0, 255, 0}, {128, 128, 128}, {0, 255, 0}, {128,128,128}, {0,255,0},{128,128,128},{0,255,0}},
                {{64, 64, 64}, {0, 0, 255}, {64, 64, 64}, {0, 0, 255}, {64, 64, 64}, {0, 0, 255},{64, 64, 64}, {0, 0, 255},{64, 64, 64}, {0, 0, 255}},
                {{0, 0, 0}, {255, 255, 0}, {0, 0, 0}, {255, 255, 0}, {0, 0, 0}, {255, 255, 0},{0, 0, 0}, {255, 255, 0}, {0, 0, 0}, {255, 255, 0}},
                {{255, 128, 64}, {255, 0, 255}, {255, 128, 64}, {255, 0, 255}, {255, 128, 64}, {255, 0, 255},{255, 128, 64}, {255, 0, 255},{255, 128, 64}, {255, 0, 255}},
                {{128, 255, 64}, {0, 255, 255}, {128, 255, 64}, {0, 255, 255}, {128, 255, 64}, {0, 255, 255},{128, 255, 64}, {0, 255, 255},{128, 255, 64}, {0, 255, 255}}
        };


        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float red = pattern[y][x][0] / (float) maxColorValue;
                float green = pattern[y][x][1] / (float) maxColorValue;
                float blue = pattern[y][x][2] / (float) maxColorValue;
                expectedGrip[y][x] = new PixelWrapper(red, green, blue);
            }
        }

        ImageWrapper inputImage = imageRepository.handleLoadImage(JPPMAsciiPath, "PPM");
        PPMImageWrapper outputImage = (PPMImageWrapper) rotateClockwise.apply(inputImage);

        // Matrice attesa dopo la rotazione oraria
        PixelWrapper[][] expectedOutputMatrix = outputImage.getData();

        assertEquals(outputImage.getHeight(), expectedHeight); // L'altezza dopo la rotazione diventa la larghezza
        assertEquals(outputImage.getWidth(), expectedWidth); // La larghezza dopo la rotazione diventa l'altezza
        assertEquals(outputImage.getColorScale(), maxColorValue);

        // Confrontiamo i singoli elementi delle matrici
        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                assertEquals(expectedOutputMatrix[y][x], outputImage.getData()[y][x],
                        "I pixel alla posizione (" + y + ", " + x + ") non corrispondono");
            }
        }
    }

}
