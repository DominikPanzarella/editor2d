package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PGMReader extends PNMReader {
    private int maxGrayValue;

    @Override
    protected String[] getHeader(BufferedReader reader) throws IOException {
        String[] headerInformation = checkLine(reader).split(" ");
        return headerInformation;
    }

    @Override
    protected PixelWrapper[][] getPixels(BufferedReader reader, String[] header) throws IOException {
        final int width = Integer.parseInt(header[0]);
        final int height = Integer.parseInt(header[1]);
        PixelWrapper[][] pixels = new PixelWrapper[height][width];

        maxGrayValue = Integer.parseInt(checkLine(reader));

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
                float normalizedGrayValue = (((float) 255 / maxGrayValue) / 255.0f) * pixel; // Normalize between 0 e 1
                pixels[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }
        }

        return pixels;
    }

    @Override
    protected ImageWrapper createImageWrapper(String[] header, PixelWrapper[][] pixels) {
        return new PPMImageWrapper(Integer.parseInt(header[1]), Integer.parseInt(header[0]), pixels, maxGrayValue);
    }

    @Override
    protected boolean canHandle(String magicNumber) {
        return magicNumber.equalsIgnoreCase("P2");
    }
}
