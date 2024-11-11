package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ZoomInObserver;

import java.util.ArrayList;
import java.util.List;

public interface ZoomInObservable extends Observable
{
    List<ZoomInObserver> observers = new ArrayList<>();

    default void addOZoomInObserver(ZoomInObserver observer) {
        observers.add(observer);
    }

    default void removeZoomInObserver(ZoomInObserver observer) {
        observers.remove(observer);
    }

    default void notifyZoomInObservers() {
        for (ZoomInObserver observer : observers) {
            observer.zoomIn();
        }
    }
}
