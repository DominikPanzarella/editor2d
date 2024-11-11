package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PPMReader extends PNMReader {
    private int maxColorValue;



    @Override
    protected String[] getHeader(BufferedReader reader) throws IOException {
        String[] headerInformation = checkLine(reader).split(" ");
        return headerInformation;
    }

    @Override
    protected PixelWrapper[][] getPixels(BufferedReader reader,String[] header) throws IOException {
        final int width = Integer.parseInt(header[0]);
        final int height = Integer.parseInt(header[1]);
        maxColorValue = Integer.parseInt(checkLine(reader));

        PixelWrapper[][] pixels = new PixelWrapper[height][width];

        int y;
        for (y = 0; y < height; y++) {
            String[] pixelValues = checkLine(reader).split(" ");

            if (pixelValues.length != width * 3) {
                throw new FileReadingException("Invalid number of pixel values!");
            }

            for (int x = 0; x < width; x++) {
                int red = Integer.parseInt(pixelValues[x * 3]);
                int green = Integer.parseInt(pixelValues[x * 3 + 1]);
                int blue = Integer.parseInt(pixelValues[x * 3 + 2]);

                float normalizedRed = (((float) 255 / maxColorValue) / 255.0f) * red;
                float normalizedGreen = (((float) 255 / maxColorValue) / 255.0f) * green;
                float normalizedBlue = (((float) 255 / maxColorValue) / 255.0f) * blue;

                pixels[y][x] = new PixelWrapper(normalizedRed, normalizedGreen, normalizedBlue);
            }
        }

        if (y != height) {
            throw new IOException("Invalid number of rows!");
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
