package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.contracts.observer.FiltersLoadedObserver;
import ch.supsi.editor2d.contracts.observer.ToggleFiltersObserver;
import ch.supsi.editor2d.controller.TranslationsController;
import ch.supsi.editor2d.contracts.handler.AddFilterHandler;
import ch.supsi.editor2d.service.algorithm.Filter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FiltersListView implements ControlledView, FiltersLoadedObserver, ToggleFiltersObserver {
    @FXML
    Label filtersListLabel;
    @FXML
    AnchorPane filtersContainerAnchorPane;

    private static FiltersListView myself;
    private static Parent parent;
    private FiltersListView(){}

    public static FiltersListView getInstance(){
        if(myself == null){
            myself = new FiltersListView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = FiltersListView.class.getResource("/view/filtersListView.fxml");
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

    @Override
    public void filtersLoaded(List<Filter> filters, AddFilterHandler handler) {
        // Creiamo un VBox per visualizzare i bottoni uno sotto l'altro
        VBox vbox = new VBox();
        vbox.setSpacing(10);  // Imposta uno spazio verticale tra i bottoni
        TranslationsController translationsController = TranslationsController.getInstance();

        // Iteriamo attraverso la lista dei filtri e creiamo un bottone per ciascuno
        for (Filter filter : filters) {
            Button filterButton = new Button(translationsController.translate("label."+filter.getName())); // Creiamo un bottone con il nome del filtro
            filterButton.setPrefWidth(filtersContainerAnchorPane.getPrefWidth()); // Imposta la larghezza del bottone
            filterButton.setPrefHeight(50); // Imposta un'altezza fissa per i bottoni

            // Imposta lo stile per il bottone
            filterButton.setStyle("-fx-background-color: #e76143; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-padding: 12px 20px; " +
                    "-fx-border-radius: 8px; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0, 2, 2); " +
                    "-fx-cursor: hand; " +  // Cursore a manina
                    "-fx-font-weight: bold; " +
                    "-fx-margin: 10px 0;"); // Margini esterni

            filterButton.setOnAction(event -> {
                handler.addFilter(filter);
            });
            filterButton.setDisable(true);
            vbox.getChildren().add(filterButton);

        }

        filtersContainerAnchorPane.getChildren().clear();  // Puliamo eventuali vecchi contenuti
        filtersContainerAnchorPane.getChildren().add(vbox); // Aggiungiamo il VBox con i bottoni
    }

    @Override
    public void toggleFiltersButtons(boolean state) {
        if (!filtersContainerAnchorPane.getChildren().isEmpty() && filtersContainerAnchorPane.getChildren().get(0) instanceof VBox vbox) {
            for (var node : vbox.getChildren()) {
                if (node instanceof Button button) {
                    button.setDisable(state);
                }
            }
        }
    }
}
