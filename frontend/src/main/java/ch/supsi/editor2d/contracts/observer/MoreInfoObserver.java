package ch.supsi.editor2d.contracts.observer;

import java.util.List;
import java.util.Map;
import java.util.Observer;

public interface MoreInfoObserver
{
    void moreInfo(Map<String, List<String>> shortcuts);
}
