package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ToggleFiltersObserver;
import ch.supsi.editor2d.contracts.observer.ToggleRedoButtonObserver;

import java.util.ArrayList;
import java.util.List;

public interface ToggleFiltersObservable {
    List<ToggleFiltersObserver> observers = new ArrayList<>();

    default void addFiltersObserver(ToggleFiltersObserver observer) {
        observers.add(observer);
    }

    default void removeFiltersObserver(ToggleFiltersObserver observer) {
        observers.remove(observer);
    }

    default void notifyFiltersObservers() {
        for (ToggleFiltersObserver observer : observers) {
            observer.toggleFiltersButtons();
        }
    }
}
