package ch.supsi.editor2d.service;

import ch.supsi.editor2d.controller.PreferencesController;
import ch.supsi.editor2d.controller.TranslationsController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferencesServiceTest {

    @Test
    public void testGetInstance() {
        PreferencesService instance = PreferencesService.getInstance();
        assertEquals(instance, PreferencesService.getInstance());
    }

    @Test
    public void testGetCurrentLanguage(){
        PreferencesService instance = PreferencesService.getInstance();
        instance.changeLanguage("it-CH");
        assertEquals("it-CH", instance.getCurrentLanguage());
    }

    @Test
    public void testChangeLanguage(){
        PreferencesController instance = PreferencesController.getInstance();
        instance.changeLanguage("en-US");
        assertEquals("en-US", instance.getCurrentLanguage());
    }

    @Test
    public void testGetPreference(){
        PreferencesController instance = PreferencesController.getInstance();
        String key = "language-tag";
        instance.changeLanguage("en-US");
        assertEquals("en-US", instance.getPreference(key));
    }
}
