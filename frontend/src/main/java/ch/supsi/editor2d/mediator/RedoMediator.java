package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.RedoHandler;
import ch.supsi.editor2d.contracts.receiver.RedoReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RedoMediator<T extends RedoReceiver<RedoHandler>> implements ShortcutMediator{
    private T receiver;
    private KeyCombination redoPipelineKeyCombination;
    private static RedoMediator myself;


    protected RedoMediator(Stage stage, T receiver, KeyCombination redoPipelineKeyCombination){
        this.redoPipelineKeyCombination = redoPipelineKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(redoPipelineKeyCombination.match(keyEvent))
                receiver.redo();
        });
    }

    public static <T extends RedoReceiver<RedoHandler>> RedoMediator<T> getInstance(Stage stage, T receiver, KeyCombination redoPipelineKeyCombination){

        if(myself == null)
            myself = new RedoMediator(stage, receiver, redoPipelineKeyCombination);
        return myself;
    }
}
