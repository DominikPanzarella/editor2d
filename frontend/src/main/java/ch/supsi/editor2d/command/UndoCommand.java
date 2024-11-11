package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.UndoHandler;
import ch.supsi.editor2d.contracts.receiver.UndoReceiver;

public class UndoCommand<T extends UndoReceiver<UndoHandler>> extends AbstractCommand<T>
{
    protected UndoCommand(T receiver) {
        super(receiver);
    }

    public static <T extends UndoReceiver<UndoHandler>> UndoCommand<T> create(T receiver) throws InstantiationException {
        if (receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new UndoCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.undo();
    }
}
