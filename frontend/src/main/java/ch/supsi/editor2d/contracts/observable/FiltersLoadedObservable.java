package ch.supsi.editor2d.contracts.observable;

import ch.supsi.editor2d.contracts.observer.FiltersLoadedObserver;
import ch.supsi.editor2d.contracts.handler.AddFilterHandler;
import ch.supsi.editor2d.service.algorithm.Filter;

import java.util.ArrayList;
import java.util.List;

public interface FiltersLoadedObservable {
    List<FiltersLoadedObserver> observers = new ArrayList<>();


    default void addObserver(FiltersLoadedObserver observer){
        observers.add(observer);
    }

    default void removeObserver(FiltersLoadedObserver observer){
        observers.remove(observer);
    }


    default void notifyObservers(List<Filter> filters, AddFilterHandler handler){
        for(FiltersLoadedObserver observer : observers)
            observer.filtersLoaded(filters, handler);
    }
}
