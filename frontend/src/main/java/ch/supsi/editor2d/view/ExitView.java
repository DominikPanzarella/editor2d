package ch.supsi.editor2d.view;

import ch.supsi.editor2d.command.CancelCommand;
import ch.supsi.editor2d.command.OkCommand;
import ch.supsi.editor2d.contracts.displayable.ExitDisplayable;
import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.contracts.observer.ExitObserver;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.contracts.handler.CancelHandler;
import ch.supsi.editor2d.contracts.handler.OKHandler;
import ch.supsi.editor2d.contracts.receiver.CancelReceiver;
import ch.supsi.editor2d.contracts.receiver.OkReceiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExitView implements ControlledView, ExitDisplayable, ExitObserver
{
    @FXML
    Button cancelButton;
    @FXML
    Button okButton;

    private static ExitView myself;
    private static Parent parent;
    private static Stage stage;

    private ExitView(){
    }

    public static ExitView getInstance(){
        if(myself == null){
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = FiltersListView.class.getResource("/view/exitView.fxml");
                if(fxmlurl != null){;
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlurl, bundle);
                    myself = new ExitView();
                    fxmlLoader.setController(myself);
                    parent = fxmlLoader.load();
                    stage = new Stage();
                    stage.setScene(new Scene(parent));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return myself;
    }

    @Override
    public <T extends OkCommand<? extends OkReceiver<OKHandler>>> void createOKBehaviour(T command){
        okButton.setOnAction(action->command.execute());
    }

    @Override
    public <T extends CancelCommand<? extends CancelReceiver<CancelHandler>>> void createCancelBehaviour(T command){
        cancelButton.setOnAction(action->command.execute());
    }

    @Override
    public void initialize() {

    }

    @Override
    public Parent getParent() {
        return parent;
    }


    @Override
    public void exit() {
        stage.show();
    }


    public Stage getStage(){
        return stage;
    }
}
