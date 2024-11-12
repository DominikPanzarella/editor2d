package ch.supsi.editor2d.service.model;

import ch.supsi.editor2d.repository.reader.PNMReader;

/**
 * Consist of two parts, a header and the image data.
 * Header: - PPM identifier(P1)
 *         - Width
 *         - Height
 *         - Max Values of the colour components for the pixels (GrayScale) --> [0, 255]
 * Data
 */
public class PGMImageWrapper extends ImageWrapper
{
    private final int grayScale;
    public PGMImageWrapper(final int height,final int width,final PixelWrapper[][] data,final int greyScale) {
        super(height, width, data);
        this.grayScale = greyScale;
    }
    private PGMImageWrapper(PGMImageWrapper imageWrapper){
        super(imageWrapper);
        this.grayScale = imageWrapper.grayScale;
    }

    public int getGrayScale(){
        return grayScale;
    }

    @Override
    public ImageWrapper clone() {
        return new PGMImageWrapper(this);
    }
}
