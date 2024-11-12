package ch.supsi.editor2d.model;

import ch.supsi.editor2d.contracts.handler.MoreInfoHandler;
import ch.supsi.editor2d.contracts.observable.MoreInfoObservable;
import ch.supsi.editor2d.contracts.observable.Observable;
import ch.supsi.editor2d.controller.TranslationsController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MoreInfoModel implements Observable, MoreInfoObservable, MoreInfoHandler
{

    private static MoreInfoModel myself;

    private TranslationsController translationsController;

    protected MoreInfoModel(){
        translationsController = TranslationsController.getInstance();
    }

    public static MoreInfoModel getInstance(){
        if(myself==null)
            myself = new MoreInfoModel();

        return myself;

    }


    @Override
    public void moreInfo(Map<String, String> shortcuts) {
        Map<String, List<String>> resultMap = new HashMap<>();

        shortcuts.entrySet().forEach(entry -> {

            final String shortcutName = entry.getKey();
            final String shortcutCommand = entry.getValue();
            final String description = translationsController.translate(shortcutName);
            List<String> details = new LinkedList<>();
            details.add(shortcutCommand);
            details.add(description);

            resultMap.putIfAbsent(shortcutName, details);
        });

        notifyMoreInfoObservers(resultMap);
    }
}
