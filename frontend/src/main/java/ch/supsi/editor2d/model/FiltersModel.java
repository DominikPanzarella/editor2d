package ch.supsi.editor2d.model;

import ch.supsi.editor2d.contracts.observable.*;
import ch.supsi.editor2d.contracts.observer.ClearPipelineObserver;
import ch.supsi.editor2d.contracts.observer.ToggleEmptyPipelineObserver;
import ch.supsi.editor2d.contracts.observer.ToggleFiltersObserver;
import ch.supsi.editor2d.contracts.observer.ToggleRunButtonsObserver;
import ch.supsi.editor2d.controller.FiltersController;
import ch.supsi.editor2d.controller.PipelineController;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.contracts.handler.AddFilterHandler;
import ch.supsi.editor2d.service.algorithm.Filter;

import java.util.ArrayList;
import java.util.List;

public class FiltersModel implements Observable, FiltersLoadedObservable, FilterSelectedObservable, FeedbackObservable, AddFilterHandler, ClearPipelineObserver, ToggleRunButtonsObservable, ToggleEmptyPipelineObservable {
    private static FiltersModel myself;
    private List<Filter> filters;
    private boolean isEmpty = true;
    private FiltersController filtersController = FiltersController.getInstance();
    private TranslationsController translationsController;
    private PipelineController pipelineController;


    protected FiltersModel(){
        this.translationsController = TranslationsController.getInstance();
    }

    public static FiltersModel getInstance(){
        if(myself==null)
            myself = new FiltersModel();
        return myself;
    }

    public void loadFilters(){
        filters = filtersController.getFiltersName();
        pipelineController = PipelineController.getInstance();
        List<String> filtersNames = new ArrayList<>();
        filters.forEach(filter -> filtersNames.add(filter.getName()));
        notifyObservers(filters, this);
    }

    @Override
    public void addFilter(Filter filter) {
        pipelineController.addFilter(filter);
        notifyObservers(filter.getName());
        notifyFeedbackObservers(filter.getName()+" "+translationsController.translate("label.filterAdded"));
        if(isEmpty){
            isEmpty = false;
            notifyeRunButtonsObservers();
            notifyEmptyPipelineObservers();
        }
    }

    @Override
    public void clearPipeline() {
        isEmpty = true;
        notifyeRunButtonsObservers();
        notifyEmptyPipelineObservers();
    }

}
