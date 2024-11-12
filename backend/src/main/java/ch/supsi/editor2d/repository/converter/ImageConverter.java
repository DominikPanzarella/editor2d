package ch.supsi.editor2d.repository.converter;

import ch.supsi.editor2d.service.model.ImageWrapper;


public abstract class ImageConverter<T extends ImageWrapper>{
    public abstract T convertTo(ImageWrapper image);
}
