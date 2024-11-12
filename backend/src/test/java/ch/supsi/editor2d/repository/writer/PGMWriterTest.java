package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PGMWriterTest {
    private PGMWriter writer;
    private ImageWrapper image;

    @BeforeEach
    void setUp() {
        writer = new PGMWriter();
        PixelWrapper[][] pixels = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.5f, 0.5f, 0.5f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.2f, 0.2f, 0.2f), new PixelWrapper(0.6f, 0.6f, 0.6f), new PixelWrapper(0.8f, 0.8f, 0.8f) },
                { new PixelWrapper(0.1f, 0.1f, 0.1f), new PixelWrapper(0.3f, 0.3f, 0.3f), new PixelWrapper(0.9f, 0.9f, 0.9f) }
        };
        image = new PGMImageWrapper(3,3, pixels,15);
    }

    @Test
    void testWriteValidExtension() throws IOException {
        String path = "test.pgm";
        String extension = "pgm";
        File tempFile = new File(path);

        // Testiamo il metodo write con un'estensione valida
        boolean result = writer.write(path, extension, image);

        assertTrue(result);
        assertTrue(tempFile.exists());

        // Pulizia
        tempFile.delete();
    }

    @Test
    void testWriteInvalidExtension() {
        String path = "test.txt";
        String extension = "txt";

        // Testiamo il caso in cui il file ha un'estensione non supportata
        Exception exception = assertThrows(IOException.class, () -> {
            writer.write(path, extension, image);
        });

        assertEquals("Can't handle this file!", exception.getMessage());
    }

    @Test
    void testWriteWithSuccessor() throws IOException {
        // Creiamo un writer che non può gestire l'estensione ma ha un successore
        PPMWriter secondWriter = new PPMWriter();
        writer.setSuccessor(secondWriter);

        String path = "test.ppm";
        String extension = "ppm";
        File tempFile = new File(path);
        if(Files.exists(tempFile.toPath())){
            tempFile.delete();
        } else{
            tempFile.createNewFile();
        }

        // Testiamo il metodo write con il successore
        boolean result = writer.write(path, extension, image);

        // change when PPMWriter is done implementing
        assertTrue(result);
        assertTrue(tempFile.exists());

        // Pulizia
        tempFile.delete();
    }
}
