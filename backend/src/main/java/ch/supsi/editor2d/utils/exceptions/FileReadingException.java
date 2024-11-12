package ch.supsi.editor2d.utils.exceptions;

public class FileReadingException extends RuntimeException
{
    public FileReadingException(){
        super("Invalid file extension!");
    }

    public FileReadingException(final String message){
        super(message);
    }


}
