package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ZoomOutHandler;

public interface ZoomOutReceiver<T extends ZoomOutHandler> extends Receiver
{
    void zoomOut();
}
