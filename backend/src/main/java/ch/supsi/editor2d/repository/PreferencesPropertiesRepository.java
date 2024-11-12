package ch.supsi.editor2d.repository;

import ch.supsi.editor2d.service.PreferencesRepositoryInterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PreferencesPropertiesRepository implements PreferencesRepositoryInterface {

    private static final String defaultPreferencesPath = "/default-user-preferences.properties";

    private static final String userHomeDirectory = System.getProperty("user.home");

    private static final String preferencesDirectory = ".editor2d";

    private static final String preferencesFile = "preferences.properties";

    public static PreferencesPropertiesRepository dao;

    private static Properties userPreferences;

    // protected default constructor to avoid a new instance being requested from clients
    protected PreferencesPropertiesRepository() {}

    // singleton instantiation of this data access object
    // guarantees only a single instance exists in the life of the application
    public static PreferencesPropertiesRepository getInstance() {
        if (dao == null) {
            dao = new PreferencesPropertiesRepository();
        }

        return dao;
    }

    public Path getUserPreferencesDirectoryPath() {
        return Path.of(userHomeDirectory, preferencesDirectory);
    }

    private boolean userPreferencesDirectoryExists() {
        return Files.exists(this.getUserPreferencesDirectoryPath());
    }

    protected Path createUserPreferencesDirectory() {
        try {
            return Files.createDirectories(this.getUserPreferencesDirectoryPath());

        } catch (IOException ignoredForDemoPurposes) {
            ;
        }

        return null;
    }

    Path getUserPreferencesFilePath() {
        return Path.of(userHomeDirectory, preferencesDirectory, preferencesFile);
    }

    private boolean userPreferencesFileExists() {
        return Files.exists(this.getUserPreferencesFilePath());
    }

    protected boolean createUserPreferencesFile(Properties defaultPreferences) {
        if (defaultPreferences == null) {
            return false;
        }

        if (!userPreferencesDirectoryExists()) {
            // user preferences directory does not exist
            // create it
            this.createUserPreferencesDirectory();
        }

        if (!userPreferencesFileExists()) {
            // user preferences file does not exist
            // create it
            try {
                // create user preferences file (with default preferences)
                FileOutputStream outputStream = new FileOutputStream(String.valueOf(this.getUserPreferencesFilePath()));
                defaultPreferences.store(outputStream, null);
                return true;

            } catch (IOException ignoredForDemoPurposes) {
                return false;
            }
        }

        return true;
    }

    private Properties loadDefaultPreferences() {
        Properties defaultPreferences = new Properties();
        try {
            InputStream defaultPreferencesStream = this.getClass().getResourceAsStream(defaultPreferencesPath);
            defaultPreferences.load(defaultPreferencesStream);

        } catch (IOException ignored) {
            ;
        }

        // return the properties object with the loaded preferences
        return defaultPreferences;
    }

    private Properties loadPreferences(Path path) {
        Properties preferences = new Properties();
        try {
            preferences.load(new FileInputStream(String.valueOf(path)));

        } catch (IOException ignoredForDemoPurposes) {
            return null;
        }

        return preferences;
    }

    @Override
    public Properties getPreferences() {
        if (userPreferences != null) {
            return userPreferences;
        }

        if (userPreferencesFileExists()) {
            userPreferences = this.loadPreferences(this.getUserPreferencesFilePath());
            return userPreferences;
        }

        userPreferences = this.loadDefaultPreferences();
        boolean rv = this.createUserPreferencesFile(userPreferences);

        // return the properties object with the loaded preferences
        return userPreferences;
    }

    public void setPreferences(String languageTag){
        // Carica le preferenze esistenti
        Properties existingPreferences = getPreferences();

        // Modifica la preferenza della lingua
        existingPreferences.setProperty("language-tag", languageTag);

        // Salva le preferenze modificate nel file
        try {
            FileOutputStream outputStream = new FileOutputStream(String.valueOf(getUserPreferencesFilePath()));
            existingPreferences.store(outputStream, null);
        } catch (IOException e) {
            return;
        }
    }

}
