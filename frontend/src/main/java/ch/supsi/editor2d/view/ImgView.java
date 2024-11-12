package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.observer.*;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.service.model.ImageWrapper;
import ch.supsi.editor2d.service.model.PixelWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImgView implements ControlledView, ImageLoadedObserver, ZoomInObserver, ZoomOutObserver, UndoObserver,RedoObserver
{

    @FXML
    ImageView imageView;

    private static Parent parent;
    private static ImgView myself;

    private static final double ZOOM_FACTOR = 1.1;

    private ImgView(){}

    public static ImgView getInstance(){
        if(myself == null){
            myself = new ImgView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = MenuBarView.class.getResource("/view/imageView.fxml");
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

    public void draw(ImageWrapper toDraw){
        // Create a new WritableImage

        WritableImage writableImage = new WritableImage(toDraw.getWidth(), toDraw.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Draw the image
        for (int h = 0; h < toDraw.getHeight(); h++) {
            for (int w = 0; w < toDraw.getWidth(); w++) {
                PixelWrapper tempColor = toDraw.getData()[h][w];
                pixelWriter.setColor(w, h, Color.color(tempColor.getRed(), tempColor.getGreen(), tempColor.getBlue()));
            }
        }

        // Display image on ImageView component
        imageView.setImage(writableImage);
        // Set container size
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);
        imageView.setCache(false);
    }

    @Override
    public void initialize() {

    }
    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public void updateImage(ImageWrapper toDraw) {
        draw(toDraw);
    }

    @Override
    public void zoomIn() {
        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
        imageView.setFitHeight(imageView.getFitHeight() * ZOOM_FACTOR);
    }

    @Override
    public void zoomOut() {
        imageView.setFitWidth(imageView.getFitWidth() / ZOOM_FACTOR);
        imageView.setFitHeight(imageView.getFitHeight() / ZOOM_FACTOR);
    }


    @Override
    public void undo(ImageWrapper data) {
        updateImage(data);
    }

    @Override
    public void redo(ImageWrapper data) {
        updateImage(data);
    }
}
