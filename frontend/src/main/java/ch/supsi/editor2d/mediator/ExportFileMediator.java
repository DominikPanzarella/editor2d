package ch.supsi.editor2d.mediator;

import ch.supsi.editor2d.contracts.handler.ExportFileHandler;
import ch.supsi.editor2d.contracts.receiver.ExportFileReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ExportFileMediator<T extends ExportFileReceiver<ExportFileHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination exportFileKeyCombination;
    private static ExportFileMediator myself;


    protected ExportFileMediator(Stage stage, T receiver, KeyCombination exportFileKeyCombination){
        this.exportFileKeyCombination = exportFileKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(exportFileKeyCombination.match(keyEvent))
                receiver.exportFile();
        });
    }

    public static <T extends ExportFileReceiver<ExportFileHandler>> ExportFileMediator<T> getInstance(Stage stage, T receiver, KeyCombination exportFileKeyCombination){

        if(myself == null)
            myself = new ExportFileMediator(stage, receiver, exportFileKeyCombination);
        return myself;
    }

}
