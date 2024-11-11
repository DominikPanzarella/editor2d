package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.PreferencesService;

import java.nio.file.Path;

public class PreferencesController {

    private static PreferencesController myself;

    private final PreferencesServiceInterface preferencesModel;

    protected PreferencesController() {
         this.preferencesModel = PreferencesService.getInstance();
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }

        return myself;
    }

    /**
     * Return the value for the given key
     *
     * @param key
     * @return String
     */
    public Object getPreference(String key) {
        return this.preferencesModel.getPreference(key);
    }
    public void changeLanguage(String languageTag){
        this.preferencesModel.changeLanguage(languageTag);
    }
    public String getCurrentLanguage() {
        return this.preferencesModel.getCurrentLanguage();
    }
    public Path getUserPreferencesDirectoryPath(){
        return preferencesModel.getUserPreferencesDirectoryPath();
    }
}
