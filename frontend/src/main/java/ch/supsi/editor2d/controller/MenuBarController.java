package ch.supsi.editor2d.controller;


import ch.supsi.editor2d.contracts.handler.*;
import ch.supsi.editor2d.contracts.receiver.*;
import ch.supsi.editor2d.model.DataModel;
import javafx.stage.Stage;

public class MenuBarController implements
        OpenFileReceiver<OpenFileHandler>,
        ChangeLanguageReceiver<ChangeLanguageHandler>,
        ZoomOutReceiver<ZoomOutHandler>,
        ZoomInReceiver<ZoomInHandler>,
        ExitReceiver<ExitHandler>,
        CancelReceiver<CancelHandler>,
        OkReceiver<OKHandler>,
        ExportFileReceiver<ExportFileHandler>

{

    private static MenuBarController myself;
    private OpenFileHandler openFileModel;
    private ChangeLanguageHandler changeLanguageModel;
    private ZoomOutHandler zoomOutModel;
    private ZoomInHandler zoomInModel;
    private ExitHandler exitModel;
    private OKHandler okModel;
    private CancelHandler cancelModel;
    private ExportFileHandler exportModel;

    protected MenuBarController(OpenFileHandler openFileModel, ChangeLanguageHandler changeLanguageModel, ExitHandler exitModel, ExportFileHandler exportFileModel) {
        this.openFileModel = openFileModel;
        this.changeLanguageModel = changeLanguageModel;
        this.exitModel = exitModel;
        this.cancelModel = DataModel.getInstance();
        this.okModel = DataModel.getInstance();
        this.zoomInModel = DataModel.getInstance();
        this.zoomOutModel = DataModel.getInstance();
        this.exportModel = exportFileModel;

    }

    public static MenuBarController getInstance(OpenFileHandler openFileModel, ChangeLanguageHandler changeLanguageModel, ExitHandler extiModel, ExportFileHandler exportModel) {
        if (myself == null) {
            myself = new MenuBarController(openFileModel, changeLanguageModel, extiModel, exportModel);
        }
        return myself;
    }

    @Override
    public void openFile() {
        openFileModel.openFile();
    }

    @Override
    public void changeLanguage(String languageTag) {
        changeLanguageModel.changeLanguage(languageTag);
    }

    @Override
    public void zoomIn() {
        zoomInModel.zoomIn();
    }

    @Override
    public void zoomOut() {
        zoomOutModel.zoomOut();
    }

    @Override
    public void exit() {
        exitModel.exit();
    }

    @Override
    public void cancel(Stage toClose) {
        cancelModel.cancel(toClose);
    }


    @Override
    public void ok() {
        okModel.ok();
    }

    @Override
    public void exportFile() {
        exportModel.exportFile();
    }

}
