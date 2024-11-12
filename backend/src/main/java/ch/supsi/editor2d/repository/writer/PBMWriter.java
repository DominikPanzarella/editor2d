package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.repository.converter.ImageConverter;
import ch.supsi.editor2d.repository.converter.PBMImageConverter;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PBMImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

import java.io.BufferedWriter;
import java.io.IOException;

public class PBMWriter extends PNMWriter {
    private final ImageConverter<PBMImageWrapper> converter = new PBMImageConverter<>();

    @Override
    protected boolean setHeader(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        String magicNumber = "P1";
        int width = toSave.getWidth();
        int height = toSave.getHeight();
        String toWrite = magicNumber + "\n" + width + " " + height +"\n";
        writer.write(toWrite);
        return true;
    }

    @Override
    protected boolean setPixels(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        int width = toSave.getWidth();
        int height = toSave.getHeight();

        PBMImageWrapper toSavePBM = converter.convertTo(toSave);

        PixelWrapper[][] toWrite = toSavePBM.getData();
        int y;
        for (y = 0; y < height; y++) {
            PixelWrapper[] pixelWrapperValues = toWrite[y];
            String[] pixelValues = new String[width];
            StringBuilder lineBuilder = new StringBuilder();
            for(int i = 0; i < width; i++){
                PixelWrapper pixelWrapper = pixelWrapperValues[i];
                int pixelValue = (int)pixelWrapper.getBlue();
                pixelValues[i] = "" + pixelValue;
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
        return extension.equals("pbm");
    }
}
