package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.RedoObserver;
import ch.supsi.editor2d.service.model.ImageWrapper;

import java.util.ArrayList;
import java.util.List;

public interface RedoObservable extends Observable
{
    List<RedoObserver> observers = new ArrayList<>();

    default void addRedoObserver(RedoObserver observer) {
        observers.add(observer);
    }

    default void removeRedoObserver(RedoObserver observer) {
        observers.remove(observer);
    }

    default void notifyRedoObservers(ImageWrapper data) {
        for (RedoObserver observer : observers) {
            observer.redo(data);
        }
    }
}
