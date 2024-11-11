package ch.supsi.editor2d.contracts.handler;

import ch.supsi.editor2d.service.algorithm.Filter;

public interface FiltersListHandler extends Handler {
    void handleAddFilter(Filter filter);
}
