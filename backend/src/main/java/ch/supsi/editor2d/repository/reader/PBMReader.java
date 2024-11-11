package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PBMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PBMReader extends PNMReader
{


    @Override
    protected boolean canHandle(String magicNumber) {
        return magicNumber.equalsIgnoreCase("P1");
    }

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

        PixelWrapper black = new PixelWrapper(0.0f, 0.0f, 0.0f);
        PixelWrapper white = new PixelWrapper(1.0f, 1.0f, 1.0f);
        int y;
        for(y=0; y<height; y++){
            String[] pixelValues = checkLine(reader).split(" ");

            if(pixelValues.length != width)
                throw new FileReadingException("Invalid number of columns!");

            for(int x=0; x<width; x++){
                int pixel = Integer.parseInt(pixelValues[x]);
                pixels[y][x] = pixel==0 ? white : black;
            }
        }

        if(y != height)
            throw new FileNotFoundException("Invalid number of rows!");

        return pixels;
    }

    @Override
    protected ImageWrapper createImageWrapper(String[] header, PixelWrapper[][] pixels) {
        return new PBMImageWrapper(Integer.parseInt(header[1]), Integer.parseInt(header[0]), pixels);
    }



}
