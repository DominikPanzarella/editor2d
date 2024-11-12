package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.FilterSelectedObserver;

import java.util.ArrayList;
import java.util.List;

public interface FilterSelectedObservable {
    List<FilterSelectedObserver> observers = new ArrayList<>();


    default void addObserver(FilterSelectedObserver observer){
        observers.add(observer);
    }

    default void removeObserver(FilterSelectedObserver observer){
        observers.remove(observer);
    }


    default void notifyObservers(String filter){
        for(FilterSelectedObserver observer : observers)
            observer.filterSelected(filter);
    }
}
