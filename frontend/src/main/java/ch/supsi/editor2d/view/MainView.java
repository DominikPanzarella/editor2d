package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.controller.TranslationsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainView implements ControlledView {
    @FXML
    private AnchorPane filtersListPane;

    @FXML
    private AnchorPane pipelinePane;

    @FXML
    private AnchorPane imagePane;

    @FXML
    private AnchorPane menuBarPane;

    @FXML
    private AnchorPane infoBarPane;

    public AnchorPane getInfoBarPane() {
        return infoBarPane;
    }

    public AnchorPane getImagePane(){
        return imagePane;
    }

    public AnchorPane getFiltersListPane(){
        return filtersListPane;
    }

    public AnchorPane getPipelinePane(){
        return pipelinePane;
    }

    public AnchorPane getMenuBarPane() {
        return menuBarPane;
    }

    private static MainView myself;
    private static Parent parent;


    protected MainView(){

    }

    public static MainView getInstance()
    {
        if(myself==null){
            myself = new MainView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = InfoBarView.class.getResource("/view/mainView.fxml");
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

    }

    @Override
    public Parent getParent() {
        return parent;
    }
}
