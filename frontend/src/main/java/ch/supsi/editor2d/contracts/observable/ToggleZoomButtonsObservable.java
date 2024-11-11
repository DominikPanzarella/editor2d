package ch.supsi.editor2d.contracts.observable;



import ch.supsi.editor2d.contracts.observer.ToggleZoomButtonsObserver;

import java.util.ArrayList;
import java.util.List;

public interface ToggleZoomButtonsObservable {
    List<ToggleZoomButtonsObserver> observers = new ArrayList<>();

    default void addZoomObserver(ToggleZoomButtonsObserver observer) {
        observers.add(observer);
    }

    default void removeZoomObserver(ToggleZoomButtonsObserver observer) {
        observers.remove(observer);
    }

    default void notifyZoomObservers() {
        for (ToggleZoomButtonsObserver observer : observers) {
            observer.toggleZoomButtons();
        }
    }
}
