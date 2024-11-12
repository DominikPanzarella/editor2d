package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

public class FlipSideSide extends Flip{


    public FlipSideSide() {
        super("FlipSideSide");
    }

    @Override
    public ImageWrapper apply(ImageWrapper image) {
        PixelWrapper[][] dataToBeFiltered = image.getData();
        PixelWrapper[][] filteredData = new PixelWrapper[image.getHeight()][image.getWidth()];

        ImageWrapper newImage = image.clone();

        for(int i = 0; i < newImage.getHeight(); i++) {
            for(int j = 0; j < newImage.getWidth(); j++) {
                filteredData[i][j] = dataToBeFiltered[i][newImage.getWidth() - j -1];
            }
        }

        newImage.setData(filteredData);
        return newImage;
    }
}
