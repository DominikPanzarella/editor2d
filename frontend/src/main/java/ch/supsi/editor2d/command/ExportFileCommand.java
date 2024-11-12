package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ExportFileHandler;
import ch.supsi.editor2d.contracts.receiver.ExportFileReceiver;

public class ExportFileCommand<T extends ExportFileReceiver<ExportFileHandler>> extends AbstractCommand<T>
{
    protected ExportFileCommand(T receiver) {
        super(receiver);
    }

    public static <T extends ExportFileReceiver<ExportFileHandler>> ExportFileCommand<T> create(T receiver) throws InstantiationException {
        if(receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ExportFileCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.exportFile();
    }
}
