package ch.supsi.editor2d.repository.writer;

import ch.supsi.editor2d.service.model.ImageWrapper;

import java.io.*;

public abstract class PNMWriter implements Writer {
    protected PNMWriter successor;

    public void setSuccessor(PNMWriter successor){
        this.successor = successor;
    }

    @Override
    public boolean write(String path, String extension, ImageWrapper toSave) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            if (canHandle(extension)) {
                return helperWriter(writer, toSave);
            } else if (successor != null) {
                return successor.write(path, extension, toSave);
            } else {
                throw new IOException("Can't handle this file!");
            }
        }
    }

    private boolean helperWriter(BufferedWriter writer, ImageWrapper toSave) throws IOException {
        return setHeader(writer, toSave) & setPixels(writer, toSave);
    }

    protected abstract boolean setHeader(BufferedWriter writer, ImageWrapper toSave) throws IOException;
    protected abstract boolean setPixels(BufferedWriter writer, ImageWrapper toSave) throws IOException;


    protected abstract boolean canHandle(final String extension);

}
