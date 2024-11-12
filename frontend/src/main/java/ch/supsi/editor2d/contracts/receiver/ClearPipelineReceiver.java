package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;

public interface ClearPipelineReceiver<T extends ClearPipelineHandler> extends Receiver{

     void clearPipeline();
}
