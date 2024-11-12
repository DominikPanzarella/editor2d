package ch.supsi.editor2d.model;

import ch.supsi.editor2d.contracts.observable.FeedbackObservable;
import ch.supsi.editor2d.contracts.observable.Observable;
import ch.supsi.editor2d.controller.PreferencesController;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.contracts.handler.ChangeLanguageHandler;

public class LanguageModel implements Observable, ChangeLanguageHandler, FeedbackObservable {

    private String currentLanguageTag;
    private static LanguageModel mySelf;
    private TranslationsController translationsController;
    private PreferencesController preferencesController;

    private LanguageModel(){
        super();
        this.translationsController = TranslationsController.getInstance();
        this.preferencesController = PreferencesController.getInstance();
    }

    public static LanguageModel getInstance(){
        if(mySelf == null){
            mySelf = new LanguageModel();
        }
        return mySelf;
    }

    @Override
    public void changeLanguage(String languageTag) {
        preferencesController.changeLanguage(languageTag);
        this.currentLanguageTag = languageTag;
        this.notifyFeedbackObservers(translationsController.translate("label.languageChanged"));
    }
}
