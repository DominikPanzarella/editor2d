package ch.supsi.editor2d.contracts.observer;

import java.util.List;
import java.util.Map;

public interface AboutObserver{
    void about(List<String> infos, Map<String, String> developers);
}
