package ch.supsi.editor2d.repository.reader;

import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.service.model.*;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;

import java.io.*;

public abstract class PNMReader implements Reader
{
    protected PNMReader successor;
    protected TranslationsController translationsController = TranslationsController.getInstance();

    public void setSuccessor(PNMReader successor){
        this.successor = successor;
    }


    public ImageWrapper read(final String path) throws FileReadingException, IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(path))){
            final String magicNumber = getMagicNumber(reader);
            if(canHandle(magicNumber)){
                return helperReader(reader,magicNumber);
            }
            else if(successor!=null){
                return successor.read(path);
            }else{
                throw new FileReadingException(translationsController.translate("label.cantHandle"));
            }
        }
    }

    private ImageWrapper helperReader(BufferedReader reader, final String magicNumber) throws IOException {
        String[] header = getHeader(reader);
        PixelWrapper[][] pixels = getPixels(reader,header);
        return createImageWrapper(header, pixels);
    }


    public String getMagicNumber(BufferedReader reader) throws FileReadingException,IOException {
        return reader.readLine();
    }
    protected abstract String[] getHeader(BufferedReader reader) throws IOException;
    protected abstract PixelWrapper[][] getPixels(BufferedReader reader,String[] header) throws IOException, FileReadingException;
    protected abstract ImageWrapper createImageWrapper(final String[] header, PixelWrapper[][] pixels);


    private final static String COMMENT_SYMBOL = "#";

    protected String checkLine(BufferedReader reader) throws FileReadingException, IOException {
        String line = reader.readLine();
        if (line != null) {
            line = line.replaceAll("[\s\n\t]+", " ");

            if (line.startsWith(" ")) {
                line = line.substring(1);
            }

            if (line.startsWith(COMMENT_SYMBOL)) {
                return checkLine(reader);
            }

            int commentIndex = line.indexOf(COMMENT_SYMBOL);
            if (commentIndex != -1) {
                // A comment is present
                return line.substring(0, commentIndex).trim();
            }

            // No comment present
            return line;
        } else {
            return null;
        }
    }

    protected abstract boolean canHandle(final String magicNumber);


}
