package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;
import ch.supsi.editor2d.contracts.receiver.ClearPipelineReceiver;

public class ClearPipelineCommand<T extends ClearPipelineReceiver<ClearPipelineHandler>> extends AbstractCommand<T>
{
    protected ClearPipelineCommand(T receiver){
        super(receiver);
    }

    public static <T extends ClearPipelineReceiver<ClearPipelineHandler>> ClearPipelineCommand<T> create(T receiver) throws InstantiationException {
        if(receiver==null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ClearPipelineCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.clearPipeline();
    }
}
