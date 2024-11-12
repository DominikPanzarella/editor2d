package ch.supsi.editor2d.service.model;

/**
 * Consist of two parts, a header and the image data.
 * Header: - PPM identifier(P3 o P6)
 *         - Width
 *         - Height
 *         - Max Values of the colour components for the pixels (ColorScale) --> [0, 255]
 * Data
 */
public class PPMImageWrapper extends ImageWrapper
{
    private final int colorScale;


    public PPMImageWrapper(final int height, final int width,final PixelWrapper[][] data, final int colorScale) {
        super(height, width, data);
        this.colorScale = colorScale;
    }

    private PPMImageWrapper(PPMImageWrapper imageWrapper){
        super(imageWrapper);
        this.colorScale = imageWrapper.colorScale;
    }

    public int getColorScale() {
        return colorScale;
    }

    @Override
    public ImageWrapper clone() {
        return new PPMImageWrapper(this);
    }
}
