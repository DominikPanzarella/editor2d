package ch.supsi.editor2d.utils.exceptions;


/*
*
* Custom exception for handling invalid RGB color
*
*/
public class InvalidColorValueException extends RuntimeException
{
    public InvalidColorValueException(){
        super("");
    }

    public InvalidColorValueException(final String message){
        super(message);
    }


}
