package ch.supsi.editor2d.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TranslationsServiceTest {

    @Test
    public void testGetInstance() {
        TranslationsService instance = TranslationsService.getInstance();
        assertEquals(instance, TranslationsService.getInstance());
    }

    @Test
    public void testIsSupportedLanguage(){
        TranslationsService instance = TranslationsService.getInstance();
        assert(instance.isSupportedLanguageTag("en-US"));
        assertFalse(instance.isSupportedLanguageTag("ru-ru"));
    }

    @Test
    public void testChangeLanguage(){
        TranslationsService instance = TranslationsService.getInstance();
        assert(instance.changeLanguage("en-US"));

        String key = "label.welcome";
        instance.changeLanguage("ru-ru");
        assertEquals("Welcome to Editor2D", instance.translate(key));
    }

    @Test
    public void testTranslate(){
        TranslationsService instance = TranslationsService.getInstance();
        String key = "label.welcome";
        instance.changeLanguage("en-US");
        assertEquals("Welcome to Editor2D", instance.translate(key));
        instance.changeLanguage("it-CH");
        assertEquals("Benvenuto su Editor2D", instance.translate(key));
    }
}
