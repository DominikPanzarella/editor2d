package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.repository.reader.PBMReader;
import ch.supsi.editor2d.repository.reader.PNMReader;
import ch.supsi.editor2d.service.algorithm.Filter;
import ch.supsi.editor2d.service.algorithm.FlipSideSide;
import ch.supsi.editor2d.service.algorithm.Negative;
import ch.supsi.editor2d.service.algorithm.RotateClockwise;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;
import ch.supsi.editor2d.utils.exceptions.ImageProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineControllerTest
{
    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private ImageWrapper image;
    PipelineController pipelineController;


    public PipelineControllerTest() throws URISyntaxException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        PNMReader parser = new PBMReader();
        image = parser.read(JPBMAsciiPath);
        pipelineController = PipelineController.getInstance();
        pipelineController.clearAllPipeline();
    }

    @Test
    public void emptyPipeline(){
        assertThrows(EmptyPipeline.class, () -> pipelineController.runPipeline(image));
    }

    @Test
    public void nullInputImage(){
        Filter flipSideSide = new FlipSideSide();
        pipelineController.addFilter(flipSideSide);
        assertThrows(ImageProcessingException.class, () -> pipelineController.runPipeline(null));
    }

    @Test
    public void applyPipeline(){
        Filter flipSideSide = new FlipSideSide();
        Filter rotateClockwise = new RotateClockwise();
        Filter negative = new Negative();

        pipelineController.addFilter(flipSideSide);
        pipelineController.addFilter(rotateClockwise);
        pipelineController.addFilter(negative);

        ImageWrapper outputImage = pipelineController.runPipeline(image);

        assertNotEquals(image,outputImage);

        assertNotNull(outputImage);
    }

}
