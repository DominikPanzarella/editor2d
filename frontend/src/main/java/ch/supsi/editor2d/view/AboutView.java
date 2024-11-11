package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.observer.AboutObserver;
import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.controller.TranslationsController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AboutView implements ControlledView, AboutObserver {
    @FXML
    private VBox developersVBox;
    @FXML
    private Label versionLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TableView<Developer> developersTable;
    @FXML
    private TableColumn<Developer, String> emailColumn;
    @FXML
    private TableColumn<Developer, String> nameColumn;
    private static AboutView myself;
    private static Stage stage;
    private static Parent parent;

    private AboutView() {
    }

    public static AboutView getInstance() {
        if (myself == null) {
            try {
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = FiltersListView.class.getResource("/view/aboutView.fxml");
                if (fxmlurl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlurl, bundle);
                    myself = new AboutView();
                    fxmlLoader.setController(myself);
                    parent = fxmlLoader.load();
                    stage = new Stage();
                    stage.setScene(new Scene(parent));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return myself;
    }

    @Override
    public void initialize() {
        // Inizializza le colonne della TableView
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
    }

    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public void about(List<String> infos, Map<String, String> developers) {
        if (infos.size() >= 2) {
            versionLabel.setText(infos.get(0));
            dateLabel.setText(infos.get(1));
        }

        List<Developer> developerList = developers.entrySet().stream()
                .map(entry -> new Developer(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        developersTable.setItems(FXCollections.observableArrayList(developerList));

        stage.show();
    }

    record Developer(String email, String name) {
    }
}
