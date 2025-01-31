package ch.supsi.editor2d.contracts.displayable;

import ch.supsi.editor2d.command.ClearPipelineCommand;
import ch.supsi.editor2d.command.RedoCommand;
import ch.supsi.editor2d.command.RunPipelineCommand;
import ch.supsi.editor2d.command.UndoCommand;
import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;
import ch.supsi.editor2d.contracts.handler.RedoHandler;
import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
import ch.supsi.editor2d.contracts.handler.UndoHandler;
import ch.supsi.editor2d.contracts.receiver.ClearPipelineReceiver;
import ch.supsi.editor2d.contracts.receiver.RedoReceiver;
import ch.supsi.editor2d.contracts.receiver.RunPipelineReceiver;
import ch.supsi.editor2d.contracts.receiver.UndoReceiver;

public interface PipelineDisplayable {
    <T extends RunPipelineCommand<? extends RunPipelineReceiver<RunPipelineHandler>>> void createRunPipelineBehaviour(T command);

    <T extends ClearPipelineCommand<? extends ClearPipelineReceiver<ClearPipelineHandler>>> void createClearPipelineBehaviour(T command);

    <T extends UndoCommand<? extends UndoReceiver<UndoHandler>>> void createUndoBehaviour(T command);

    <T extends RedoCommand<? extends RedoReceiver<RedoHandler>>> void createRedoBehaviour(T command);
}
