package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;

public abstract class Rotate extends Filter
{

    public Rotate(String name) {
        super(name);
    }

    @Override
    public ImageWrapper apply(ImageWrapper image){
        ImageWrapper newImage = image.clone();

        final int newHeight = newImage.getWidth();
        final int newWidth = newImage.getHeight();

        PixelWrapper[][] oldMatrix = image.getData();
        PixelWrapper[][] newMatrix = rotate(oldMatrix);

        newImage.setData(newMatrix);
        newImage.setHeight(newHeight);
        newImage.setWidth(newWidth);

        return newImage;
    }

    protected abstract PixelWrapper[][] rotate(PixelWrapper[][] matrix);



}
