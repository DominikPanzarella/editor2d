package ch.supsi.editor2d.service.model;

import ch.supsi.editor2d.repository.reader.PNMReader;

/**
 * Consist of two parts, a header and the image data.
 * Header: - PPM identifier(P2 or P5)
 *         - Width
 *         - Height

 * Data
 */
public class PBMImageWrapper extends ImageWrapper
{
    public PBMImageWrapper(final int height,final  int width,final PixelWrapper[][] data) {
        super(height, width, data);

    }

    @Override
    public ImageWrapper clone() {
        return new PBMImageWrapper(this);
    }

    private PBMImageWrapper(PBMImageWrapper imageWrapper){
        super(imageWrapper);
    }

}
