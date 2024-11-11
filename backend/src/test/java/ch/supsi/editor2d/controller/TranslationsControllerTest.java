package ch.supsi.editor2d.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TranslationsControllerTest {
    @Test
    public void testGetInstance() {
        TranslationsController instance = TranslationsController.getInstance();
        assertEquals(instance, TranslationsController.getInstance());
    }

    @Test
    public void testChangeLanguage(){
        TranslationsController instance = TranslationsController.getInstance();
        assert(instance.changeLanguage("en-US"));

        String key = "label.welcome";
        instance.changeLanguage("ru-ru");
        assertEquals("Welcome to Editor2D", instance.translate(key));
    }

    @Test
    public void testTranslate(){
        TranslationsController instance = TranslationsController.getInstance();
        String key = "label.welcome";
        instance.changeLanguage("en-US");
        assertEquals("Welcome to Editor2D", instance.translate(key));
        instance.changeLanguage("it-CH");
        assertEquals("Benvenuto su Editor2D", instance.translate(key));
    }
}
