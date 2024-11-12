package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ExitObserver;

import java.util.ArrayList;
import java.util.List;

public interface ExitObservable extends Observable{
    List<ExitObserver> observers = new ArrayList<>();

    default void addExitObserver(ExitObserver observer) {
        observers.add(observer);
    }

    default void removeExitObserver(ExitObserver observer) {
        observers.remove(observer);
    }

    default void notifyExitObservers() {
        for (ExitObserver observer : observers) {
            observer.exit();
        }
    }
}
