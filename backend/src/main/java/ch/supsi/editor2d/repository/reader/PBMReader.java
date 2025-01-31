package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PBMReader extends PNMReader {

    @Override
    protected boolean canHandle(String magicNumber) {
        return magicNumber.equalsIgnoreCase("P1");
    }

    @Override
    protected String[] getHeader(BufferedReader reader) throws IOException {
        // Estrae il primo set di informazioni dall'header
        String[] headerInformation = checkLine(reader).split(" ");
        if (headerInformation.length != 2) {
            throw new FileReadingException(translationsController.translate("label.invalidHeader"));
        }
        return headerInformation;
    }

    @Override
    protected PixelWrapper[][] getPixels(BufferedReader reader, String[] header) throws IOException {
        final int width = Integer.parseInt(header[0]);
        final int height = Integer.parseInt(header[1]);
        PixelWrapper[][] pixels = new PixelWrapper[height][width];

        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);
        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);

        // Accumula i pixel in una lista temporanea
        List<Integer> pixelValues = new ArrayList<>();
        String line;

        while ((line = checkLine(reader)) != null) {
            for (String value : line.split("\\s+")) {
                pixelValues.add(Integer.parseInt(value));
            }
        }


        // Verifica il numero totale di pixel
        if (pixelValues.size() != width * height) {
            throw new FileReadingException(translationsController.translate("label.invalidDimensions"));
        }

        // Popola la matrice rispettando le dimensioni
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixelValues.get(index++);
                pixels[y][x] = (pixel == 0) ? white : black;
            }
        }

        return pixels;
    }

    @Override
    protected ImageWrapper createImageWrapper(String[] header, PixelWrapper[][] pixels) {
        // Crea un'immagine PBM basata sui dati
        return new PPMImageWrapper(
                Integer.parseInt(header[1]), // Altezza
                Integer.parseInt(header[0]), // Larghezza
                pixels,
                1 // Profondità colore (1 per PBM)
        );
    }
}
