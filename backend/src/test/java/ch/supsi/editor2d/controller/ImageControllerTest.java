package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.ImageService;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ImageControllerTest
{
    private ImageController controller;

    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private final String JWrongExtensionPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongExtension.pnm")).toURI()).toString();
    private final String wrongMagicNumberJPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongMagicNumber.pbm")).toURI()).toString();
    private final String wrongBodyJPath =  Paths.get((getClass().getClassLoader().getResource("PBM/jWrongBody.pbm")).toURI()).toString();

    public ImageControllerTest() throws URISyntaxException {
    }


    @BeforeEach
    private void init(){
        controller = ImageController.getInstance();
    }

    @Test
    public void loadCorrectImageTest() throws IOException {
        assertNotNull(controller.loadImage(JPBMAsciiPath));
    }

    @Test
    public void checkCorrectImageTest(){
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

        try{
            ImageWrapper imageLoaded = controller.loadImage(JPBMAsciiPath);
            int resultHeight = imageLoaded.getHeight();
            int resultWidth = imageLoaded.getWidth();

            // check for correct height and width
            assertEquals(expectedHeight, resultHeight);
            assertEquals(expectedWidth, resultWidth);

            //check for correct data
            PixelWrapper[][] resultGrid = imageLoaded.getData();
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
    public void wrongExtensionTest(){
        assertThrows(FileReadingException.class, () ->controller.loadImage(JWrongExtensionPath));
    }


    @Test()
    public void wrongMagicNumberTest(){
        assertThrows(FileReadingException.class, () -> controller.loadImage(wrongMagicNumberJPath));
    }

    @Test
    public void wrongBodyWidthTest(){
        assertThrows(FileReadingException.class, () -> controller.loadImage(wrongBodyJPath));
    }

    @Test
    public void wrongBodyHeightTest(){
        assertThrows(FileReadingException.class, () -> controller.loadImage(wrongBodyJPath));
    }


}
