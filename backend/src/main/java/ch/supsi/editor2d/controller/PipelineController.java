package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.algorithm.Applicable;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.pipeline.PipelineService;
import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;
import ch.supsi.editor2d.utils.exceptions.ImageProcessingException;

public class PipelineController
{
    private final IPipelineService pipelineService;

    private static PipelineController myself;

    public static PipelineController getInstance(){
        if(myself==null)
            myself = new PipelineController(PipelineService.getInstance());
        return myself;
    }

    protected PipelineController(IPipelineService pipelineService){
        this.pipelineService = pipelineService;
    }

    public ImageWrapper runPipeline(ImageWrapper imageWrapper) throws EmptyPipeline, ImageProcessingException {
        return pipelineService.run(imageWrapper);
    }

    public void addFilter(Applicable filter){
        pipelineService.add(filter);
    }

    public void clearAllPipeline(){
        pipelineService.clear();
    }



}
