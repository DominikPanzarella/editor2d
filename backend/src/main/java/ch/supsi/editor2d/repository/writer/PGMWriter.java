package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.*;

import java.io.BufferedWriter;
import java.io.IOException;

public class PGMWriter extends PNMWriter {

    private PGMImageWrapper toSavePGM;
    @Override
    protected boolean setHeader(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        PPMImageWrapper data = (PPMImageWrapper) toSave;
        String magicNumber = "P2";
        int width = toSave.getWidth();
        int height = toSave.getHeight();

        toSavePGM = new PGMImageWrapper(height,width,toSave.getData(), data.getColorScale());

        String toWrite = magicNumber + "\n" + width + " " + height + "\n" + toSavePGM.getGrayScale()+"\n";
        writer.write(toWrite);
        return true;
    }

    @Override
    protected boolean setPixels(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        int width = toSave.getWidth();
        int height = toSave.getHeight();
        int grayScale;


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
