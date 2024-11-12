package ch.supsi.editor2d.contracts.handler;

import ch.supsi.editor2d.service.algorithm.Filter;

public interface AddFilterHandler extends Handler {
    void addFilter(Filter filter);
}
