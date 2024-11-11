package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.PreferencesService;
import ch.supsi.editor2d.service.TranslationsService;

import java.util.ResourceBundle;

public class TranslationsController {

    private static TranslationsController myself;

    private final TranslationsServiceInterface translationsModel;

    private final PreferencesServiceInterface preferencesModel;

    protected TranslationsController() {
        this.preferencesModel = PreferencesService.getInstance();
        this.translationsModel = TranslationsService.getInstance();

        String currentLanguage = preferencesModel.getCurrentLanguage();
        this.translationsModel.changeLanguage(currentLanguage);
    }

    public static TranslationsController getInstance() {
        if (myself == null) {
            myself = new TranslationsController();
        }

        return myself;
    }

    /**
     * Translate the given key
     *
     * @param key
     *
     * @return String
     */
    public String translate(String key) {

        return this.translationsModel.translate(key);
    }
    public boolean changeLanguage(String languageTag) {
        return this.translationsModel.changeLanguage(languageTag);
    }
    public ResourceBundle getResourceBundle(){
        return this.translationsModel.getResourceBundle();
    }
}
