package ch.supsi.editor2d.repository.converter;

import ch.supsi.editor2d.service.model.*;

public class PBMImageConverter<T extends ImageWrapper> extends ImageConverter<PBMImageWrapper> {
    private float[] formula = {0.299f, 0.587f, 0.114f};

    @Override
    public PBMImageWrapper convertTo(ImageWrapper image) {
        if(image instanceof PGMImageWrapper) {
            PixelWrapper[][] newPixels = new PixelWrapper[image.getHeight()][image.getWidth()];
            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {
                    PixelWrapper pixel = image.getData()[i][j];
                    float finalValue = pixel.getBlue()>0.5f?1:0;
                    pixel.setBlueColor(finalValue);
                    pixel.setGreenColor(finalValue);
                    pixel.setRedColor(finalValue);
                    newPixels[i][j] = pixel;
                }
            }
            return new PBMImageWrapper(image.getHeight(), image.getWidth(), newPixels);
        } else if(image instanceof PBMImageWrapper pbmImage) {
            return pbmImage;
        } else if(image instanceof PPMImageWrapper) {
            PixelWrapper[][] newPixels = new PixelWrapper[image.getHeight()][image.getWidth()];
            for(int i = 0; i < image.getHeight(); i++) {
                for(int j = 0; j < image.getWidth(); j++) {
                    PixelWrapper pixel = image.getData()[i][j];
                    float finalValue = (pixel.getRed()*formula[0] + pixel.getGreen()*formula[1] + pixel.getBlue()*formula[2])>0.5f?1:0;
                    pixel.setBlueColor(finalValue);
                    pixel.setGreenColor(finalValue);
                    pixel.setRedColor(finalValue);
                    newPixels[i][j] = pixel;
                }
            }
            return new PBMImageWrapper(image.getHeight(), image.getWidth(), newPixels);
        }
        return null;
    }
}
