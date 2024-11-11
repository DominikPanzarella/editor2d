package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.ZoomInHandler;
import ch.supsi.editor2d.contracts.handler.ZoomOutHandler;
import ch.supsi.editor2d.contracts.receiver.ZoomInReceiver;
import ch.supsi.editor2d.contracts.receiver.ZoomOutReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ZoomMediator<T extends ZoomInReceiver<ZoomInHandler> & ZoomOutReceiver<ZoomOutHandler>> implements ShortcutMediator
{
    private T zoomReceiver;
    private KeyCombination zoomInKeyCombination;
    private KeyCombination zoomOutKeyCombination;

    private static ZoomMediator myself;

    protected ZoomMediator(Stage primaryStage, T zoomReceiver, KeyCombination zoomInKeyCombination, KeyCombination zoomOutKeyCombination){
        this.zoomInKeyCombination = zoomInKeyCombination;
        this.zoomOutKeyCombination = zoomOutKeyCombination;
        this.zoomReceiver = zoomReceiver;
        Scene scene = primaryStage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(zoomInKeyCombination.match(keyEvent))
                zoomReceiver.zoomIn();

            if(zoomOutKeyCombination.match(keyEvent))
                zoomReceiver.zoomOut();
        });

    }

    public static <T extends ZoomInReceiver<ZoomInHandler> & ZoomOutReceiver<ZoomOutHandler>> ZoomMediator<T> getInstance(Stage stage, T zoomReceiver, KeyCombination zoomInKeyCombination,KeyCombination zoomOutKeyCombination){

        if(myself == null)
            myself = new ZoomMediator<>(stage,zoomReceiver, zoomInKeyCombination, zoomOutKeyCombination);
        return myself;
    }

}
