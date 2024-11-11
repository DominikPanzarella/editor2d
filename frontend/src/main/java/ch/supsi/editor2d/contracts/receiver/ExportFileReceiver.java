package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ExportFileHandler;

public interface ExportFileReceiver<T extends ExportFileHandler> extends Receiver
{
    void exportFile();
}
