package ch.supsi.editor2d.repository;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class PreferencesPropertiesRepositoryTest {

    @Test
    public void testGetInstance() {
        PreferencesPropertiesRepository instance = PreferencesPropertiesRepository.getInstance();
        assertEquals(instance, PreferencesPropertiesRepository.getInstance());
    }

    @Test
    public void testGetPreferences() {
        PreferencesPropertiesRepository instance = PreferencesPropertiesRepository.getInstance();
        Properties preferences = instance.getPreferences();
        assertNotNull(preferences);
        String languageTag = preferences.getProperty("language-tag");
        assertTrue(languageTag.equals("it-CH") || languageTag.equals("en-US"));
    }

    @Test
    public void testSetPreferences() {
        PreferencesPropertiesRepository instance = PreferencesPropertiesRepository.getInstance();

        instance.setPreferences("it-CH");

        Properties updatedPreferences = new Properties();
        try (FileInputStream fis = new FileInputStream(instance.getUserPreferencesFilePath().toFile())) {
            updatedPreferences.load(fis);
        } catch (IOException e) {
            fail();
        }

        assertEquals("it-CH", updatedPreferences.getProperty("language-tag"));

        instance.setPreferences("en-US");

        updatedPreferences = new Properties();
        try (FileInputStream fis = new FileInputStream(instance.getUserPreferencesFilePath().toFile())) {
            updatedPreferences.load(fis);
        } catch (IOException e) {
            fail();
        }

        assertEquals("en-US", updatedPreferences.getProperty("language-tag"));
    }

    @Test
    public void testCreateUserPreferencesDirectory(){
        PreferencesPropertiesRepository instance = PreferencesPropertiesRepository.getInstance();
        assertNotNull(instance.createUserPreferencesDirectory());
    }

    @Test
    public void testCreateUserPreferencesFile(){
        PreferencesPropertiesRepository instance = PreferencesPropertiesRepository.getInstance();
        assert(instance.createUserPreferencesFile(instance.getPreferences()));
    }
}
