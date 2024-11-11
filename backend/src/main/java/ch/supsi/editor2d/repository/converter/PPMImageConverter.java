package ch.supsi.editor2d.repository.converter;

import ch.supsi.editor2d.service.model.*;

public class PPMImageConverter<T extends ImageWrapper> extends ImageConverter<PPMImageWrapper>  {
    @Override
    public PPMImageWrapper convertTo(ImageWrapper image) {
        if(image instanceof PBMImageWrapper) {
            return new PPMImageWrapper(image.getHeight(), image.getWidth(), image.getData(), 1);
        } else if(image instanceof PGMImageWrapper imageToSave) {
            return new PPMImageWrapper(image.getHeight(), image.getWidth(), image.getData(), imageToSave.getGrayScale());
        } else if(image instanceof PPMImageWrapper ppmImage) {
            return ppmImage;
        }
        return null;
    }

}
