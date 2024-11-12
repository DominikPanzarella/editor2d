package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
public interface RunPipelineReceiver<T extends RunPipelineHandler> extends Receiver
{
    void runPipeline();
}
