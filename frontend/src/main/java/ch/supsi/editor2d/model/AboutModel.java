package ch.supsi.editor2d.model;

import ch.supsi.editor2d.contracts.observable.AboutObservable;
import ch.supsi.editor2d.contracts.handler.AboutHandler;
import ch.supsi.editor2d.controller.TranslationsController;

import java.util.List;
import java.util.Map;


public class AboutModel implements AboutHandler, AboutObservable {

    private static AboutModel myself;


    protected AboutModel(){
    }

    public static AboutModel getInstance(){
        if(myself==null)
            myself = new AboutModel();

        return myself;

    }


    @Override
    public void about(List<String> infos, Map<String, String> developers) {
        notifyObservers(infos, developers);
    }
}

