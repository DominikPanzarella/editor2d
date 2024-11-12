package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.UndoObserver;
import ch.supsi.editor2d.service.model.ImageWrapper;

import java.util.ArrayList;
import java.util.List;

public interface UndoObservable extends Observable{
    List<UndoObserver> observers = new ArrayList<>();

    default void addUndoObserver(UndoObserver observer) {
        observers.add(observer);
    }

    default void removeUndoObserver(UndoObserver observer) {
        observers.remove(observer);
    }

    default void notifyUndoObservers(ImageWrapper data) {
        for (UndoObserver observer : observers) {
            observer.undo(data);
        }
    }
}
