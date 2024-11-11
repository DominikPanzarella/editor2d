package ch.supsi.editor2d.utils;
import ch.supsi.editor2d.command.*;
import ch.supsi.editor2d.contracts.handler.*;
import ch.supsi.editor2d.controller.*;
import ch.supsi.editor2d.mediator.*;
import ch.supsi.editor2d.contracts.receiver.*;
import ch.supsi.editor2d.model.DataModel;
import ch.supsi.editor2d.model.LanguageModel;
import ch.supsi.editor2d.model.PropertiesModel;
import ch.supsi.editor2d.command.AboutCommand;
import ch.supsi.editor2d.command.ChangeLanguageCommand;
import ch.supsi.editor2d.command.OpenFileCommand;
import ch.supsi.editor2d.model.*;
import ch.supsi.editor2d.utils.annotations.Incomplete;
import ch.supsi.editor2d.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

@Incomplete("This class is still under development.")
public class Initializer
{

    public static void init(Stage stage) throws IOException, InstantiationException {

        TranslationsController translationsController = TranslationsController.getInstance();
        FiltersModel filtersModel = FiltersModel.getInstance();
        DataModel model = DataModel.getInstance();
        AboutModel aboutModel = AboutModel.getInstance();
        LanguageModel languageModel = LanguageModel.getInstance();
        MoreInfoModel moreInfoModel = MoreInfoModel.getInstance();
        // Instancing the notifiers

        /*
            ###################################
                Loading all fxml files
            ###################################
         */

        // MENUBAR
        Parent menuBar;
        MenuBarView menuBarView = MenuBarView.getInstance();
        menuBar = menuBarView.getParent();

        // INFOBAR

        InfoBarView infoBarViewView = InfoBarView.getInstance();
        Parent infoBarView = infoBarViewView.getParent();
        infoBarViewView.addToDisplay(translationsController.translate("label.welcome"));
        //feedbackNotifier.addObserver(infoBarViewView);

        // IMAGEVIEW
        ImgView imgView = ImgView.getInstance();
        imgView.initialize();
        Parent imageView = imgView.getParent();

        // ABOUT VIEW
        AboutView aboutView = AboutView.getInstance();
        PropertiesModel propertiesModel = PropertiesModel.getInstance();
        aboutView.initialize();

        //EXIT VIEW
        ExitView exitView = ExitView.getInstance();
        exitView.initialize();


        // MAINVIEW
        MainView mainViewView = MainView.getInstance();
        Parent mainView = mainViewView.getParent();


        // FILTERSVIEW
        FiltersListView filtersListView = FiltersListView.getInstance();
        Parent filterSelectionView = filtersListView.getParent();

        // PIPELINEVIEW
        PipelineView pipelineView = PipelineView.getInstance();
        Parent pipelineViewParent = pipelineView.getParent();

        //MOREINFOVIEW
        MoreInfoView moreInfoView = MoreInfoView.getInstance();
        moreInfoView.initialize();

        // menuBar.fxml inside mainView.fxml
        AnchorPane menuBarPane = mainViewView.getMenuBarPane();
        menuBarPane.getChildren().add(menuBar);
        AnchorPane.setBottomAnchor(menuBar, 0.0);
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);

        // infoBarView.fxml inside mainView.fxml
        AnchorPane infoBarPane = mainViewView.getInfoBarPane();
        infoBarPane.getChildren().add(infoBarView);
        AnchorPane.setBottomAnchor(infoBarView, 0.0);
        AnchorPane.setTopAnchor(infoBarView, 0.0);
        AnchorPane.setLeftAnchor(infoBarView, 0.0);
        AnchorPane.setRightAnchor(infoBarView, 0.0);


        // imageView.fxml inside mainView.fxml
        AnchorPane imagePane = mainViewView.getImagePane();
        imagePane.getChildren().add(imageView);
        AnchorPane.setBottomAnchor(imageView, 0.0);
        AnchorPane.setTopAnchor(imageView, 0.0);
        AnchorPane.setLeftAnchor(imageView, 0.0);
        AnchorPane.setRightAnchor(imageView, 0.0);

        // FilterSelectionView inside mainView
        AnchorPane filtersSelectionPane = mainViewView.getFiltersListPane();
        filtersSelectionPane.getChildren().setAll(filterSelectionView);
        AnchorPane.setBottomAnchor(filterSelectionView, 0.0);
        AnchorPane.setTopAnchor(filterSelectionView, 0.0);
        AnchorPane.setLeftAnchor(filterSelectionView, 0.0);
        AnchorPane.setRightAnchor(filterSelectionView, 0.0);


