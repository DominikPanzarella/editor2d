package ch.supsi.editor2d;

import ch.supsi.editor2d.model.DataModel;
import ch.supsi.editor2d.utils.Initializer;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

abstract public class AbstractMainGUITest extends ApplicationTest {

    protected static final int SLEEP_INTERVAL = 1000; // Tempo di pausa tra i test
    protected static final Logger LOGGER = Logger.getAnonymousLogger();
    protected int stepNo;
    private static Stage primaryStage;
    protected final DataModel dataModel = DataModel.getInstance();;

    @BeforeAll
    public static void setupSpec() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    protected void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOGGER.info("STEP" + stepNo + ": " + step);
        runnable.run();
        LOGGER.info("STEP" + stepNo + ": " + "end");
    }

    @Override
    public void start(final Stage stage) throws Exception {
        primaryStage = stage; // Salva il riferimento allo stage principale
        final Start main = new Start();
        stage.toFront();
        main.start(stage);
    }

}