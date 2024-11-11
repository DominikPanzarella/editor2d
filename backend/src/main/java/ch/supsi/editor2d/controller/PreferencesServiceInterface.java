package ch.supsi.editor2d.controller;

import java.nio.file.Path;

public interface PreferencesServiceInterface {

    String getCurrentLanguage();

    Object getPreference(String key);
    void changeLanguage(String languageTag);
    Path getUserPreferencesDirectoryPath();

}
