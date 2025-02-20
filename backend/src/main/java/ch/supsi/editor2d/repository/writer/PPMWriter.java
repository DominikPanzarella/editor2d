package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PPMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public class PPMWriter extends PNMWriter {

    PPMImageWrapper toSavePPM ;
    @Override
    protected boolean setHeader(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        String magicNumber = "P3";
        int width = toSave.getWidth();
        int height = toSave.getHeight();

        toSavePPM = (PPMImageWrapper) toSave;
        String toWrite = magicNumber + "\n" + width + " " + height + "\n" + toSavePPM.getColorScale()+"\n";
        writer.write(toWrite);
        return true;
    }

    @Override
    protected boolean setPixels(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        int width = toSave.getWidth();
        int height = toSave.getHeight();
        int colorScale;

        colorScale = toSavePPM.getColorScale();
        PixelWrapper[][] toWrite = toSavePPM.getData();
        int y;
        for (y = 0; y < height; y++) {
            PixelWrapper[] pixelWrapperValues = toWrite[y];
            String[] pixelValues = new String[width];
            StringBuilder lineBuilder = new StringBuilder();
            for(int i = 0; i < width; i++){
                PixelWrapper pixelWrapper = pixelWrapperValues[i];
                float pixelValueRed = pixelWrapper.getRed();
                float pixelValueGreen = pixelWrapper.getGreen();
                float pixelValueBlue = pixelWrapper.getBlue();
                int denormalizedColorValueRed = (int) (pixelValueRed * colorScale);
                int denormalizedColorValueGreen = (int) (pixelValueGreen * colorScale);
                int denormalizedColorValueBlue = (int) (pixelValueBlue * colorScale);

                pixelValues[i] = "" + denormalizedColorValueRed + " "+ denormalizedColorValueGreen + " " + denormalizedColorValueBlue;                ;
                if(i!=(pixelWrapperValues.length-1))
                    lineBuilder.append(pixelValues[i]).append("\t");
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
        return extension.equals("ppm");
    }
}
