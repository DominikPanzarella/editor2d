package ch.supsi.editor2d.utils.exceptions;

public class FileReadingException extends RuntimeException
{
    public FileReadingException(){
        super("");
    }

    public FileReadingException(final String message){
        super(message);
    }


}
