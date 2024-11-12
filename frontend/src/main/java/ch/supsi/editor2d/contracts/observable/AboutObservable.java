package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.AboutObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AboutObservable extends Observable
{
    List<AboutObserver> observers = new ArrayList<>();

    default public void addObserver(AboutObserver observer) {
        observers.add(observer);
    }

    default void removeObserver(AboutObserver observer) {
        observers.remove(observer);
    }

    default void notifyObservers(List<String> infos, Map<String, String> developers) {
        for (AboutObserver observer : observers) {
            observer.about(infos, developers);
        }
    }
}
