package ch.supsi.editor2d.view;

import ch.supsi.editor2d.contracts.displayable.InfoDisplayable;
import ch.supsi.editor2d.contracts.observer.ControlledView;
import ch.supsi.editor2d.contracts.observer.FeedbackObserver;
import ch.supsi.editor2d.controller.TranslationsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class InfoBarView implements FeedbackObserver, ControlledView, InfoDisplayable {
    @FXML
    private Text infobarText;
    @FXML
    private ScrollPane scrollPane;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static InfoBarView myself;
    private static Parent parent;


    protected InfoBarView()
    {
        //empty constructor
    }

    public static InfoBarView getInstance()
    {
        if(myself==null){
            myself = new InfoBarView();
            try{
                TranslationsController translationsController = TranslationsController.getInstance();
                ResourceBundle bundle = translationsController.getResourceBundle();
                URL fxmlurl = InfoBarView.class.getResource("/view/infoBarView.fxml");
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
    public void addToDisplay(String feedback) {
        // Ottieni l'ora corrente e formattala
        String currentTime = LocalTime.now().format(TIME_FORMATTER);

        // Crea la stringa del feedback con l'ora
        String feedbackWithTime = String.format("[%s] %s", currentTime, feedback);

        infobarText.setText(infobarText.getText()+"\n"+feedbackWithTime);
        // Scorri fino alla fine dopo aver aggiornato il contenuto
        scrollPane.layout();
        scrollPane.setVvalue(1.0); // Scorri verticalmente fino alla fine
    }

    @Override
    public void updateFeedback(String feedback) {
        addToDisplay(feedback);
    }

    @Override
    public void initialize() {
        //empty method
    }

    @Override
    public Parent getParent() {
        return parent;
    }
}
