package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.OpenFileHandler;

public interface OpenFileReceiver<T extends OpenFileHandler> extends Receiver{
    void openFile();
}
