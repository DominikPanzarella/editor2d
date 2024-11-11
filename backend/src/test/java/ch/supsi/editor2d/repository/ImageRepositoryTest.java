package ch.supsi.editor2d.repository;

import ch.supsi.editor2d.service.IImageRepository;
import ch.supsi.editor2d.service.ImageService;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PBMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class ImageRepositoryTest
{
    private IImageRepository imageRepository;
    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private final String JPPMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PPM/j.ppm")).toURI()).toString();
    private final String JPGMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PGM/j.pgm")).toURI()).toString();
    private final String JWrongExtensionPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongExtension.pnm")).toURI()).toString();
    private final String wrongMagicNumberJPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongMagicNumber.pbm")).toURI()).toString();
    private final String wrongBodyJPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongBody.pbm")).toURI()).toString();

    public ImageRepositoryTest() throws URISyntaxException
    {}

    @BeforeEach
    private void init(){
        imageRepository = ImageRepository.getInstance();
    }

    @Test
    public void loadCorrectPBMImageTest() throws IOException {
        assertNotNull(imageRepository.handleLoadImage(JPBMAsciiPath,"PBM"));
    }

    @Test
    public void loadCorrectPGMImageTest() throws IOException {
        assertNotNull(imageRepository.handleLoadImage(JPGMAsciiPath,"PGM"));
    }

    @Test
    public void loadCorrectPPMImageTest() throws IOException {
        assertNotNull(imageRepository.handleLoadImage(JPPMAsciiPath,"PPM"));
    }

    @Test
    public void loadWrongExtensionImageTest() {
        assertThrows(FileReadingException.class,() ->imageRepository.handleLoadImage(JWrongExtensionPath,"PNM"));
    }

    @Test
    public void loadWrongmagicNumberImageTest() {
        assertThrows(FileReadingException.class,() ->imageRepository.handleLoadImage(wrongMagicNumberJPath,"PBM"));
    }

    @Test
    public void loadWrongBodyImageTest() {
        assertThrows(FileReadingException.class, () -> imageRepository.handleLoadImage(wrongBodyJPath, "PBM"));
    }

    @Test
    public void checkCorrectPBMImageLoading(){
        final int expectedHeight = 10;
        final int expectedWidth = 6;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 1, 0},
                {0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);

        for(int y=0; y<expectedHeight; y++){
            for(int x=0; x<expectedWidth; x++)
                expectedGrip[y][x] = (pattern[y][x]==0) ? white : black;
        }

        try {
            ImageWrapper loadedPBM = imageRepository.handleLoadImage(JPBMAsciiPath, "PBM");

            final int resultHeight = loadedPBM.getHeight();
            final int resultWidth = loadedPBM.getWidth();

            assertEquals(expectedHeight, resultHeight);
            assertEquals(expectedWidth, resultWidth);

            PixelWrapper[][] resultGrid = loadedPBM.getData();

            for(int y=0; y<resultHeight; y++){
                for(int x=0; x<resultWidth; x++){
                    assertEquals(expectedGrip[y][x].getRed(), resultGrid[y][x].getRed());
                    assertEquals(expectedGrip[y][x].getBlue(), resultGrid[y][x].getBlue());
                    assertEquals(expectedGrip[y][x].getGreen(), resultGrid[y][x].getGreen());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void checkCorrectPGMImageLoading() {
        final int expectedHeight = 7;
        final int expectedWidth = 24;
        final int maxGrayValue = 15;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][] pattern = {
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  3,  3,  3,  3,  0,  0,  7,  7,  7,  7,  0,  0, 11, 11, 11, 11,  0,  0, 15, 15, 15, 15,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  0,  0,  0,  0,  0, 11,  0,  0,  0,  0,  0, 15,  0,  0, 15,  0},
                {0,  3,  3,  3,  0,  0,  0,  7,  7,  7,  0,  0,  0, 11, 11, 11,  0,  0,  0, 15, 15, 15, 15,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  0,  0,  0,  0,  0, 11,  0,  0,  0,  0,  0, 15,  0,  0,  0,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  7,  7,  7,  0,  0, 11, 11, 11, 11,  0,  0, 15,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float normalizedGrayValue = (((float) 255 /maxGrayValue) / 255.0f) * pattern[y][x];
                expectedGrip[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }
        }
        // JPGMAsciiPath
        try{
            ImageWrapper loadedPBM = imageRepository.handleLoadImage(JPGMAsciiPath, "PGM");

            final int resultHeight = loadedPBM.getHeight();
            final int resultWidth = loadedPBM.getWidth();

            assertEquals(expectedHeight, resultHeight);
            assertEquals(expectedWidth, resultWidth);

            PixelWrapper[][] resultGrid = loadedPBM.getData();

            for(int y=0; y<resultHeight; y++){
                for(int x=0; x<resultWidth; x++){
                    assertEquals(expectedGrip[y][x].getRed(), resultGrid[y][x].getRed());
                    assertEquals(expectedGrip[y][x].getBlue(), resultGrid[y][x].getBlue());
                    assertEquals(expectedGrip[y][x].getGreen(), resultGrid[y][x].getGreen());
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkCorrectPPMImageLoading(){
        final int expectedHeight = 10;
        final int expectedWidth = 6;
        final int maxColorValue = 255;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][][] pattern = {
                {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
                {{255, 255, 255}, {128, 128, 128}, {64, 64, 64}, {0, 0, 0}, {255, 128, 64}, {128, 255, 64}},
                {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
                {{255, 255, 255}, {128, 128, 128}, {64, 64, 64}, {0, 0, 0}, {255, 128, 64}, {128, 255, 64}},
                {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
                {{255, 255, 255}, {128, 128, 128}, {64, 64, 64}, {0, 0, 0}, {255, 128, 64}, {128, 255, 64}},
                {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
                {{255, 255, 255}, {128, 128, 128}, {64, 64, 64}, {0, 0, 0}, {255, 128, 64}, {128, 255, 64}},
                {{255, 0, 0}, {0, 255, 0}, {0, 0, 255}, {255, 255, 0}, {255, 0, 255}, {0, 255, 255}},
                {{255, 255, 255}, {128, 128, 128}, {64, 64, 64}, {0, 0, 0}, {255, 128, 64}, {128, 255, 64}}
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float red = pattern[y][x][0] / (float) maxColorValue;
                float green = pattern[y][x][1] / (float) maxColorValue;
                float blue = pattern[y][x][2] / (float) maxColorValue;
                expectedGrip[y][x] = new PixelWrapper(red, green, blue);
            }
        }
        // JPGMAsciiPath
        try{
            ImageWrapper loadedPBM = imageRepository.handleLoadImage(JPPMAsciiPath, "PPM");

            final int resultHeight = loadedPBM.getHeight();
            final int resultWidth = loadedPBM.getWidth();

            assertEquals(expectedHeight, resultHeight);
            assertEquals(expectedWidth, resultWidth);

            PixelWrapper[][] resultGrid = loadedPBM.getData();

            for(int y=0; y<resultHeight; y++){
                for(int x=0; x<resultWidth; x++){
                    assertEquals(expectedGrip[y][x].getRed(), resultGrid[y][x].getRed());
                    assertEquals(expectedGrip[y][x].getBlue(), resultGrid[y][x].getBlue());
                    assertEquals(expectedGrip[y][x].getGreen(), resultGrid[y][x].getGreen());
                }
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
