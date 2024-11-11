package ch.supsi.editor2d.repository.reader;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PNMReaderTest {

    @Test
    public void checkAndGetLineRemoveContent(){
        String expected = "1234";
        Reader reader = new StringReader("1234 # Comment");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result = PNMReader.checkLine(bufferedReader);
            assertEquals(expected, result);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void checkAndGetLineEmptyLine() {
        Reader reader = new StringReader("");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            PNMReader.checkLine(bufferedReader);
            fail();
        } catch (IOException ignored) {
        }
    }

    @Test
    void checkAndGetLineMultiLine() {
        List<String> expected = Arrays.asList("Line1", "Line3");

        Reader reader = new StringReader("Line1\n#Cancel\nLine3");
        BufferedReader bufferedReader = new BufferedReader(reader);

        try {
            String result1 = PNMReader.checkLine(bufferedReader);
            String result2 = PNMReader.checkLine(bufferedReader);
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
            String result = PNMReader.checkLine(bufferedReader);
            String result2 = PNMReader.checkLine(bufferedReader);
            assertEquals(expected.get(0),result);
            assertEquals(expected.get(1), result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
