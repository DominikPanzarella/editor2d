package ch.supsi.editor2d.service;

import ch.supsi.editor2d.service.model.ImageWrapper;

import java.io.File;
import java.io.IOException;

public interface IImageRepository
{
    ImageWrapper handleLoadImage(final String path, final String extension) throws IOException;

    boolean handleExportImage(final String path, final String extension, ImageWrapper image) throws IOException;

}
