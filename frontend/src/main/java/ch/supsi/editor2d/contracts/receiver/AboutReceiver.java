package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.AboutHandler;

public interface AboutReceiver<T extends AboutHandler> extends Receiver{

    void about();
}
