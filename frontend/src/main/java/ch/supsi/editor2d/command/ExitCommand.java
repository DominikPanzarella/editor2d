package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ExitHandler;
import ch.supsi.editor2d.contracts.receiver.ExitReceiver;

public class ExitCommand<T extends ExitReceiver<ExitHandler>> extends AbstractCommand<T>
{

    protected ExitCommand(T receiver) {
        super(receiver);
    }

    public static <T extends ExitReceiver<ExitHandler>> ExitCommand<T> create(T receiver) throws InstantiationException {
        if (receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ExitCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.exit();
    }
}
