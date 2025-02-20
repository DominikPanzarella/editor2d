package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.utils.exceptions.FileReadingException;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PNMReaderTest {

    @Test
    public void checkAndGetLineRemoveContent(){
        String expected = "1234";
        java.io.Reader reader = new StringReader("1234 # Comment");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result = new PPMReader().checkLine(bufferedReader);
            assertEquals(expected, result);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void checkAndGetLineEmptyLine() {
        java.io.Reader reader = new StringReader("");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            assertNull(new PPMReader().checkLine(bufferedReader));
        } catch (FileReadingException | IOException ignored) {
        }
    }

    @Test
    void checkAndGetLineMultiLine() {
        List<String> expected = Arrays.asList("Line1", "Line3");

        java.io.Reader reader = new StringReader("Line1\n#Cancel\nLine3");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result1 = new PPMReader().checkLine(bufferedReader);
            String result2 = new PPMReader().checkLine(bufferedReader);
            assertEquals(expected.get(0), result1);
            assertEquals(expected.get(1), result2);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void removeSpaceTest(){
        List<String> expected = Arrays.asList("Line1", "Line3");
        Reader reader = new StringReader("               Line1\n      #Cancel\n         Line3");
        BufferedReader bufferedReader = new BufferedReader(reader);
        try{
            String result = new PPMReader().checkLine(bufferedReader);
            String result2 = new PPMReader().checkLine(bufferedReader);
            assertEquals(expected.get(0),result);
            assertEquals(expected.get(1), result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
