package ch.supsi.editor2d.service.model;

import ch.supsi.editor2d.repository.reader.PNMReader;

import java.util.Arrays;

/**
 * Check the following site for PNM images: https://paulbourke.net/dataformats/ppm/
*/
public abstract class ImageWrapper {
    private int height;                           // Image Height
    private int width;                           // Image Width
    private PixelWrapper[][] data;                // Image Pixels


    public ImageWrapper(final int height, final int width, PixelWrapper[][] data){
        this.height = height;
        this.width = width;
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public PixelWrapper[][] getData() {
        return Arrays.stream(data).map(PixelWrapper[]::clone).toArray($ -> data.clone());
    }

    protected ImageWrapper(ImageWrapper imageWrapper){
        this.height = imageWrapper.getHeight();
        this.width = imageWrapper.getWidth();
        this.data = imageWrapper.getData();
    }

    public abstract ImageWrapper clone();

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setData(PixelWrapper[][] data) {
        this.data = data;
    }
}
