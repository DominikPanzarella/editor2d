package ch.supsi.editor2d.service.pipeline;

import ch.supsi.editor2d.controller.IPipelineService;
import ch.supsi.editor2d.service.algorithm.Applicable;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;
import ch.supsi.editor2d.utils.exceptions.ImageProcessingException;


/**
 * Can be simplified
 */
public class PipelineService implements IPipelineService, Executor<ImageWrapper,Applicable> {
    private static PipelineService myself;
    private Executor<ImageWrapper, Applicable> pipelineExecutor;

    public static PipelineService getInstance(){
        if(myself==null)
            myself = new PipelineService();
        return myself;
    }

    protected PipelineService(){
        pipelineExecutor = new PipelineExecutor();
    }

    @Override
    public ImageWrapper run(ImageWrapper input) throws EmptyPipeline, ImageProcessingException {
        return pipelineExecutor.run(input);
    }

    @Override
    public void add(Applicable filter) {
        pipelineExecutor.add(filter);
    }

    @Override
    public void clear() {
        pipelineExecutor.clear();
    }
}
