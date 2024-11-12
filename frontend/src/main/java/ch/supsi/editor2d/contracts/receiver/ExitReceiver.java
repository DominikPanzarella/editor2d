package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ExitHandler;

public interface ExitReceiver<T extends ExitHandler>  extends Receiver
{
    void exit();
}
