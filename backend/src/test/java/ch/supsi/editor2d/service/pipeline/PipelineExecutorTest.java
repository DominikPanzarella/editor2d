package ch.supsi.editor2d.service.pipeline;

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

public class PipelineExecutorTest
{
    private final String JPBMAsciiPath = Paths.get((getClass().getClassLoader().getResource("PBM/j.pbm")).toURI()).toString();
    private ImageWrapper image;
    PipelineExecutor pipelineExecutor;

    public PipelineExecutorTest() throws URISyntaxException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        PNMReader parser = new PBMReader();
        image = parser.read(JPBMAsciiPath);
        pipelineExecutor = new PipelineExecutor();
        pipelineExecutor.clear();
    }

    @Test
    public void emptyPipeline(){
        assertThrows(EmptyPipeline.class, () -> pipelineExecutor.run(image));
    }

    @Test
    public void nullInputImage(){
        Filter flipSideSide = new FlipSideSide();
        pipelineExecutor.add(flipSideSide);
        assertThrows(ImageProcessingException.class, () -> pipelineExecutor.run(null));
    }


    @Test
    public void applyPipeline(){
       Filter flipSideSide = new FlipSideSide();
       Filter rotateClockwise = new RotateClockwise();
       Filter negative = new Negative();

       pipelineExecutor.add(flipSideSide);
       pipelineExecutor.add(rotateClockwise);
       pipelineExecutor.add(negative);

       ImageWrapper outputImage = pipelineExecutor.run(image);

       assertNotEquals(image,outputImage);

       assertNotNull(outputImage);
    }



}
