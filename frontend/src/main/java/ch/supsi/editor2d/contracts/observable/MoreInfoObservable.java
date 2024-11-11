package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.AboutObserver;
import ch.supsi.editor2d.contracts.observer.MoreInfoObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface MoreInfoObservable extends Observable
{
    List<MoreInfoObserver> observers = new ArrayList<>();

    default void addMoreInfoObserver(MoreInfoObserver observer) {
        observers.add(observer);
    }

    default void removeMoreInfoObserver(MoreInfoObserver observer) {
        observers.remove(observer);
    }

    default void notifyMoreInfoObservers(Map<String, List<String>> shortcuts) {
        for (MoreInfoObserver observer : observers) {
            observer.moreInfo(shortcuts);
        }
    }
}
