package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ZoomOutHandler;
import ch.supsi.editor2d.contracts.receiver.ZoomOutReceiver;

public class ZoomOutCommand<T extends ZoomOutReceiver<ZoomOutHandler>> extends AbstractCommand<T>{
    protected ZoomOutCommand(T receiver) {
        super(receiver);
    }

    public static <T extends ZoomOutReceiver<ZoomOutHandler>> ZoomOutCommand<T> create(T receiver) throws InstantiationException {
        if(receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ZoomOutCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.zoomOut();
    }
}
