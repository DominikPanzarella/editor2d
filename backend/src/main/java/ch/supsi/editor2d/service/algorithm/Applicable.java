package ch.supsi.editor2d.service.algorithm;

import ch.supsi.editor2d.service.model.ImageWrapper;

public interface Applicable
{
    ImageWrapper apply(ImageWrapper image);
}
