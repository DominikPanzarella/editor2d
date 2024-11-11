package ch.supsi.editor2d.contracts.observer;

import ch.supsi.editor2d.service.model.ImageWrapper;

public interface UndoObserver
{
    void undo(ImageWrapper data);
}
