package ch.supsi.editor2d.contracts.handler;

import javafx.stage.Stage;

public interface CancelHandler extends Handler
{
    void cancel(Stage toClose);
}