        // PipelineView inside mainView
        AnchorPane pipelinePane = mainViewView.getPipelinePane();
        pipelinePane.getChildren().setAll(pipelineViewParent);
        AnchorPane.setBottomAnchor(pipelineViewParent, 0.0);
        AnchorPane.setTopAnchor(pipelineViewParent, 0.0);
        AnchorPane.setLeftAnchor(pipelineViewParent, 0.0);
        AnchorPane.setRightAnchor(pipelineViewParent, 0.0);
        pipelinePane.getChildren().setAll(pipelineViewParent);

        /*
           ###################################
                   Setup Controller
           ###################################
         */
        AboutController aboutController = AboutController.getInstance(aboutModel,propertiesModel);
        MenuBarController menuBarController = MenuBarController.getInstance(model, languageModel, model, model);
        PipelineListController pipelineListController = PipelineListController.getInstance(model, model, model, model);
        MoreInfoController moreInfoController = MoreInfoController.getInstance(moreInfoModel, propertiesModel);


        /*
           ###################################
                   Setup Receiver
           ###################################
         */
        OpenFileReceiver<OpenFileHandler> openFileReceiver = menuBarController;         //Refactored
        AboutReceiver<AboutHandler> aboutReceiver = aboutController;           //Refactored
        ChangeLanguageReceiver<ChangeLanguageHandler> languageReceiver = menuBarController;                    //Refactored
        RunPipelineReceiver<RunPipelineHandler> runPipelineReceiver = pipelineListController;               //Refactored
        ClearPipelineReceiver<ClearPipelineHandler> clearPipelineReceiver = pipelineListController;           //refactored
        ZoomInReceiver<ZoomInHandler> zoomInReceiver = menuBarController;
        ZoomOutReceiver<ZoomOutHandler> zoomOutReceiver = menuBarController;
        ExitReceiver<ExitHandler> exitReceiver = menuBarController;
        OkReceiver<OKHandler> okReceiver = menuBarController;
        CancelReceiver<CancelHandler> cancelReceiver = menuBarController;
        ExportFileReceiver<ExportFileHandler> exportFileReceiver = menuBarController;
        UndoReceiver<UndoHandler> undoReceiver = pipelineListController;
        RedoReceiver<RedoHandler> redoReceiver = pipelineListController;
        MoreInfoReceiver<MoreInfoHandler> moreInfoReceiver = moreInfoController;

        /*
           ###################################
                   Setup Command
           ###################################
         */

        OpenFileCommand<OpenFileReceiver<OpenFileHandler>> openFileCommand = OpenFileCommand.create(openFileReceiver);     //Refactored
        AboutCommand<AboutReceiver<AboutHandler>> aboutCommand = AboutCommand.create(aboutReceiver);                 //Refactored
        ChangeLanguageCommand<ChangeLanguageReceiver<ChangeLanguageHandler>> languageEnUSCommand = ChangeLanguageCommand.create(languageReceiver, "en-US");        //Refactored
        ChangeLanguageCommand<ChangeLanguageReceiver<ChangeLanguageHandler>> languageItCHCommand = ChangeLanguageCommand.create(languageReceiver, "it-CH");        //Refactored
        RunPipelineCommand<RunPipelineReceiver<RunPipelineHandler>> runPipelineCommand = RunPipelineCommand.create(runPipelineReceiver);                                  //Refactored
        ClearPipelineCommand<ClearPipelineReceiver<ClearPipelineHandler>> clearPipelineCommand = ClearPipelineCommand.create(clearPipelineReceiver);
        ZoomInCommand<ZoomInReceiver<ZoomInHandler>> zoomInCommand = ZoomInCommand.create(zoomInReceiver);
        ZoomOutCommand<ZoomOutReceiver<ZoomOutHandler>> zoomOutCommand = ZoomOutCommand.create(zoomOutReceiver);
        ExitCommand<ExitReceiver<ExitHandler>> exitCommand = ExitCommand.create(exitReceiver);
        OkCommand<OkReceiver<OKHandler>> okCommand  = OkCommand.create(okReceiver);
        CancelCommand<CancelReceiver<CancelHandler>> cancelCommand = CancelCommand.create(cancelReceiver, exitView.getStage());
        ExportFileCommand<ExportFileReceiver<ExportFileHandler>> exportFileCommand = ExportFileCommand.create(exportFileReceiver);
        UndoCommand<UndoReceiver<UndoHandler>> undoCommand = UndoCommand.create(undoReceiver);
        RedoCommand<RedoReceiver<RedoHandler>> redoCommand = RedoCommand.create(redoReceiver);
        MoreInfoCommand<MoreInfoReceiver<MoreInfoHandler>> moreInfoCommand = MoreInfoCommand.create(moreInfoReceiver);
        /*
           ###################################
               Linking Component - Command
           ###################################
         */

