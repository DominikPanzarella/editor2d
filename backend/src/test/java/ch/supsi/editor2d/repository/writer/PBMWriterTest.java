package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PBMImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PBMWriterTest {
    private PBMWriter writer;
    private ImageWrapper image;

    @BeforeEach
    void setUp() {
        writer = new PBMWriter();
        PixelWrapper[][] pixelsPBM = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(1.0f, 1.0f, 1.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f) }
        };
        image = new PBMImageWrapper(3,3, pixelsPBM);
    }

    @Test
    void testWriteValidExtension() throws IOException {
        String path = "test.pbm";
        String extension = "pbm";
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
}
