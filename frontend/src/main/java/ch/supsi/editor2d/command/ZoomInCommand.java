package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ZoomInHandler;
import ch.supsi.editor2d.contracts.receiver.ZoomInReceiver;

public class ZoomInCommand<T extends ZoomInReceiver<ZoomInHandler>> extends AbstractCommand<T> {


    protected ZoomInCommand(T receiver) {
        super(receiver);
    }

    public static <T extends ZoomInReceiver<ZoomInHandler>> ZoomInCommand<T> create(T receiver) throws InstantiationException {
        if(receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ZoomInCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.zoomIn();
    }
}
