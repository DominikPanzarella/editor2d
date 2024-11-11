package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.service.algorithm.Applicable;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.pipeline.Executor;

public interface IPipelineService extends Executor<ImageWrapper, Applicable> {
}
