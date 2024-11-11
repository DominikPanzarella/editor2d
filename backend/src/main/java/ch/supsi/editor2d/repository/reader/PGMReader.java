package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.IOException;

public class PGMReader extends PNMReader {
    private int maxGrayValue;


    @Override
    protected String[] getHeader(BufferedReader reader) throws IOException {
        String[] headerInformation = checkLine(reader).split(" ");
        return headerInformation;
    }

    @Override
    protected PixelWrapper[][] getPixels(BufferedReader reader,String[] header) throws IOException {
        final int width = Integer.parseInt(header[0]);
        final int height = Integer.parseInt(header[1]);
        maxGrayValue = Integer.parseInt(checkLine(reader));

        PixelWrapper[][] pixels = new PixelWrapper[height][width];

        int y;
        for (y = 0; y < height; y++) {
            String[] pixelValues = checkLine(reader).split(" ");

            for(var p : pixelValues)
                System.out.print(p+" ");

            if (pixelValues.length != width) {
                throw new FileReadingException("Invalid number of columns!");
            }

            for (int x = 0; x < width; x++) {
                int grayValue = Integer.parseInt(pixelValues[x]);
                float normalizedGrayValue = (((float) 255 / maxGrayValue) / 255.0f) * grayValue; // Normalize between 0 e 1
                pixels[y][x] = new PixelWrapper(normalizedGrayValue, normalizedGrayValue, normalizedGrayValue);
            }

            System.out.println();
        }

        if (y != height) {
            throw new IOException("Invalid number of rows!");
        }

        return pixels;
    }

    @Override
    protected ImageWrapper createImageWrapper(String[] header, PixelWrapper[][] pixels) {
        return new PGMImageWrapper(Integer.parseInt(header[1]), Integer.parseInt(header[0]), pixels, maxGrayValue);
    }

    @Override
    protected boolean canHandle(String magicNumber) {
        return magicNumber.equalsIgnoreCase("P2");
    }
}
