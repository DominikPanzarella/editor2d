package ch.supsi.editor2d.controller;

import java.util.ResourceBundle;

public interface TranslationsServiceInterface {

    boolean isSupportedLanguageTag(String languageTag);

    boolean changeLanguage(String languageTag);

    String translate(String key);
    ResourceBundle getResourceBundle();
}
