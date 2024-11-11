package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.AboutObserver;
import ch.supsi.editor2d.contracts.observer.ClearPipelineObserver;

import java.util.ArrayList;
import java.util.List;

public interface ClearPipelineObservable extends Observable
{
    List<ClearPipelineObserver> observers = new ArrayList<>();

    default public void addClearPipelineObserver(ClearPipelineObserver observer) {
        observers.add(observer);
    }

    default void removeObserver(AboutObserver observer) {
        observers.remove(observer);
    }

    default void notifyClearPipelineObservers() {
        for (ClearPipelineObserver observer : observers) {
            observer.clearPipeline();
        }
    }
}
