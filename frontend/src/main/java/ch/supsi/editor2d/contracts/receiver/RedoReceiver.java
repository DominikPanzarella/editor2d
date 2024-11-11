package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.RedoHandler;

public interface RedoReceiver<T extends RedoHandler> extends Receiver
{
    void redo();
}
