package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ToggleEmptyPipelineObserver;

import java.util.ArrayList;
import java.util.List;

public interface ToggleEmptyPipelineObservable {
    List<ToggleEmptyPipelineObserver> observers = new ArrayList<>();

    default void addEmptyPipelineObserver(ToggleEmptyPipelineObserver observer) {
        observers.add(observer);
    }

    default void removeEmptyPipelineObserver(ToggleEmptyPipelineObserver observer) {
        observers.remove(observer);
    }

    default void notifyEmptyPipelineObservers() {
        for (ToggleEmptyPipelineObserver observer : observers) {
            observer.toggleEmptyPipelineButton();
        }
    }
}
