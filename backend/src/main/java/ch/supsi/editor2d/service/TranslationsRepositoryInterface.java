package ch.supsi.editor2d.service;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public interface TranslationsRepositoryInterface {

    List<String> getSupportedLanguageTags();

    Properties getTranslations(Locale locale);
    ResourceBundle getResourceBundle();
}
