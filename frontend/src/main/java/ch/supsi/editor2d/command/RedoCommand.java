package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.RedoHandler;
import ch.supsi.editor2d.contracts.receiver.RedoReceiver;


public class RedoCommand<T extends RedoReceiver<RedoHandler>> extends AbstractCommand<T> {
    protected RedoCommand(T receiver) {
        super(receiver);
    }

    public static <T extends RedoReceiver<RedoHandler>> RedoCommand<T> create(T receiver) throws InstantiationException {
        if (receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new RedoCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.redo();
    }
}
