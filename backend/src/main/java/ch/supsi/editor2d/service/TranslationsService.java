package ch.supsi.editor2d.service;

import ch.supsi.editor2d.controller.TranslationsServiceInterface;
import ch.supsi.editor2d.repository.TranslationsPropertiesRepository;

import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class TranslationsService implements TranslationsServiceInterface {

    private static TranslationsService myself;

    private final TranslationsRepositoryInterface translationsDao;

    private final List<String> supportedLanguageTags;

    private Properties translations;

    protected TranslationsService() {
        this.translationsDao = TranslationsPropertiesRepository.getInstance();
        this.supportedLanguageTags = translationsDao.getSupportedLanguageTags();
    }

    public static TranslationsService getInstance() {
        if (myself == null) {
            myself = new TranslationsService();
        }

        return myself;
    }

    @Override
    public boolean isSupportedLanguageTag(String languageTag) {
        if (!this.supportedLanguageTags.contains(languageTag)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean changeLanguage(String languageTag) {
        this.translations = translationsDao.getTranslations(Locale.forLanguageTag(languageTag));
        return this.translations != null;
    }

    @Override
    public String translate(String key) {
        return this.translations.getProperty(key);
    }
    public ResourceBundle getResourceBundle() {
        return this.translationsDao.getResourceBundle();
    }
}
