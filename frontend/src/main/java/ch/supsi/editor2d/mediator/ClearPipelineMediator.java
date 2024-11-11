package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;
import ch.supsi.editor2d.contracts.receiver.ClearPipelineReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ClearPipelineMediator<T extends ClearPipelineReceiver<ClearPipelineHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination clearPipelineKeyCombination;
    private static ClearPipelineMediator myself;


    protected ClearPipelineMediator(Stage stage, T receiver, KeyCombination clearPipelineKeyCombination){
        this.clearPipelineKeyCombination = clearPipelineKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(clearPipelineKeyCombination.match(keyEvent))
                receiver.clearPipeline();
        });
    }

    public static <T extends ClearPipelineReceiver<ClearPipelineHandler>> ClearPipelineMediator<T> getInstance(Stage stage, T receiver, KeyCombination clearPipelineKeyCombination){

        if(myself == null)
            myself = new ClearPipelineMediator(stage, receiver, clearPipelineKeyCombination);
        return myself;
    }
}
