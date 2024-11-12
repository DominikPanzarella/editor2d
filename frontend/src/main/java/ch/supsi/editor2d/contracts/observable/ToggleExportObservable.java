package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ToggleExportObserver;
import ch.supsi.editor2d.contracts.observer.ToggleFiltersObserver;

import java.util.ArrayList;
import java.util.List;

public interface ToggleExportObservable {
    List<ToggleExportObserver> observers = new ArrayList<>();

    default void addExportObserver(ToggleExportObserver observer) {
        observers.add(observer);
    }

    default void removeExportObserver(ToggleExportObserver observer) {
        observers.remove(observer);
    }

    default void notifyExportObservers() {
        for (ToggleExportObserver observer : observers) {
            observer.toggleExportButton();
        }
    }
}
