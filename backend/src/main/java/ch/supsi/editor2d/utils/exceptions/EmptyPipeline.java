package ch.supsi.editor2d.utils.exceptions;

import ch.supsi.editor2d.controller.TranslationsController;

public class EmptyPipeline extends RuntimeException
{
    public EmptyPipeline(){
        super("");
    }

    public EmptyPipeline(final String message){
        super(message);
    }

}
