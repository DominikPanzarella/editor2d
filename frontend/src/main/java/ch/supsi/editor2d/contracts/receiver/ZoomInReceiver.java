package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ZoomInHandler;

public interface ZoomInReceiver<T extends ZoomInHandler> extends Receiver
{
    void zoomIn();
}
