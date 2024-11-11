package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PPMReaderTest {


    private final String JPPMAsciiPath = Paths.get(getClass().getClassLoader().getResource("PPM/j.ppm").toURI()).toString();
    private PPMReader parser;
    private BufferedReader bufferedReader;
    public PPMReaderTest() throws URISyntaxException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        parser = new PPMReader();
        bufferedReader = new BufferedReader(new FileReader(JPPMAsciiPath));
    }

    @Test
    public void checkMagicNumberTest() {
        try {
            String magicNumber = parser.getMagicNumber(bufferedReader);
            assertEquals("P3", magicNumber);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void checkHeaderTest() {
        checkMagicNumberTest();
        try {
            String[] header = parser.getHeader(bufferedReader);
            assertEquals("6", header[0]);
            assertEquals("10", header[1]);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    public void checkBodyTest() {
        final int expectedHeight = 10;
        final int expectedWidth = 6;
        final int maxColorValue = 255;

        PixelWrapper[][] expectedGrip = new PixelWrapper[expectedHeight][expectedWidth];

        int[][][] pattern = {
                {{255, 0, 0},           {0, 255, 0},              {0, 0, 255},        {255, 255, 0},       {255, 0, 255},         {0, 255, 255}},
                {{255, 255, 255},       {128, 128, 128},      {64, 64, 64},       {0, 0, 0},           {255, 128, 64},        {128, 255, 64}},
                {{255, 0, 0},           {0, 255, 0},              {0, 0, 255},        {255, 255, 0},       {255, 0, 255},         {0, 255, 255}},
                {{255, 255, 255},       {128, 128, 128},      {64, 64, 64},       {0, 0, 0},           {255, 128, 64},        {128, 255, 64}},
                {{255, 0, 0},           {0, 255, 0},              {0, 0, 255},        {255, 255, 0},       {255, 0, 255},         {0, 255, 255}},
                {{255, 255, 255},       {128, 128, 128},      {64, 64, 64},       {0, 0, 0},           {255, 128, 64},        {128, 255, 64}},
                {{255, 0, 0},           {0, 255, 0},              {0, 0, 255},        {255, 255, 0},       {255, 0, 255},         {0, 255, 255}},
                {{255, 255, 255},       {128, 128, 128},      {64, 64, 64},       {0, 0, 0},           {255, 128, 64},        {128, 255, 64}},
                {{255, 0, 0},           {0, 255, 0},              {0, 0, 255},        {255, 255, 0},       {255, 0, 255},         {0, 255, 255}},
                {{255, 255, 255},       {128, 128, 128},      {64, 64, 64},       {0, 0, 0},            {255, 128, 64},       {128, 255, 64}}
        };

        for (int y = 0; y < expectedHeight; y++) {
            for (int x = 0; x < expectedWidth; x++) {
                float red = pattern[y][x][0] / (float) maxColorValue;
                float green = pattern[y][x][1] / (float) maxColorValue;
                float blue = pattern[y][x][2] / (float) maxColorValue;
                expectedGrip[y][x] = new PixelWrapper(red, green, blue);
            }
        }

        try {
            checkHeaderTest();
            PixelWrapper[][] resultGrid = parser.getPixels(bufferedReader,new String[]{"6", "10"});

            for (int y = 0; y < expectedHeight; y++) {
                for (int x = 0; x < expectedWidth; x++) {
                    assertEquals(expectedGrip[y][x].getRed(), resultGrid[y][x].getRed());
                    assertEquals(expectedGrip[y][x].getGreen(), resultGrid[y][x].getGreen());
                    assertEquals(expectedGrip[y][x].getBlue(), resultGrid[y][x].getBlue());
                }
            }


        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }


}
