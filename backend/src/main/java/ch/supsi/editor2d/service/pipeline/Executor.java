package ch.supsi.editor2d.service.pipeline;

import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;

public interface Executor<T,K>
{
    T run(T input) throws EmptyPipeline;
    void add(K filter);
    void clear();

}
