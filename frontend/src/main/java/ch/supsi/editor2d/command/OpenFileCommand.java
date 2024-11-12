package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.OpenFileHandler;
import ch.supsi.editor2d.contracts.receiver.OpenFileReceiver;

public class OpenFileCommand<T extends OpenFileReceiver<OpenFileHandler>> extends AbstractCommand<T>{

    protected OpenFileCommand(T receiver) {
        super(receiver);
    }

    public static <T extends OpenFileReceiver<OpenFileHandler>> OpenFileCommand<T> create(T receiver) throws InstantiationException {
        if(receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new OpenFileCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.openFile();
    }
}
