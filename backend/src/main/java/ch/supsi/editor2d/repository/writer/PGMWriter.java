package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.repository.converter.ImageConverter;
import ch.supsi.editor2d.repository.converter.PGMImageConverter;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PGMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public class PGMWriter extends PNMWriter {
    private final ImageConverter<PGMImageWrapper> converter = new PGMImageConverter<>();

    @Override
    protected boolean setHeader(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        String magicNumber = "p2";
        int width = toSave.getWidth();
        int height = toSave.getHeight();
        PGMImageWrapper toSavePGM = converter.convertTo(toSave);
        String toWrite = magicNumber + "\n" + width + " " + height + "\n" + toSavePGM.getGrayScale()+"\n";
        writer.write(toWrite);
        return true;
    }

    @Override
    protected boolean setPixels(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        int width = toSave.getWidth();
        int height = toSave.getHeight();
        int grayScale;

        PGMImageWrapper toSavePGM = converter.convertTo(toSave);

        grayScale = toSavePGM.getGrayScale();
        PixelWrapper[][] toWrite = toSavePGM.getData();
        int y;
        for (y = 0; y < height; y++) {
            PixelWrapper[] pixelWrapperValues = toWrite[y];
            String[] pixelValues = new String[width];
            StringBuilder lineBuilder = new StringBuilder();
            for(int i = 0; i < width; i++){
                PixelWrapper pixelWrapper = pixelWrapperValues[i];
                float pixelValue = pixelWrapper.getBlue();
                int denormalizedGrayValue = (int) (pixelValue * grayScale);
                pixelValues[i] = "" + denormalizedGrayValue;
                if(i!=(pixelWrapperValues.length-1))
                    lineBuilder.append(pixelValues[i]).append(" ");
                else
                    lineBuilder.append(pixelValues[i]);


            }
            String line = lineBuilder.toString();
            writer.write(line+"\n");
        }
        return true;
    }

    @Override
    protected boolean canHandle(String extension) {
        return extension.equals("pgm");
    }
}
