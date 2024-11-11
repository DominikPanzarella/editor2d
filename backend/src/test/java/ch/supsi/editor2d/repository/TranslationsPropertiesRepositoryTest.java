package ch.supsi.editor2d.repository;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranslationsPropertiesRepositoryTest {

    @Test
    public void testGetInstance() {
        TranslationsPropertiesRepository instance = TranslationsPropertiesRepository.getInstance();
        assertEquals(instance, TranslationsPropertiesRepository.getInstance());
    }

    @Test
    public void testGetSupportedLanguageTags(){
        // This method tests implicitly also loadSupportedLanguageTags that is a private method
        TranslationsPropertiesRepository instance = TranslationsPropertiesRepository.getInstance();
        List<String> supportedTags = instance.getSupportedLanguageTags();
        assertEquals(2, supportedTags.size());
        assertTrue(supportedTags.contains("en-US"));
        assertTrue(supportedTags.contains("it-CH"));
    }

    @Test
    public void testGetTranslations(){
        TranslationsPropertiesRepository instance = TranslationsPropertiesRepository.getInstance();

        Locale englishLocale = Locale.forLanguageTag("en-US");
        Properties englishTranslations = instance.getTranslations(englishLocale);
        assertEquals("Welcome to Editor2D", englishTranslations.getProperty("label.welcome"));

        Locale italianLocale = Locale.forLanguageTag("it-CH");
        Properties italianTranslations = instance.getTranslations(italianLocale);
        assertEquals("Benvenuto su Editor2D", italianTranslations.getProperty("label.welcome"));

        Locale unsupportedLocale = Locale.forLanguageTag("ru-ru");
        Properties fallbackTranslations = instance.getTranslations(unsupportedLocale);
        assertEquals("Welcome to Editor2D", fallbackTranslations.getProperty("label.welcome"));
    }

}