        menuBarView.createOpenMenuItemBehaviour(openFileCommand);
        menuBarView.createAboutBehavior(aboutCommand);
        menuBarView.createItCHMenuItemBehaviour(languageItCHCommand);
        menuBarView.createEnUSMenuItemBehaviour(languageEnUSCommand);
        menuBarView.createRunPipelineBehaviour(runPipelineCommand);
        menuBarView.createMoreInfoBehaviour(moreInfoCommand);
        pipelineView.createRunPipelineBehaviour(runPipelineCommand);
        pipelineView.createClearPipelineBehaviour(clearPipelineCommand);
        menuBarView.createZoomInBehaviour(zoomInCommand);
        menuBarView.createZoomOutBehaviour(zoomOutCommand);
        menuBarView.createExitBehaviour(exitCommand);
        menuBarView.createExportFileBehaviour(exportFileCommand);
        exitView.createCancelBehaviour(cancelCommand);
        exitView.createOKBehaviour(okCommand);
        pipelineView.createUndoBehaviour(undoCommand);
        pipelineView.createRedoBehaviour(redoCommand);


        /*
          ###################################
              Linking Observer - Notifier
           ###################################

         */
        model.addExitObserver(exitView);
        model.addObserver(imgView);
        model.addClearPipelineObserver(pipelineView);
        model.addOZoomInObserver(imgView);
        model.addOZoomOutObserver(imgView);
        aboutModel.addObserver(aboutView);
        languageModel.addObserver(infoBarViewView);
        filtersModel.addObserver(filtersListView);
        filtersModel.addObserver(pipelineView);
        model.addExportObserver(menuBarView);
        model.addZoomObserver(menuBarView);
        model.addFiltersObserver(filtersListView);
        model.addClearPipelineObserver(filtersModel);
        filtersModel.addeRunButtonsObserver(pipelineView);
        filtersModel.addEmptyPipelineObserver(pipelineView);
        filtersModel.addeRunButtonsObserver(menuBarView);
        model.addUndoButtonObserver(pipelineView);
        model.addORedoButtonObserver(pipelineView);
        model.addUndoObserver(imgView);
        model.addRedoObserver(imgView);
        moreInfoModel.addMoreInfoObserver(moreInfoView);

        /*
          ###################################
              Initializing - views and their handler
           ###################################

         */
        filtersListView.initialize();


        /*
          ###################################
              Loading filters
           ###################################

         */

        filtersModel.loadFilters();


        /*
          ###################################
              Scene setup
           ###################################
         */

        stage.setTitle("Editor2D");
        stage.setScene(new Scene(mainView));
        stage.getIcons().add(new Image(Objects.requireNonNull(Initializer.class.getResourceAsStream("/logo/2DEditorLogo.png"))));

         /*
          ###################################
              key combination shortcut setup
           ###################################
         */

        KeyCombination zoomInKeyCombination = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);
        KeyCombination zoomOutKeyCombination = new KeyCodeCombination(KeyCode.O,  KeyCombination.CONTROL_DOWN);
        KeyCombination openFileKeyCombination = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        KeyCombination runPipelineKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination clearPipelineKeyCombination = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        KeyCombination undoPipelineKeyCombination = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        KeyCombination redoPipelineKeyCombination = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        KeyCombination exportFileKeyCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        KeyCombination exitKeyCombination = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        KeyCombination aboutKeyCombination = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);

        /*654
          ###################################
              Mediator setup
           ###################################
         */


        ZoomMediator.getInstance(stage ,menuBarController, zoomInKeyCombination, zoomOutKeyCombination) ;
        OpenFileMediator.getInstance(stage, menuBarController, openFileKeyCombination);
        RunPipelineMediator.getInstance(stage, pipelineListController, runPipelineKeyCombination);
        ClearPipelineMediator.getInstance(stage, pipelineListController, clearPipelineKeyCombination);
        UndoMediator.getInstance(stage, pipelineListController, undoPipelineKeyCombination);
        RedoMediator.getInstance(stage, pipelineListController, redoPipelineKeyCombination);
        ExportFileMediator.getInstance(stage,menuBarController, exportFileKeyCombination);
        ExitMediator.getInstance(stage,menuBarController, exitKeyCombination);
        AboutMediator.getInstance(stage, aboutController, aboutKeyCombination);

          /*
          ###################################
              Scene show
           ###################################

         */
        stage.show();
    }

}
