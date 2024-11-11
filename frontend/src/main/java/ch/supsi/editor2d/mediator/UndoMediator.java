package ch.supsi.editor2d.mediator;
import ch.supsi.editor2d.contracts.handler.UndoHandler;
import ch.supsi.editor2d.contracts.receiver.UndoReceiver;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class UndoMediator<T extends UndoReceiver<UndoHandler>> implements ShortcutMediator
{
    private T receiver;
    private KeyCombination undoPipelineKeyCombination;
    private static UndoMediator myself;


    protected UndoMediator(Stage stage, T receiver, KeyCombination undoPipelineKeyCombination){
        this.undoPipelineKeyCombination = undoPipelineKeyCombination;
        this.receiver = receiver;

        Scene scene = stage.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {

            if(undoPipelineKeyCombination.match(keyEvent))
                receiver.undo();
        });
    }

    public static <T extends UndoReceiver<UndoHandler>> UndoMediator<T> getInstance(Stage stage, T receiver, KeyCombination undoPipelineKeyCombination){

        if(myself == null)
            myself = new UndoMediator(stage, receiver, undoPipelineKeyCombination);
        return myself;
    }

}
