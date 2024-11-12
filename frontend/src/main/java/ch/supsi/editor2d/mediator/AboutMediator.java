package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.AboutHandler;
import ch.supsi.editor2d.contracts.receiver.AboutReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AboutMediator<T extends AboutReceiver<AboutHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination aboutKeyCombination;
    private static AboutMediator myself;


    protected AboutMediator(Stage stage, T receiver, KeyCombination aboutKeyCombination){
        this.aboutKeyCombination = aboutKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(aboutKeyCombination.match(keyEvent))
                receiver.about();
        });
    }

    public static <T extends AboutReceiver<AboutHandler>> AboutMediator<T> getInstance(Stage stage, T receiver, KeyCombination aboutKeyCombination){

        if(myself == null)
            myself = new AboutMediator(stage, receiver, aboutKeyCombination);
        return myself;
    }
}
