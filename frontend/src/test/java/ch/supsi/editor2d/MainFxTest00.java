package ch.supsi.editor2d;
import ch.supsi.editor2d.controller.TranslationsController;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.testfx.matcher.base.NodeMatchers;
import java.util.Set;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class MainFxTest00 extends AbstractMainGUITest {

    private final String imageURL;

    {
        try {
            imageURL = Paths.get(getClass().getClassLoader().getResource("image.pbm").toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private TranslationsController translationsController;
    private Path tempDir;

    @BeforeEach
    public void setup() throws Exception {
        // Mock del FileChooser con PowerMock per metodi statici
        FileChooser mockFileChooser = Mockito.mock(FileChooser.class);
        when(mockFileChooser.showOpenDialog(Mockito.any())).thenReturn(new File(imageURL));
        dataModel.setFileChooser(mockFileChooser);

        translationsController = TranslationsController.getInstance();
    }

    private void setExportTempDir() throws IOException {
        // Crea una directory temporanea per i test
        tempDir = Files.createTempDirectory("test-export-dir");
        FileChooser mockFileChooser = Mockito.mock(FileChooser.class);
        when(mockFileChooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
        when(mockFileChooser.showSaveDialog(Mockito.any())).thenReturn(tempDir.resolve("exportedImage.pbm").toFile());

        // Usa il metodo per esportare il file
        dataModel.exportFile(mockFileChooser);

        // Verifica che il file sia stato esportato
        Path exportedFile = tempDir.resolve("exportedImage.pbm");
        assert Files.exists(exportedFile) : "Il file non è stato esportato correttamente.";
    }

    @AfterEach
    public void cleanup() throws IOException {
        // Cancella la directory temporanea dopo il test
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .map(Path::toFile)
                    .forEach(File::delete);
            tempDir.toFile().delete();
        }
    }

    @AfterEach
    public void teardown() throws IOException {
        //cartella temporanea per salvataggio file
        if (tempDir != null) {
            // Cancella tutti i file e la directory
            Files.walk(tempDir)
                    .map(Path::toFile)
                    .forEach(File::delete);
            System.out.println("Cartella temporanea eliminata: " + tempDir.toAbsolutePath());
        }

    }

    @Test
    public void endToEndTest() throws IOException {
        step("Caricamento scena principale", this::testMainScene);
        step("pulsanti disabilitati", this::testButtonsInitiallyDisabled);

        step("Test Help menu", this::testHelpMenu);
        step("Test menu e caricamento file", this::testFileMenuAndLoadFile);

        step("Verifica zoomIn zoomOut", this::testZoom);
        step("Verifica applicazione dei filtri", this::testFilters);
        step("interazione con delete Pipeline", this::testDeletePipelineButton);
        step("interazione con applica Pipeline", this::testPipelineButton);
        step("interazione con applica Pipeline da menu", this::testEditMenuStartPipeline);
        step("interazione con Undo", this::testUndoButton);
        step("interazione con redo", this::testRedoButton);
        step("interazione con Undo da menu", this::testEditMenuUndo);
        step("interazione con redo da menu", this::testEditMenuRedo);

        step("Salvataggio dell'immagine esportata", this::testExportFile);
        step("cambio lingua eng", this::setLanguageEnglish);
        step("esci", this::testMenuExit);
    }

    private void testZoom() {
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#zoomInMenuItem");
        verifyThat("#infobarText", node -> {
            if (node instanceof Text text) {
                return text.getText().contains(translationsController.translate("label.ZoomIn"));
            }
            return false;
        });
        sleep(SLEEP_INTERVAL);
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#zoomOutMenuItem");
        verifyThat("#infobarText", node -> {
            if (node instanceof Text text) {
                return text.getText().contains(translationsController.translate("label.ZoomOut"));
            }
            return false;
        });
    }

    private void testEditMenuStartPipeline(){
        testFilters();
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#runPipelineMenuItem");
        // Verifica messaggio della pipeline eseguita
        verifyThat("#infobarText", node -> {
            if (node instanceof Text text) {
                return text.getText().contains(translationsController.translate("label.pipelineExecuted"));
            }
            return false;
        });

    }

    private void testEditMenuUndo(){
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#undoMenuItem");

        // Verifica messaggio dell'undo
        verifyThat("#infobarText", node -> {
            if (node instanceof Text text) {
                return text.getText().contains(translationsController.translate("label.undo"));
            }
            return false;
        });
    }

    private void testEditMenuRedo(){
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#redoMenuItem");

        // Verifica messaggio dell'undo
        verifyThat("#infobarText", node -> {
            if (node instanceof Text text) {
                return text.getText().contains(translationsController.translate("label.redo"));
            }
            return false;
        });
    }

    private void testMenuExit() {
        clickOn("#fileMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#exitMenuItem");
        sleep(SLEEP_INTERVAL);
    }

    private void testMainScene() {
        // Verifica che i componenti principali siano visibili
        verifyThat("#pipelinePane", isVisible());
        verifyThat("#filtersListPane", isVisible());
        verifyThat("#imagePane", isVisible());
        verifyThat("#menuBarPane", isVisible());
        verifyThat("#infoBarPane", isVisible());

        //bug primo click nullo
        clickOn("#fileMenu");
        sleep(SLEEP_INTERVAL);
    }

    public void testButtonsInitiallyDisabled() {
            // Verifica che i pulsanti principali siano disabilitati
            verifyThat("#undoButton", NodeMatchers.isDisabled());
            verifyThat("#redoButton", NodeMatchers.isDisabled());
            verifyThat("#deleteButton", NodeMatchers.isDisabled());
            verifyThat("#runPipelineButton", NodeMatchers.isDisabled());

            // Accedi direttamente al MenuBar
            MenuBar menuBar = (MenuBar) lookup("#menuBar").query();

            // Verifica che gli elementi dei menu siano disabilitati
            Menu fileMenu = menuBar.getMenus().stream()
                    .filter(menu -> translationsController.translate("label.fileMenu").equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'File' non trovato"));

            Menu editMenu = menuBar.getMenus().stream()
                    .filter(menu -> translationsController.translate("label.edit").equals(menu.getText()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Menu 'Edit' non trovato"));

            // Verifica MenuItem del menu File
            MenuItem exportMenuItem = fileMenu.getItems().stream()
                    .filter(item -> "exportMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'exportMenuItem' non trovato"));
            assert exportMenuItem.isDisable();

            // Verifica MenuItem del menu Edit
            MenuItem runPipelineMenuItem = editMenu.getItems().stream()
                    .filter(item -> "runPipelineMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'runPipelineMenuItem' non trovato"));
            assert runPipelineMenuItem.isDisable();

            MenuItem undoMenuItem = editMenu.getItems().stream()
                    .filter(item -> "undoMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'undoMenuItem' non trovato"));
            assert undoMenuItem.isDisable();

            MenuItem redoMenuItem = editMenu.getItems().stream()
                    .filter(item -> "redoMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'redoMenuItem' non trovato"));
            assert redoMenuItem.isDisable();

            MenuItem zoomInMenuItem = editMenu.getItems().stream()
                    .filter(item -> "zoomInMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'zoomInMenuItem' non trovato"));
            assert zoomInMenuItem.isDisable();

            MenuItem zoomOutMenuItem = editMenu.getItems().stream()
                    .filter(item -> "zoomOutMenuItem".equals(item.getId()))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("MenuItem 'zoomOutMenuItem' non trovato"));
            assert zoomOutMenuItem.isDisable();

            // Verifica che tutti i pulsanti dei filtri siano disabilitati
            Set<Node> buttons = lookup("#filtersListPane .button").queryAll();
            for (Node node : buttons) {
                if (node instanceof Button button) {
                    verifyThat(button, NodeMatchers.isDisabled());
                }
            }
    }

    public void testHelpMenu() {
            // Verifica che il menu Help sia visibile e abilitato
            verifyThat("#helpMenu", isVisible());
            verifyThat("#helpMenu", isEnabled());

            // Apri il menu Help
            clickOn("#helpMenu");

            sleep(SLEEP_INTERVAL);

            // Verifica che i sotto-menu siano visibili
            verifyThat("#aboutMenuItem", isVisible());
            verifyThat("#moreInfoMenuItem", isVisible());

            // Test del popup "About"
            step("Test del popup About", () -> {
                // Clicca sull'opzione 'About'
                clickOn("#aboutMenuItem");
                sleep(SLEEP_INTERVAL);

                // Verifica che i componenti del popup siano visibili
                verifyThat("#developersVBox", isVisible());
                verifyThat("#versionLabel", isVisible());
                verifyThat("#dateLabel", isVisible());
                verifyThat("#developersTable", isVisible());

                // Verifica che la tabella abbia almeno una colonna
                verifyThat("#emailColumn", isVisible());
                verifyThat("#nameColumn", isVisible());

                // Chiudi il popup
                clickOn(".button"); // Supponendo che ci sia un pulsante di chiusura
                sleep(SLEEP_INTERVAL);
            });

            // Test del popup "More Info"
            step("Test del popup More Info", () -> {
                // Clicca sull'opzione 'More Info'
                clickOn("#helpMenu");
                clickOn("#moreInfoMenuItem");
                sleep(SLEEP_INTERVAL);

                // Verifica che i componenti del popup siano visibili
                verifyThat("#developersVBox", isVisible());
                verifyThat("#shortcutListTable", isVisible());

                // Verifica che la tabella abbia almeno una colonna
                verifyThat("#shortcutName", isVisible());
                verifyThat("#shortcutCommand", isVisible());
                verifyThat("#shortcutDescription", isVisible());

                // Chiudi il popup
                clickOn(".button"); // Supponendo che ci sia un pulsante di chiusura
                sleep(SLEEP_INTERVAL);
            });
    }

    private void setLanguageEnglish(){
        clickOn("#editMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#changeLanguageMenu");
        sleep(SLEEP_INTERVAL);
        moveTo("#enUSMenuItem");
        clickOn("#enUSMenuItem");
    }


    private void testFileMenuAndLoadFile() {
            // Simula il clic sul menu File -> Open
            clickOn("#fileMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#openMenuItem");
            sleep(SLEEP_INTERVAL);

            // Verifica che il file sia stato caricato correttamente
            verifyThat("#filtersListPane", isEnabled());
            sleep(SLEEP_INTERVAL);

            verifyThat("#infobarText", nnode -> {
                if (nnode instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.imageOpened"));
                }
                return false;
            });

    }

    private void testFilters() {
            // Cerca tutti i pulsanti nella lista dei filtri
            Set<Node> buttons = lookup("#filtersListPane .button").queryAll();
            for (Node node : buttons) {
                if (node instanceof Button button) {
                    String filterName = button.getText();
                    System.out.println("Trovato pulsante filtro: " + filterName);

                    // Simula il clic sul pulsante del filtro
                    clickOn(button);
                    sleep(SLEEP_INTERVAL);

                    // Verifica dinamica del messaggio nell'infobar utilizzando i label
                    String expectedMessage = translationsController.translate("label.filterAdded");
                    verifyThat("#infobarText", nnode -> {
                        if (nnode instanceof Text text) {
                            return text.getText().contains(expectedMessage);
                        }
                        return false;
                    });

                    // Pausa tra le iterazioni per evitare conflitti nei test
                    sleep(SLEEP_INTERVAL);
                }
            }
    }

    private void testPipelineButton() {
        testFilters(); // Riempie i filtri per avere una pipeline da eseguire
            verifyThat("#runPipelineButton", isEnabled());
            sleep(SLEEP_INTERVAL);
            clickOn("#runPipelineButton");
            sleep(SLEEP_INTERVAL);

            // Verifica messaggio della pipeline eseguita
            verifyThat("#infobarText", node -> {
                if (node instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.pipelineExecuted"));
                }
                return false;
            });
    }

    private void testDeletePipelineButton() {
        testFilters(); // Riempie i filtri per avere una pipeline da eliminare
            verifyThat("#deleteButton", isEnabled());
            sleep(SLEEP_INTERVAL);
            clickOn("#deleteButton");
            sleep(SLEEP_INTERVAL);

            // Verifica messaggio della pipeline svuotata
            verifyThat("#infobarText", node -> {
                if (node instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.pipelineCleared"));
                }
                return false;
            });
    }

    private void testUndoButton() {
            verifyThat("#undoButton", isEnabled());
            sleep(SLEEP_INTERVAL);
            clickOn("#undoButton");
            sleep(SLEEP_INTERVAL);

            // Verifica messaggio dell'undo
            verifyThat("#infobarText", node -> {
                if (node instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.undo"));
                }
                return false;
            });
    }

    private void testRedoButton() {
            verifyThat("#redoButton", isEnabled());
            sleep(SLEEP_INTERVAL);
            clickOn("#redoButton");
            sleep(SLEEP_INTERVAL);

            // Verifica messaggio del redo
            verifyThat("#infobarText", node -> {
                if (node instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.redo"));
                }
                return false;
            });
    }

    private void testExportFile() {
            try {
                setExportTempDir();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            clickOn("#fileMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#exportMenuItem");
            sleep(SLEEP_INTERVAL);
            press(KeyCode.ENTER);
            sleep(SLEEP_INTERVAL);

            // Verifica messaggio di esportazione riuscita
            verifyThat("#infobarText", node -> {
                if (node instanceof Text text) {
                    return text.getText().contains(translationsController.translate("label.imageExported"));
                }
                return false;
            });

    }
}