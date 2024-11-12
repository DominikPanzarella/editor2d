package ch.supsi.editor2d.service;

import ch.supsi.editor2d.controller.PreferencesServiceInterface;
import ch.supsi.editor2d.repository.PreferencesPropertiesRepository;

import java.nio.file.Path;
import java.util.Properties;

public class PreferencesService implements PreferencesServiceInterface {

    private static PreferencesService myself;

    private final PreferencesRepositoryInterface preferencesDao;

    private final Properties userPreferences;

    protected PreferencesService() {
        this.preferencesDao = PreferencesPropertiesRepository.getInstance();
        this.userPreferences = preferencesDao.getPreferences();
    }

    public static PreferencesService getInstance() {
        if (myself == null) {
            myself = new PreferencesService();
        }

        return myself;
    }

    @Override
    public String getCurrentLanguage() {
        return userPreferences.getProperty("language-tag");
    }

    @Override
    public Object getPreference(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }

        if (userPreferences == null) {
            return null;
        }

        return userPreferences.get(key);
    }
    public void changeLanguage(String languageTag) {
        preferencesDao.setPreferences(languageTag);
    }

    @Override
    public Path getUserPreferencesDirectoryPath() {
        return preferencesDao.getUserPreferencesDirectoryPath();
    }

}
