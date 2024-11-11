package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.OpenFileHandler;
import ch.supsi.editor2d.contracts.receiver.OpenFileReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class OpenFileMediator<T extends OpenFileReceiver<OpenFileHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination openFileKeyCombination;
    private static OpenFileMediator myself;


    protected OpenFileMediator(Stage stage, T receiver, KeyCombination openFileKeyCombination){
        this.openFileKeyCombination = openFileKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(openFileKeyCombination.match(keyEvent))
                receiver.openFile();
        });
    }

    public static <T extends OpenFileReceiver<OpenFileHandler>> OpenFileMediator<T> getInstance(Stage stage, T receiver, KeyCombination openFileKeyCombination){

        if(myself == null)
            myself = new OpenFileMediator(stage, receiver, openFileKeyCombination);
        return myself;
    }

}
