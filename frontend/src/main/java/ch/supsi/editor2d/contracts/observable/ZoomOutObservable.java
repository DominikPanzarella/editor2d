package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ZoomOutObserver;

import java.util.ArrayList;
import java.util.List;

public interface ZoomOutObservable extends Observable
{
    List<ZoomOutObserver> observers = new ArrayList<>();

    default void addOZoomOutObserver(ZoomOutObserver observer) {
        observers.add(observer);
    }

    default void removeZoomOutObserver(ZoomOutObserver observer) {
        observers.remove(observer);
    }

    default void notifyZoomOutObservers() {
        for (ZoomOutObserver observer : observers) {
            observer.zoomOut();
        }
    }
}
