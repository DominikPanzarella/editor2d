package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.OKHandler;

public interface OkReceiver<T extends OKHandler> extends Receiver
{
    void ok();
}
