package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ToggleUndoButtonObserver;
import ch.supsi.editor2d.service.model.ImageWrapper;

import java.util.ArrayList;
import java.util.List;

public interface ToggleUndoButtonObservable {
    List<ToggleUndoButtonObserver> observers = new ArrayList<>();

    default void addUndoButtonObserver(ToggleUndoButtonObserver observer) {
        observers.add(observer);
    }

    default void removeUndoButtonObserver(ToggleUndoButtonObserver observer) {
        observers.remove(observer);
    }

    default void notifyUndoButtonObservers(boolean state) {
        for (ToggleUndoButtonObserver observer : observers) {
            observer.toggleUndoButton(state);
        }
    }
}
