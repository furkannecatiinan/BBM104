import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class manages the main game flow, including the setup of the game scene and handling user interactions.
 */
public class GameController {
    private GameScene gameScene;
    private Player player;
    private Pane rootPane = new Pane();

    /**
     * Constructs a GameController, setting up the initial game environment including the stage, labels, and player.
     *
     * @param primaryStage the primary stage for this application, onto which the scene is set
     */
    public GameController(Stage primaryStage) {
        Label fuelLabel = new Label();
        Label moneyLabel = new Label();
        Label haulLabel = new Label();
        player = new Player(1400, 1000, fuelLabel, moneyLabel, haulLabel, rootPane);
        gameScene = new GameScene(primaryStage, rootPane, player);

        primaryStage.setTitle("HULOAD");
        primaryStage.setScene(gameScene.getScene());
        primaryStage.show();

        startGame();

    }

    /**
     * Starts the game logic by enabling key press handling for player movement.
     */
    public void startGame() {
        gameScene.getScene().setOnKeyPressed(event -> player.handleKeyPress(event.getCode()));
    }
}
