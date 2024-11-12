package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
import ch.supsi.editor2d.contracts.receiver.RunPipelineReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RunPipelineMediator<T extends RunPipelineReceiver<RunPipelineHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination runPipelineKeyCombination;
    private static RunPipelineMediator myself;


    protected RunPipelineMediator(Stage stage, T receiver, KeyCombination runPipelineKeyCombination){
        this.runPipelineKeyCombination = runPipelineKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(runPipelineKeyCombination.match(keyEvent))
                receiver.runPipeline();
        });
    }

    public static <T extends RunPipelineReceiver<RunPipelineHandler>> RunPipelineMediator<T> getInstance(Stage stage, T receiver, KeyCombination runPipelineKeyCombination){

        if(myself == null)
            myself = new RunPipelineMediator(stage, receiver, runPipelineKeyCombination);
        return myself;
    }

}
