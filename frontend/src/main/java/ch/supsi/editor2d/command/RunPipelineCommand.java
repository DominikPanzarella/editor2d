package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
import ch.supsi.editor2d.contracts.receiver.RunPipelineReceiver;


public class RunPipelineCommand<T extends RunPipelineReceiver<RunPipelineHandler>> extends AbstractCommand<T>
{

    protected RunPipelineCommand(T receiver) {
        super(receiver);
    }

    public static <T extends RunPipelineReceiver<RunPipelineHandler>> RunPipelineCommand<T> create(T receiver) throws InstantiationException {
        if(receiver==null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new RunPipelineCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.runPipeline();
    }
}
