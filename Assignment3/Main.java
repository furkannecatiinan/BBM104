import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is the entry point for the application, launching the game by creating a new GameController.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        new GameController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
