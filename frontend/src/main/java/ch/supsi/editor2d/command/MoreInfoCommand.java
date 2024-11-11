package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.MoreInfoHandler;
import ch.supsi.editor2d.contracts.receiver.MoreInfoReceiver;

public class MoreInfoCommand<T extends MoreInfoReceiver<MoreInfoHandler>> extends AbstractCommand<T>{
    protected MoreInfoCommand(T receiver) {
        super(receiver);
    }

    public static <T extends MoreInfoReceiver<MoreInfoHandler>> MoreInfoCommand<T> create(T receiver) throws InstantiationException {
        if (receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new MoreInfoCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.moreInfo();
    }
}
