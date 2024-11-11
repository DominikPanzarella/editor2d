package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.contracts.observer.MoreInfoObserver;
import ch.supsi.editor2d.controller.TranslationsController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MoreInfoView implements ControlledView, MoreInfoObserver

{

    @FXML
    private TableView<ShortcutCommand> shortcutListTable;
    @FXML
    private TableColumn<ShortcutCommand, String> shortcutName;

    @FXML
    private TableColumn<ShortcutCommand, String> shortcutCommand;

    @FXML
    private TableColumn<ShortcutCommand, String> shortcutDescription;

    private static MoreInfoView myself;
    private static Stage stage;
    private static Parent parent;

    private MoreInfoView() {
    }

    public static MoreInfoView getInstance() {
        if (myself == null) {
            try {
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = FiltersListView.class.getResource("/view/moreInfosView.fxml");
                if (fxmlurl != null) {
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlurl, bundle);
                    myself = new MoreInfoView();
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
        shortcutName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name));
        shortcutCommand.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().command));
        shortcutDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().description));
    }

    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public void moreInfo(Map<String, List<String>> shortcuts) {
        List<ShortcutCommand> shortcutList = shortcuts.entrySet().stream()
                .map(entry ->{
                    final String key = entry.getKey();
                    final List<String> values = entry.getValue();
                    return new ShortcutCommand(key, values.get(0), values.get(1));
                }).collect(Collectors.toList());

        shortcutListTable.setItems(FXCollections.observableArrayList(shortcutList));

        stage.show();
    }

    record ShortcutCommand(String name, String command, String description)
    {}
}
