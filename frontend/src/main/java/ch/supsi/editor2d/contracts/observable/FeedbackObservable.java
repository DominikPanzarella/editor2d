package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.FeedbackObserver;

import java.util.ArrayList;
import java.util.List;

public interface FeedbackObservable extends Observable{
    List<FeedbackObserver> observers = new ArrayList<>();

    default public void addObserver(FeedbackObserver observer) {
        observers.add(observer);
    }

    default void removeObserver(FeedbackObserver observer) {
        observers.remove(observer);
    }

    default void notifyFeedbackObservers(String feedback) {
        for (FeedbackObserver observer : observers) {
            observer.updateFeedback(feedback);
        }
    }
}
