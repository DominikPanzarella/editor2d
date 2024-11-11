package ch.supsi.editor2d;

import ch.supsi.editor2d.utils.annotations.Incomplete;
import ch.supsi.editor2d.utils.Initializer;
import javafx.application.Application;
import javafx.stage.Stage;

@Incomplete("This class is still under development.")
public class Start extends Application
{
    @Override
    public void start(Stage stage) throws Exception {
        Initializer.init(stage);
    }

    public static void main(String[] args){
        launch(args);
    }
}
