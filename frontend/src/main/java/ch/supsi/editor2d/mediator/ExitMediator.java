package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.ExitHandler;
import ch.supsi.editor2d.contracts.receiver.ExitReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ExitMediator<T extends ExitReceiver<ExitHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination exitKeyCombination;
    private static ExitMediator myself;


    protected ExitMediator(Stage stage, T receiver, KeyCombination exitKeyCombination){
        this.exitKeyCombination = exitKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(exitKeyCombination.match(keyEvent))
                receiver.exit();
        });
    }

    public static <T extends ExitReceiver<ExitHandler>> ExitMediator<T> getInstance(Stage stage, T receiver, KeyCombination exitKeyCombination){

        if(myself == null)
            myself = new ExitMediator(stage, receiver, exitKeyCombination);
        return myself;
    }
}
