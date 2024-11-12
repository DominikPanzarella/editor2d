package ch.supsi.editor2d.contracts.observer;

import javafx.scene.Parent;

public interface ControlledView {
    void  initialize();
    Parent getParent();
}
