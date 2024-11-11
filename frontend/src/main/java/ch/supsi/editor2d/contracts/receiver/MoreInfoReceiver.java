package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.MoreInfoHandler;

public interface MoreInfoReceiver<T extends MoreInfoHandler> extends Receiver{
    void moreInfo();
}
