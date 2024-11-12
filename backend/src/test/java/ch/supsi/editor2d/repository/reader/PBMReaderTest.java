package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class PBMReaderTest
{
    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private PBMReader parser;
    private BufferedReader bufferedReader;

    public PBMReaderTest() throws URISyntaxException {

    }

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        parser = new PBMReader();
        bufferedReader = new BufferedReader(new FileReader(JPBMAsciiPath));
    }



    @Test
    void checkMagicNumberTest() throws FileNotFoundException {
        try{
            String result = parser.getMagicNumber(bufferedReader);
            assertEquals("P1",result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkHeaderTest() throws FileNotFoundException {
        try{
            checkMagicNumberTest();
            String[] expectedHeader = new String[]{"6", "10"};
            String[] resultHeader = parser.getHeader(bufferedReader);
            assertEquals(expectedHeader[0], resultHeader[0]);
            assertEquals(expectedHeader[1], resultHeader[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkBodyTest(){

        final int height = 10;
        final int width = 6;
        PixelWrapper[][] expectedGrip = new PixelWrapper[height][width];

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

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++)
                expectedGrip[y][x] = (pattern[y][x]==0) ? white : black;
        }

        try{
            checkHeaderTest();
            PixelWrapper[][] resultGrid = parser.getPixels(bufferedReader,new String[]{"6","10"});

            for(int y=0; y<height; y++){
                for(int x=0; x<width; x++){
                    assertEquals(expectedGrip[y][x].getRed(), resultGrid[y][x].getRed());
                    assertEquals(expectedGrip[y][x].getBlue(), resultGrid[y][x].getBlue());
                    assertEquals(expectedGrip[y][x].getGreen(), resultGrid[y][x].getGreen());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
