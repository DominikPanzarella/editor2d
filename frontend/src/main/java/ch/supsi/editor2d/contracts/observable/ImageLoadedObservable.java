package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.ImageLoadedObserver;
import ch.supsi.editor2d.service.model.ImageWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that implements the Notifier (Subject) logic in the Observer pattern.
 * Mantains a list of observers (ImageLoadedObserver) that will be notified
 * when an image is loaded.
 */

public interface ImageLoadedObservable extends Observable
{
    List<ImageLoadedObserver> observers = new ArrayList<>();


    default void addObserver(ImageLoadedObserver observer){
        observers.add(observer);
    }

    default void removeObserver(ImageLoadedObserver observer){
        observers.remove(observer);
    }


    default void notifyObservers(ImageWrapper toDraw){
        for(ImageLoadedObserver observer : observers)
            observer.updateImage(toDraw);
    }
}
