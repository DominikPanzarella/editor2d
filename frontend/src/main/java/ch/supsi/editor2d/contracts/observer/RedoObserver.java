package ch.supsi.editor2d.contracts.observer;

import ch.supsi.editor2d.service.model.ImageWrapper;

import java.awt.*;

public interface RedoObserver{
    void redo(ImageWrapper data);
}
