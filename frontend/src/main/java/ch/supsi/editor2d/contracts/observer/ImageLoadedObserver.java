package ch.supsi.editor2d.contracts.observer;


import ch.supsi.editor2d.service.model.ImageWrapper;

/**
 * Any class implementing this interface becomes an observer
 * notified when an Image is loaded
 */
public interface ImageLoadedObserver {
    void updateImage(ImageWrapper toDraw);
}
