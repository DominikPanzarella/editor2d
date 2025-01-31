package ch.supsi.editor2d.view;

import ch.supsi.editor2d.command.ClearPipelineCommand;
import ch.supsi.editor2d.command.RedoCommand;
import ch.supsi.editor2d.command.RunPipelineCommand;
import ch.supsi.editor2d.command.UndoCommand;
import ch.supsi.editor2d.contracts.displayable.PipelineDisplayable;
import ch.supsi.editor2d.contracts.handler.RedoHandler;
import ch.supsi.editor2d.contracts.handler.UndoHandler;
import ch.supsi.editor2d.contracts.observer.*;
import ch.supsi.editor2d.contracts.receiver.RedoReceiver;
import ch.supsi.editor2d.contracts.receiver.UndoReceiver;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.contracts.handler.ClearPipelineHandler;
import ch.supsi.editor2d.contracts.handler.RunPipelineHandler;
import ch.supsi.editor2d.contracts.receiver.ClearPipelineReceiver;
import ch.supsi.editor2d.contracts.receiver.RunPipelineReceiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PipelineView implements ControlledView, PipelineDisplayable,FilterSelectedObserver, ClearPipelineObserver, ToggleUndoButtonObserver, ToggleRedoButtonObserver, ToggleRunButtonsObserver, ToggleEmptyPipelineObserver
{
    @FXML
    Label historyLabel;

    @FXML
    GridPane pipelineActionsGridPane;

    @FXML
    AnchorPane pipelineActionView;

    @FXML
    Button redoButton;

    @FXML
    Button undoButton;

    @FXML
    Button deleteButton;

    @FXML
    Button runPipelineButton;

    @FXML
    VBox filtersSelectedVBox;

    private static Parent parent;

    private static PipelineView myself;

    private PipelineView(){}

    public static PipelineView getInstance(){
        if(myself == null){
            myself = new PipelineView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxml = PipelineView.class.getResource("/view/pipelineView.fxml");

                if(fxml != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(fxml, bundle);
                    fxmlLoader.setController(myself);
                    parent = fxmlLoader.load();

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return myself;
    }

    @Override
    public void initialize() {
        runPipelineButton.setDisable(true);
        deleteButton.setDisable(true);
        redoButton.setDisable(true);
        undoButton.setDisable(true);
    }

    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public <T extends RunPipelineCommand<? extends RunPipelineReceiver<RunPipelineHandler>>> void createRunPipelineBehaviour(T command){
        runPipelineButton.setOnAction(action->command.execute());
    }

    @Override
    public <T extends ClearPipelineCommand<? extends ClearPipelineReceiver<ClearPipelineHandler>>> void createClearPipelineBehaviour(T command){
        deleteButton.setOnAction(action->command.execute());
    }

    @Override
    public <T extends UndoCommand<? extends UndoReceiver<UndoHandler>>> void createUndoBehaviour(T command){
        undoButton.setOnAction(action->command.execute());
    }

    @Override
    public <T extends RedoCommand<? extends RedoReceiver<RedoHandler>>> void createRedoBehaviour(T command){
        redoButton.setOnAction(action->command.execute());
    }

    @Override
    public void filterSelected(String filter) {
        TranslationsController translationsController = TranslationsController.getInstance();
        Label filterLabel = new Label(translationsController.translate("label."+filter));

        // Applica lo stile inline
        filterLabel.setStyle("-fx-background-color: #f1a08e; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-padding: 12px 20px; " +
                "-fx-border-radius: 8px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0, 2, 2); " +
                "-fx-alignment: center; " +
                "-fx-margin: 10px 0; " +
                "-fx-font-weight: bold;");

        // Imposta la larghezza della Label per usare la larghezza disponibile
        filterLabel.setPrefWidth(300); // Puoi impostare qui la larghezza del VBox

        // Aggiungi la Label al VBox
        filtersSelectedVBox.getChildren().add(filterLabel);
    }

    @Override
    public void clearPipeline() {
        filtersSelectedVBox.getChildren().clear();
    }

    @Override
    public void toggleEmptyPipelineButton(boolean state) {
        deleteButton.setDisable(state);
    }

    @Override
    public void toggleRedoButton(boolean state) {
        redoButton.setDisable(state);
    }

    @Override
    public void toggleRunButtons(boolean state) {
        runPipelineButton.setDisable(state);
    }

    @Override
    public void toggleUndoButton(boolean state) {
        undoButton.setDisable(state);

    }

}
