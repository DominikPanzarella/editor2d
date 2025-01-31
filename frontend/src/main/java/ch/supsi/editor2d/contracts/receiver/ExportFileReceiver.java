package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ExportFileHandler;
import javafx.stage.FileChooser;

public interface ExportFileReceiver<T extends ExportFileHandler> extends Receiver
{
    void exportFile(FileChooser fileChooser);
}
