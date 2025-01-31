package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class PGMReaderTest {
    private final String JPGMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PGM/j.pgm")).toURI()).toString();
    private PGMReader parser;
    private BufferedReader bufferedReader;

    public PGMReaderTest() throws URISyntaxException {
    }

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        parser = new PGMReader();
        bufferedReader = new BufferedReader(new FileReader(JPGMAsciiPath));
    }

    @Test
    void checkMagicNumberTest() throws FileNotFoundException {
        try{
            String result = parser.getMagicNumber(bufferedReader);
            assertEquals("P2",result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * This test requires checkMagicNumberTest() to run correctly before
     */
    @Test
    void checkHeaderTest() throws FileNotFoundException {
        checkMagicNumberTest();
        String[] expectedHeader = new String[]{"24", "7"};
        try{
            String[] resultHeader = parser.getHeader(bufferedReader);
            assertEquals(expectedHeader[0], resultHeader[0]);
            assertEquals(expectedHeader[1], resultHeader[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void checkBodyTest(){


        final int height = 7;
        final int width = 24;
        final int maxGrayValue = 15;

        PixelWrapper[][] expectedGrip = new PixelWrapper[height][width];

        int[][] pattern = {
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0},
                {0,  3,  3,  3,  3,  0,  0,  7,  7,  7,  7,  0,  0, 11, 11, 11, 11,  0,  0, 15, 15, 15, 15,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  0,  0,  0,  0,  0, 11,  0,  0,  0,  0,  0, 15,  0,  0, 15,  0},
                {0,  3,  3,  3,  0,  0,  0,  7,  7,  7,  0,  0,  0, 11, 11, 11,  0,  0,  0, 15, 15, 15, 15,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  0,  0,  0,  0,  0, 11,  0,  0,  0,  0,  0, 15,  0,  0,  0,  0},
                {0,  3,  0,  0,  0,  0,  0,  7,  7,  7,  7,  0,  0, 11, 11, 11, 11,  0,  0, 15,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0}
        };

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float normalizedGrayValue = (((float) 255 /maxGrayValue) / 255.0f) * pattern[y][x];

                expectedGrip[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }
        }

        try {
            checkHeaderTest();
            PixelWrapper[][] resultGrid = parser.getPixels(bufferedReader,new String[]{"24","7"});

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
            String nonLinearPath = Paths.get(getClass().getClassLoader().getResource("PGM/non_linear.pgm").toURI()).toString();

            try (BufferedReader nonLinearReader = new BufferedReader(new FileReader(nonLinearPath))) {
                // Controllo magic number
                String magicNumber = parser.getMagicNumber(nonLinearReader);
                assertEquals("P2", magicNumber);

                // Header
                String[] header = parser.getHeader(nonLinearReader);
                int width = Integer.parseInt(header[0]);
                int height = Integer.parseInt(header[1]);
                assertEquals(3, width);
                assertEquals(2, height);

                int maxGrayValue = Integer.parseInt(parser.checkLine(nonLinearReader));
                assertEquals(5, maxGrayValue);

                // Pattern atteso
                int[][] pattern = {
                        {5, 5, 3},
                        {5, 0, 1}
                };

                PixelWrapper[][] expectedPixels = new PixelWrapper[height][width];
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        float normalizedGrayValue = (((float) 255 / maxGrayValue) / 255.0f) * pattern[y][x];
                        expectedPixels[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
                    }
                }

                PixelWrapper[][] resultGrid = parser.getPixels(nonLinearReader, new String[]{String.valueOf(width), String.valueOf(height)});

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
