package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.UndoHandler;

import java.io.Reader;

public interface UndoReceiver<T extends UndoHandler> extends Receiver
{
    void undo();
}
