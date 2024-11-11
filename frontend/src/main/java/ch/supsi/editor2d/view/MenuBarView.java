package ch.supsi.editor2d.view;

import ch.supsi.editor2d.command.*;
import ch.supsi.editor2d.contracts.handler.*;
import ch.supsi.editor2d.contracts.observer.*;
import ch.supsi.editor2d.contracts.receiver.*;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.service.model.ImageWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuBarView implements ControlledView, ToggleExportObserver, ToggleRedoButtonObserver, ToggleUndoButtonObserver, ToggleZoomButtonsObserver, ToggleRunButtonsObserver {
    @FXML
    Menu fileMenu;
    @FXML
    MenuItem openMenuItem;
    @FXML
    MenuItem exportMenuItem;
    @FXML
    MenuItem exitMenuItem;
    @FXML
    Menu editMenu;
    @FXML
    Menu changeLanguageMenu;
    @FXML
    MenuItem enUSMenuItem;
    @FXML
    MenuItem itCHMenuItem;
    @FXML
    MenuItem runPipelineMenuItem;
    @FXML
    MenuItem undoMenuItem;
    @FXML
    MenuItem redoMenuItem;
    @FXML
    MenuItem zoomInMenuItem;
    @FXML
    MenuItem zoomOutMenuItem;
    @FXML
    Menu helpMenu;
    @FXML
    MenuItem aboutMenuItem;

    @FXML
    MenuItem moreInfoMenuItem;
    private static Parent parent;


    private static MenuBarView myself;

    private MenuBarView(){}

    public static MenuBarView getInstance(){
        if(myself == null){
            myself = new MenuBarView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = MenuBarView.class.getResource("/view/menuBar.fxml");
                if(fxmlurl != null){
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlurl, bundle);
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
        exportMenuItem.setDisable(true);
        runPipelineMenuItem.setDisable(true);
        undoMenuItem.setDisable(true);
        zoomInMenuItem.setDisable(true);
        zoomOutMenuItem.setDisable(true);
        redoMenuItem.setDisable(true);
    }

    public <T extends OpenFileCommand<? extends OpenFileReceiver<OpenFileHandler>>> void createOpenMenuItemBehaviour(T command){
        openMenuItem.setOnAction(action->command.execute());
    }

    public <T extends AboutCommand<? extends AboutReceiver<AboutHandler>>> void createAboutBehavior(T command) {
        aboutMenuItem.setOnAction(action -> command.execute());
    }

    public <T extends ChangeLanguageCommand<? extends ChangeLanguageReceiver<ChangeLanguageHandler>>> void createEnUSMenuItemBehaviour(T command){
        enUSMenuItem.setOnAction(action->command.execute());
    }

    public <T extends ChangeLanguageCommand<? extends ChangeLanguageReceiver<ChangeLanguageHandler>>> void createItCHMenuItemBehaviour(T command){
        itCHMenuItem.setOnAction(action->command.execute());
    }

    public <T extends RunPipelineCommand<? extends RunPipelineReceiver<RunPipelineHandler>>> void createRunPipelineBehaviour(T command){
        runPipelineMenuItem.setOnAction(action->command.execute());
    }

    public <T extends ZoomOutCommand<? extends ZoomOutReceiver<ZoomOutHandler>>> void createZoomOutBehaviour(T command){
        zoomOutMenuItem.setOnAction(action->command.execute());
    }

    public <T extends ZoomInCommand<? extends ZoomInReceiver<ZoomInHandler>>> void createZoomInBehaviour(T command){
        zoomInMenuItem.setOnAction(action->command.execute());
    }

    public <T extends ExitCommand<? extends ExitReceiver<ExitHandler>>> void createExitBehaviour(T command){
        exitMenuItem.setOnAction(action->command.execute());
    }

    public <T extends ExportFileCommand<? extends ExportFileReceiver<ExportFileHandler>>> void createExportFileBehaviour(T command){
        exportMenuItem.setOnAction(action->command.execute());
    }

    public <T extends MoreInfoCommand<? extends MoreInfoReceiver<MoreInfoHandler>>> void createMoreInfoBehaviour(T command){
        moreInfoMenuItem.setOnAction(action->command.execute());
    }


    public Parent getParent() {
        return parent;
    }

    @Override
    public void toggleExportButton() {
        exportMenuItem.setDisable(!exportMenuItem.isDisable());
    }

    @Override
    public void toggleRedoButton(boolean state) {
        redoMenuItem.setDisable(!redoMenuItem.isDisable());
    }

    @Override
    public void toggleUndoButton(boolean state) {
        undoMenuItem.setDisable(state);
    }

    @Override
    public void toggleZoomButtons() {
        zoomInMenuItem.setDisable(!zoomInMenuItem.isDisable());
        zoomOutMenuItem.setDisable(!zoomOutMenuItem.isDisable());
    }

    @Override
    public void toggleRunButtons() {
        runPipelineMenuItem.setDisable(!runPipelineMenuItem.isDisable());
    }

}
