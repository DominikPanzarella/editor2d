package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Writer {
    boolean write(final String path, String extension, ImageWrapper toSave) throws IOException;
}
