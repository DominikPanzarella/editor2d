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
    private final String JPBMNonLinearAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/nonlinear.pbm")).toURI()).toString();
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


    @Test
    void checkNonLinearBodyTest() {

        final int height = 10;
        final int width = 6;
        PixelWrapper[][] expectedGrip = new PixelWrapper[height][width];

        int[][] pattern = {
                {0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1, 0}
        };

        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                expectedGrip[y][x] = (pattern[y][x] == 0) ? white : black;
            }
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(JPBMNonLinearAsciiPath));
            checkHeaderTest();
            PixelWrapper[][] resultGrid = parser.getPixels(bufferedReader, new String[]{"6", "10"});

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
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
    void checkNonLinearPixelDistributionTest() {
        try {
            String nonLinearPath = Paths.get(getClass().getClassLoader().getResource("PBM/non_linear.pbm").toURI()).toString();

            try (BufferedReader nonLinearReader = new BufferedReader(new FileReader(nonLinearPath))) {
                // Controllo magic number
                String magicNumber = parser.getMagicNumber(nonLinearReader);
                assertEquals("P1", magicNumber);

                // Header
                String[] header = parser.getHeader(nonLinearReader);
                int width = Integer.parseInt(header[0]);
                int height = Integer.parseInt(header[1]);
                assertEquals(4, width);
                assertEquals(2, height);

                // Pattern atteso
                // 0 = white (1.0f), 1 = black (0.0f)
                int[][] pattern = {
                        {0, 1, 0, 1},
                        {1, 1, 0, 0}
                };

                PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
                PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);

                PixelWrapper[][] expectedPixels = new PixelWrapper[height][width];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        expectedPixels[y][x] = (pattern[y][x] == 0) ? white : black;
                    }
                }

                PixelWrapper[][] resultGrid = parser.getPixels(nonLinearReader, header);

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        assertEquals(expectedPixels[y][x].getRed(), resultGrid[y][x].getRed());
                        assertEquals(expectedPixels[y][x].getGreen(), resultGrid[y][x].getGreen());
                        assertEquals(expectedPixels[y][x].getBlue(), resultGrid[y][x].getBlue());
                    }
                }

            }
        } catch (URISyntaxException | IOException e) {
            fail("An exception should not have been thrown: " + e.getMessage());
        }
    }
}
