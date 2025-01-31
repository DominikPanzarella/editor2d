package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PPMReader extends PNMReader {
    private int maxColorValue;

    @Override
    protected String[] getHeader(BufferedReader reader) throws IOException {
        String[] headerInformation = checkLine(reader).split(" ");
        return headerInformation;
    }

    @Override
    protected PixelWrapper[][] getPixels(BufferedReader reader, String[] header) throws IOException, FileReadingException {
        final int width = Integer.parseInt(header[0]);
        final int height = Integer.parseInt(header[1]);
        maxColorValue = Integer.parseInt(checkLine(reader));

        PixelWrapper[][] pixels = new PixelWrapper[height][width];

        // Lista per accumulare tutti i valori dei pixel
        List<Integer> pixelValues = new ArrayList<>();

        // Leggi le righe finché non abbiamo tutti i pixel
        String line;
        while ((line = checkLine(reader)) != null) {
            String[] values = line.split("\\s+");
            for (String val : values) {
                if (!val.trim().isEmpty()) {
                    pixelValues.add(Integer.parseInt(val));
                }
            }

            // Se abbiamo raggiunto o superato il numero totale di pixel richiesto, interrompi la lettura
            if (pixelValues.size() >= width * height * 3) {
                break;
            }
        }

        // Verifica se il numero di pixel è corretto
        if (pixelValues.size() != width * height * 3) {
            throw new FileReadingException(translationsController.translate("label.invalidNumberPixels"));
        }

        // Popola la matrice di pixel
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int red = pixelValues.get(index++);
                int green = pixelValues.get(index++);
                int blue = pixelValues.get(index++);

                float normalizedRed = (((float) 255 / maxColorValue) / 255.0f) * red;
                float normalizedGreen = (((float) 255 / maxColorValue) / 255.0f) * green;
                float normalizedBlue = (((float) 255 / maxColorValue) / 255.0f) * blue;

                pixels[y][x] = new PixelWrapper(normalizedRed, normalizedGreen, normalizedBlue);
            }
        }

        return pixels;
    }

    @Override
    protected ImageWrapper createImageWrapper(String[] header, PixelWrapper[][] pixels) {
        return new PPMImageWrapper(Integer.parseInt(header[1]), Integer.parseInt(header[0]), pixels, maxColorValue);
    }

    @Override
    protected boolean canHandle(String magicNumber) {
        return magicNumber.equalsIgnoreCase("P3");
    }
}
