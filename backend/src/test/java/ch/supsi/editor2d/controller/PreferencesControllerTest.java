package ch.supsi.editor2d.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PreferencesControllerTest {

    @Test
    public void testGetInstance() {
        PreferencesController instance = PreferencesController.getInstance();
        assertEquals(instance, PreferencesController.getInstance());
    }

    @Test
    public void testGetCurrentLanguage(){
        PreferencesController instance = PreferencesController.getInstance();
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
