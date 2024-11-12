package ch.supsi.editor2d.contracts.handler;

import java.util.List;
import java.util.Map;

public interface PropertiesHandler {
    List<String> loadInfos();
    Map<String,String> loadDevelopers();

    Map<String,String> loadShortcuts();
}
