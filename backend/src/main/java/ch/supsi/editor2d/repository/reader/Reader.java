package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Reader
{
    //TODO: possibile soluzione -> nel reader passare il path del file, internamente viene istanziato un BufferedReader
    ImageWrapper read(final String path) throws FileReadingException, FileNotFoundException, IOException;
}
