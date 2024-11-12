package ch.supsi.editor2d.controller;

import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;
import ch.supsi.editor2d.contracts.handler.RedoHandler;
import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
import ch.supsi.editor2d.contracts.handler.UndoHandler;
import ch.supsi.editor2d.contracts.receiver.ClearPipelineReceiver;
import ch.supsi.editor2d.contracts.receiver.RedoReceiver;
import ch.supsi.editor2d.contracts.receiver.RunPipelineReceiver;
import ch.supsi.editor2d.contracts.receiver.UndoReceiver;

public class PipelineListController implements RunPipelineReceiver<RunPipelineHandler>, ClearPipelineReceiver<ClearPipelineHandler>, UndoReceiver<UndoHandler>, RedoReceiver<RedoHandler>
{
    private RunPipelineHandler runPipelineModel;
    private ClearPipelineHandler clearPipelineHandler;
    private UndoHandler undoHandler;
    private RedoHandler redoHandler;
    private static PipelineListController myself;

    protected PipelineListController(RunPipelineHandler runPipelineModel, ClearPipelineHandler clearPipelineHandler, UndoHandler undoHandler, RedoHandler redoHandler) {
        this.runPipelineModel = runPipelineModel;
        this.clearPipelineHandler = clearPipelineHandler;
        this.undoHandler = undoHandler;
        this.redoHandler = redoHandler;
    }

    public static PipelineListController getInstance(RunPipelineHandler runPipelineModel, ClearPipelineHandler clearPipelineHandler, UndoHandler undoHandler, RedoHandler redoHandler) {
        if (myself == null) {
            myself = new PipelineListController(runPipelineModel, clearPipelineHandler, undoHandler,redoHandler);
        }
        return myself;
    }

    @Override
    public void runPipeline() {
        runPipelineModel.runPipeline();
    }

    @Override
    public void clearPipeline() {
        clearPipelineHandler.clearPipeline();
    }

    @Override
    public void undo() {
        undoHandler.undo();
    }

    @Override
    public void redo() {
        redoHandler.redo();
    }
}
