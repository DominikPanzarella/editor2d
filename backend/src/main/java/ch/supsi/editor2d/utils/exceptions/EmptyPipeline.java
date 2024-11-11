package ch.supsi.editor2d.utils.exceptions;

public class EmptyPipeline extends RuntimeException
{
    public EmptyPipeline(){
        super("Pipeline empty!");
    }

    public EmptyPipeline(final String message){
        super(message);
    }

}
