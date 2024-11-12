package ch.supsi.editor2d.repository.converter;

import ch.supsi.editor2d.service.model.PBMImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PGMConverterTest {
    private ImageConverter<PGMImageWrapper> converter;
    private PGMImageWrapper iPGMImage;
    private PBMImageWrapper iPBMImage;

    @BeforeEach
    public void setUp() {
        converter = new PGMImageConverter<>();
        PixelWrapper[][] pixels = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.5f, 0.5f, 0.5f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.2f, 0.2f, 0.2f), new PixelWrapper(0.6f, 0.6f, 0.6f), new PixelWrapper(0.8f, 0.8f, 0.8f) },
                { new PixelWrapper(0.1f, 0.1f, 0.1f), new PixelWrapper(0.3f, 0.3f, 0.3f), new PixelWrapper(0.9f, 0.9f, 0.9f) }
        };
        iPGMImage = new PGMImageWrapper(3,3, pixels,15);
        PixelWrapper[][] pixelsPBM = {
                { new PixelWrapper(0.0f, 0.0f, 1.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(1.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 0.0f, 0.0f) },
                { new PixelWrapper(0.0f, 1.0f, 0.0f), new PixelWrapper(1.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f) }
        };
        iPBMImage = new PBMImageWrapper(3,3, pixelsPBM);
    }

    @Test
    void testConvertPBMToPGM() {
        // Test per la conversione da PGM a PBM
        PGMImageWrapper result = converter.convertTo(iPBMImage);
        assertNotNull(result);  // Verifica che il risultato non sia null
        assertEquals(3, result.getWidth());  // Verifica la larghezza
        assertEquals(3, result.getHeight()); // Verifica l'altezza
        assertEquals(iPGMImage.getClass(), result.getClass());
        // Verifica che i colori siano stati modificati
    }

    @Test
    void testConvertPGMToPGM() {
        // Test per la conversione da PBM a PBM (deve restituire l'immagine originale)
        PGMImageWrapper result = converter.convertTo(iPGMImage);
        assertSame(iPGMImage, result);  // Verifica che il risultato sia lo stesso oggetto
    }
}
