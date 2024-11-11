package ch.supsi.editor2d.contracts.observer;

import ch.supsi.editor2d.contracts.handler.AddFilterHandler;
import ch.supsi.editor2d.service.algorithm.Filter;

import java.util.List;

public interface FiltersLoadedObserver {
    void filtersLoaded(List<Filter> filters, AddFilterHandler handler);
}
