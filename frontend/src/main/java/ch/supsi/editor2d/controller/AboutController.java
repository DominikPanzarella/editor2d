package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.contracts.handler.AboutHandler;
import ch.supsi.editor2d.contracts.handler.PropertiesHandler;
import ch.supsi.editor2d.contracts.receiver.AboutReceiver;

public class AboutController implements AboutReceiver<AboutHandler>
{
    private AboutHandler model;
    private static AboutController myself;
    private PropertiesHandler properties;

    protected AboutController(AboutHandler model, PropertiesHandler properties) {
        this.model = model;
        this.properties = properties;
    }


    public static AboutController getInstance(AboutHandler model, PropertiesHandler properties) {
        if (myself == null) {
            myself = new AboutController(model, properties);
        }
        return myself;
    }


    @Override
    public void about() {
        model.about(properties.loadInfos(), properties.loadDevelopers());
    }
}
