package ch.supsi.editor2d.service.pipeline;

import ch.supsi.editor2d.service.algorithm.Applicable;
import ch.supsi.editor2d.service.algorithm.Filter;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;
import ch.supsi.editor2d.utils.exceptions.ImageProcessingException;

import java.util.LinkedList;
import java.util.List;

public class PipelineExecutor implements Executor<ImageWrapper, Applicable> {
    private final List<Applicable> filtersList;


    public PipelineExecutor(){
        filtersList = new LinkedList<>();
    }


    @Override
    public ImageWrapper run(ImageWrapper input) throws EmptyPipeline, ImageProcessingException{
        if(filtersList.isEmpty())
            throw new EmptyPipeline();
        if(input == null)
            throw new ImageProcessingException("Input image cannot be null!");
        ImageWrapper output = input.clone();
        for(Applicable toApply : filtersList){
            output = toApply.apply(output);
        }
        return output;
    }

    @Override
    public void add(Applicable filter) {
        filtersList.add(filter);
    }

    @Override
    public void clear() {
        filtersList.clear();
    }
}
