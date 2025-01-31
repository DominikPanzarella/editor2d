package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ToggleRunButtonsObserver;
import ch.supsi.editor2d.contracts.observer.ToggleUndoButtonObserver;

import java.util.ArrayList;
import java.util.List;

public interface ToggleRunButtonsObservable {
    List<ToggleRunButtonsObserver> observers = new ArrayList<>();

    default void addeRunButtonsObserver(ToggleRunButtonsObserver observer) {
        observers.add(observer);
    }

    default void removeRunButtonsObserver(ToggleRunButtonsObserver observer) {
        observers.remove(observer);
    }

    default void notifyeRunButtonsObservers(boolean state) {
        for (ToggleRunButtonsObserver observer : observers) {
            observer.toggleRunButtons(state);
        }
    }}
