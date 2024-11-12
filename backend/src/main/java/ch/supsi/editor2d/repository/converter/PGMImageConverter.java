package ch.supsi.editor2d.repository.converter;

import ch.supsi.editor2d.service.model.*;

public class PGMImageConverter <T extends ImageWrapper> extends ImageConverter<PGMImageWrapper> {
    private float[] formula = {0.299f, 0.587f, 0.114f};

    @Override
    public PGMImageWrapper convertTo(ImageWrapper image) {
        if(image instanceof PBMImageWrapper) {
            return new PGMImageWrapper(image.getHeight(), image.getWidth(), image.getData(), 1);
        } else if(image instanceof PGMImageWrapper pgmImage) {
            return pgmImage;
        } else if(image instanceof PPMImageWrapper imageToSave){
            PixelWrapper[][] newPixels = new PixelWrapper[image.getHeight()][image.getWidth()];
            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {

                    PixelWrapper pixel = image.getData()[i][j];

                    float finalValue = pixel.getRed()*formula[0] + pixel.getGreen()*formula[1] + pixel.getBlue()*formula[2];
                    pixel.setBlueColor(finalValue);
                    pixel.setGreenColor(finalValue);
                    pixel.setRedColor(finalValue);
                    newPixels[i][j] = pixel;
                }
            }
            return new PGMImageWrapper(image.getHeight(), image.getWidth(), newPixels, imageToSave.getColorScale());
        }
        return null;
    }
}
