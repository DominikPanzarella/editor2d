package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.model.ImageWrapper;

import java.io.File;
import java.io.IOException;

public interface IImageService {

    ImageWrapper loadImage(final String path) throws IOException;

    boolean exportImage(final String path, final String extension, ImageWrapper image) throws IOException;
}
