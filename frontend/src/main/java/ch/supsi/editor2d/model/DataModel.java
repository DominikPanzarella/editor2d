package ch.supsi.editor2d.model;

import ch.supsi.editor2d.alert.ErrorPopup;
import ch.supsi.editor2d.contracts.handler.*;
import ch.supsi.editor2d.contracts.observable.*;
import ch.supsi.editor2d.controller.ImageController;
import ch.supsi.editor2d.controller.PipelineController;
import ch.supsi.editor2d.controller.PreferencesController;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.memento.Memento;
import ch.supsi.editor2d.memento.MementoCaretaker;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.utils.exceptions.EmptyPipeline;
import ch.supsi.editor2d.utils.exceptions.FileReadingException;
import ch.supsi.editor2d.utils.exceptions.ImageProcessingException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/*
* Data model that contains the application data through the usage
* */
public class DataModel implements Observable, RedoHandler, UndoHandler, OpenFileHandler, ZoomInHandler, ZoomOutHandler, ExportFileHandler, ExitHandler, ImageLoadedObservable, RunPipelineHandler, ClearPipelineHandler, OKHandler, CancelHandler, ClearPipelineObservable , FeedbackObservable, ZoomOutObservable, ZoomInObservable, ExitObservable, ToggleExportObservable, ToggleFiltersObservable, ToggleZoomButtonsObservable, ToggleUndoButtonObservable, ToggleRedoButtonObservable, UndoObservable, RedoObservable
{
    private ImageWrapper initialImage;
    private ImageWrapper currentImage;
    private static DataModel mySelf;
    private ImageController imageController;
    private PipelineController pipelineController;
    private TranslationsController translationsController;
    private PreferencesController preferencesController;
    private MementoCaretaker<ImageWrapper> imageWrapperMementoCaretaker;

    private boolean isChanged;

    private DataModel(){
        super();
        this.imageController = ImageController.getInstance();
        this.pipelineController = PipelineController.getInstance();
        this.translationsController = TranslationsController.getInstance();
        this.preferencesController = PreferencesController.getInstance();
        this.imageWrapperMementoCaretaker = new MementoCaretaker<>();
        this.isChanged = false;
    }

    public static DataModel getInstance(){
        if(mySelf == null){
            mySelf = new DataModel();
        }
        return mySelf;
    }

    public ImageWrapper getCurrentImage(){
        return currentImage;
    }

    public void openFile(){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(translationsController.translate("label.openTitle"));
        fileChooser.setInitialDirectory(preferencesController.getUserPreferencesDirectoryPath().toFile());

        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            loadImage(file.getPath());
            notifyFeedbackObservers(translationsController.translate("label.imageOpened"));
        }
    }

    private void loadImage(String path){
        try{
            ImageWrapper loadedImage = imageController.loadImage(path);
            this.initialImage = loadedImage;
            this.currentImage = this.initialImage;
            // Add the state after successfully loading an image
            this.imageWrapperMementoCaretaker.addState(new Memento<>(currentImage.clone()));
            notifyObservers(currentImage);
            notifyExportObservers();
            notifyFiltersObservers();
            notifyZoomObservers();
        } catch (FileReadingException | IOException e) {
            new ErrorPopup().show(e.getMessage());
        }
    }

    @Override
    public void runPipeline() {
        // Controlla se l'immagine attuale è cambiata prima di eseguire la pipeline
        ImageWrapper previousImage = currentImage.clone();

        // Salva l'immagine attuale per l'elaborazione
        initialImage = currentImage;

        try {
            // Esegui la pipeline
            currentImage = pipelineController.runPipeline(initialImage);

            // Aggiungi lo stato solo se c'è stata una modifica
            if (!previousImage.equals(currentImage)) {
                this.imageWrapperMementoCaretaker.addState(new Memento<>(currentImage.clone()));
                notifyUndoButtonObservers(false);
            }

            // Notifica gli osservatori
            notifyObservers(currentImage);
            notifyFeedbackObservers(translationsController.translate("label.pipelineExecuted"));
        } catch (ImageProcessingException e) {
            notifyFeedbackObservers(translationsController.translate("label.imageProcessingException"));
            new ErrorPopup().show(translationsController.translate("label.imageProcessingException"));
        } catch (EmptyPipeline e) {
            new ErrorPopup().show(translationsController.translate("label.emptyPipelineException"));
            notifyFeedbackObservers(translationsController.translate("label.emptyPipelineException"));
        }

        imageWrapperMementoCaretaker.printCurrentMemento();
    }

    @Override
    public void clearPipeline() {
        pipelineController.clearAllPipeline();
        notifyClearPipelineObservers();
        notifyFeedbackObservers(translationsController.translate("label.pipelineCleared"));
    }

    @Override
    public void zoomIn() {
        notifyZoomInObservers();
    }

    @Override
    public void zoomOut() {
        notifyZoomOutObservers();
    }

    @Override
    public void exit() {
        if(this.isChanged){
            notifyExitObservers();
        }
        else{
            System.exit(0);
        }
    }

    @Override
    public void cancel(Stage toClose) {
        toClose.close();
    }

    @Override
    public void ok() {
        System.exit(0);
    }

    @Override
    public void exportFile() {
        if(currentImage == null){
            new ErrorPopup().show(translationsController.translate("label.emptyImage"));
            notifyFeedbackObservers(translationsController.translate("label.emptyImage"));
            return;
        }

        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PGM Files", "*.pgm"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PBM Files", "*.pbm"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PPM Files", "*.ppm"));
        fileChooser.setTitle(translationsController.translate("label.exportTitle"));
        fileChooser.setInitialDirectory(preferencesController.getUserPreferencesDirectoryPath().toFile());

        File file = fileChooser.showSaveDialog(null);
        String[] fileName = file.toURI().getPath().split(File.separator);
        String[] fileNameAndExtension = fileName[fileName.length - 1].split("\\.");
        try {
            imageController.exportImage(file.toURI().getPath(), fileNameAndExtension[1], currentImage);
            notifyFeedbackObservers(translationsController.translate("label.imageExported"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo() {
        Memento<ImageWrapper> previousState = imageWrapperMementoCaretaker.undo();
        if (previousState != null) {
            currentImage = previousState.getState();
            notifyUndoObservers(currentImage);
            notifyRedoButtonObservers(false);
            if(!imageWrapperMementoCaretaker.canUndo()){
                notifyUndoButtonObservers(true);
            }
        } else {
            notifyUndoButtonObservers(true);
        }
    }

    @Override
    public void redo() {
        Memento<ImageWrapper> previousState = imageWrapperMementoCaretaker.redo();
        if (previousState != null) {
            currentImage = previousState.getState();
            notifyRedoObservers(currentImage);
            notifyUndoButtonObservers(false);
            if(!imageWrapperMementoCaretaker.canRedo()){
                notifyRedoButtonObservers(true);
            }
        } else {
            notifyRedoButtonObservers(true);
        }
    }

}
