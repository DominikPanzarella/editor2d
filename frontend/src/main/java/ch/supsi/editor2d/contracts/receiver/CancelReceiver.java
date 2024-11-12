package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.CancelHandler;
import javafx.stage.Stage;

public interface CancelReceiver<T extends CancelHandler> extends Receiver
{
    void cancel(Stage toClose);
}
