package ch.supsi.editor2d.service;

import java.nio.file.Path;
import java.util.Properties;

public interface PreferencesRepositoryInterface {

    Properties getPreferences();

    void setPreferences(String languageTag);

    Path getUserPreferencesDirectoryPath();
}