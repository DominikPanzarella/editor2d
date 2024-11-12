package ch.supsi.editor2d.repository.converter;
import ch.supsi.editor2d.service.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PBMConverterTest {
    private PBMImageConverter<ImageWrapper> converter;
    private PGMImageWrapper iPGMImage;
    private PBMImageWrapper iPBMImage;

    @BeforeEach
    public void setUp() {
        converter = new PBMImageConverter<>();
        PixelWrapper[][] pixels = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.5f, 0.5f, 0.5f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.2f, 0.2f, 0.2f), new PixelWrapper(0.6f, 0.6f, 0.6f), new PixelWrapper(0.8f, 0.8f, 0.8f) },
                { new PixelWrapper(0.1f, 0.1f, 0.1f), new PixelWrapper(0.3f, 0.3f, 0.3f), new PixelWrapper(0.9f, 0.9f, 0.9f) }
        };
        iPGMImage = new PGMImageWrapper(3,3, pixels,15);
        PixelWrapper[][] pixelsPBM = {
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(1.0f, 1.0f, 1.0f) },
                { new PixelWrapper(1.0f, 1.0f, 1.0f), new PixelWrapper(0.0f, 0.0f, 0.0f), new PixelWrapper(0.0f, 0.0f, 0.0f) }
        };
        iPBMImage = new PBMImageWrapper(3,3, pixelsPBM);
    }

    @Test
    void testConvertPGMToPBM() {
        // Test per la conversione da PGM a PBM
        PBMImageWrapper result = converter.convertTo(iPGMImage);
        assertNotNull(result);  // Verifica che il risultato non sia null
        assertEquals(3, result.getWidth());  // Verifica la larghezza
        assertEquals(3, result.getHeight()); // Verifica l'altezza
        assertEquals(iPBMImage.getClass(), result.getClass());
        // Verifica che i colori siano stati modificati
    }

    @Test
    void testConvertPBMToPBM() {
        // Test per la conversione da PBM a PBM (deve restituire l'immagine originale)
        PBMImageWrapper result = converter.convertTo(iPBMImage);
        assertSame(iPBMImage, result);  // Verifica che il risultato sia lo stesso oggetto
    }
}
