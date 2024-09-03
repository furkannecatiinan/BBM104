

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the main scene of the game, handling all visual components, the game map, and interactions.
 */
public class GameScene {
    private Scene scene;
    private Pane rootPane;
    private Player player;
    public Label infoLabel1;
    public Label infoLabel2;
    public Label infoLabel3;
    //private Map map;
    private int width = 1400;
    private int height = 1000;
    public List<List<ImageView>> tiles = new ArrayList<>();
    public List<List<TileInfo>> tileInfos = new ArrayList<>();
    private Random randomValue = new Random();
    private Rectangle quitButtonRect;
    private Label quitButtonLabel;

    /**
     * Constructs the game scene, sets up the game environment, map, and UI elements.
     *
     * @param primaryStage The primary stage of the application where the scene will be displayed.
     * @param pane The root pane used for adding visual components.
     * @param player The player instance interacting within the game.
     */
    public GameScene(Stage primaryStage, Pane pane, Player player) {
        this.rootPane = pane;
        this.player = player;

        rootPane.setPrefSize(1400, 1000);
        setupBackground();
        setupMap();
        setupLabels();
        player.setGameScene(this);
        player.setLabels(infoLabel1, infoLabel2, infoLabel3);

        this.scene = new Scene(pane, 1400, 1000);
        primaryStage.setTitle("HULOAD");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        setupButtons();

    }

    /**
     * Sets up the initial game map with tiles based on predefined block types.
     */
    private void setupMap() {
        int tileSize = 50;

        for (int x = 0; x < width; x += tileSize) {
            List<ImageView> column = new ArrayList<>();
            List<TileInfo> infoColumn = new ArrayList<>();

            for (int y = 0; y < height; y += tileSize) {
                Blocks.BlockType type;
                if (y < 250) {
                    type = Blocks.BlockType.EMPTY; // Gökyüzü bloğu
                } else if (y == 250) {
                    type = Blocks.BlockType.TOPSOIL; // En üst katman topsoil
                } else if (x == 0 || x == width - tileSize || y == height - tileSize) {
                    type = Blocks.BlockType.OBSTACLE; // Kenarlarda engel bloğu
                } else {
                    type = determineBlockType(randomValue.nextDouble()); // İç kısımlarda rastgele bloklar
                }

                ImageView imageView = new ImageView(new Image(getImagePath(type)));
                imageView.setX(x);
                imageView.setY(y);
                rootPane.getChildren().add(imageView);
                column.add(imageView);
                TileInfo info = new TileInfo(type, type.getValue(), type.getWeight());
                infoColumn.add(info);
            }
            tiles.add(column);
            tileInfos.add(infoColumn);
        }
    }

    /**
     * Determines the block type based on a random value.
     *
     * @param randomValue The random value used to determine the block type.
     * @return The block type determined by the random value.
     */
    private Blocks.BlockType determineBlockType(double randomValue) {
        if (randomValue < 0.10) return Blocks.BlockType.LAVA;
        if (randomValue < 0.12) return Blocks.BlockType.DIAMOND;
        if (randomValue < 0.15) return Blocks.BlockType.GOLD;
        if (randomValue < 0.18) return Blocks.BlockType.AMAZONITE;
        if (randomValue < 0.20) return Blocks.BlockType.BRONZIUM;
        if (randomValue < 0.22) return Blocks.BlockType.EINSTEINIUM;
        if (randomValue < 0.25) return Blocks.BlockType.EMERALD;
        if (randomValue < 0.27) return Blocks.BlockType.IRONIUM;
        if (randomValue < 0.30) return Blocks.BlockType.PLATINIUM;
        if (randomValue < 0.32) return Blocks.BlockType.RUBY;
        if (randomValue < 0.34) return Blocks.BlockType.SILVERIUM;
        if (randomValue < 0.40) return Blocks.BlockType.OBSTACLE;
        if (randomValue == 12)  return Blocks.BlockType.OBSTACLE;
        if (randomValue == 15) return Blocks.BlockType.TOPSOIL;
        return Blocks.BlockType.SOIL;
    }

    /**
     * Returns the image path for a given block type.
     *
     * @param type The block type for which the image path is requested.
     * @return The image path for the given block type.
     */
    private String getImagePath(Blocks.BlockType type) {
        switch (type) {
            case SOIL:
                return Blocks.SOIL_IMAGE_PATH;
            case OBSTACLE:
                return Blocks.OBSTACLE_IMAGE_PATH;
            case GOLD:
                return Blocks.GOLD_IMAGE_PATH;
            case DIAMOND:
                return Blocks.DIAMOND_IMAGE_PATH;
            case LAVA:
                return Blocks.LAVA_IMAGE_PATH;
            case FLOOR:
                return Blocks.FLOOR_IMAGE_PATH;
            case TOPSOIL:
                return Blocks.TOPSOIL_IMAGE_PATH;
            case AMAZONITE:
                return Blocks.AMAZONITE_IMAGE_PATH;
            case BRONZIUM:
                return Blocks.BRONZIUM_IMAGE_PATH;
            case EINSTEINIUM:
                return Blocks.EINSTEINIUM_IMAGE_PATH;
            case EMERALD:
                return Blocks.EMERALD_IMAGE_PATH;
            case IRONIUM:
                return Blocks.IRONIUM_IMAGE_PATH;
            case PLATINIUM:
                return Blocks.PLATINIUM_IMAGE_PATH;
            case RUBY:
                return Blocks.RUBY_IMAGE_PATH;
            case SILVERIUM:
                return Blocks.SILVERIUM_IMAGE_PATH;
            case EMPTY:
                return Blocks.EMPTY_IMAGE_PATH;
            case SKY:
                return Blocks.SKY_IMAGE_PATH;
            default:
                throw new IllegalArgumentException("Invalid block type!");
        }
    }

    /**
     * Sets up the background of the game scene with a sky and ground.
     */
    private void setupBackground() {
        Rectangle ground = new Rectangle(0, 250, 1400, 750);
        ground.setFill(Color.DARKORANGE);
        rootPane.getChildren().add(ground);

        Rectangle sky = new Rectangle(0, 0, 1400, 250);
        sky.setFill(Color.SKYBLUE);
        rootPane.getChildren().add(sky);
        rootPane.getChildren().add(player.getImageView());
    }

    /**
     * Sets up the labels for displaying player information.
     */
    private void setupLabels() {
        infoLabel1 = new Label("Fuel: " + player.getFuel());
        infoLabel1.setTextFill(Color.WHITE);
        infoLabel1.setLayoutX(10);
        infoLabel1.setLayoutY(10);
        infoLabel1.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Font büyüklüğü 16


        infoLabel2 = new Label("Money: " + player.getMoney());
        infoLabel2.setTextFill(Color.WHITE);
        infoLabel2.setLayoutX(10);
        infoLabel2.setLayoutY(50);
        infoLabel2.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Font büyüklüğü 16

        infoLabel3 = new Label("Haul: " + player.getHaul());
        infoLabel3.setTextFill(Color.WHITE);
        infoLabel3.setLayoutX(10);
        infoLabel3.setLayoutY(90);
        infoLabel3.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Font büyüklüğü 16

        rootPane.getChildren().addAll(infoLabel1, infoLabel2, infoLabel3);
    }

    public Scene getScene() {
        return scene;
    }

    public Pane getPane() {
        return rootPane;
    }

    /**
     * Ends the game when the player hits lava.
     */
    public void endGameForLava() {
        Rectangle gameOverScreen = new Rectangle(0, 0, 1400, 1000);
        gameOverScreen.setFill(Color.RED);
        rootPane.getChildren().add(gameOverScreen);

        Label gameOverLabel = new Label("GAME OVER BECAUSE OF LAVA");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setLayoutX(1400 * 0.5 - 300);  // Pozisyon ayarlaması
        gameOverLabel.setLayoutY(1000 * 0.5 - 50);
        rootPane.getChildren().add(gameOverLabel);

        quitButtonRect.setVisible(true);
        quitButtonLabel.setVisible(true);
        quitButtonRect.toFront();
        quitButtonLabel.toFront();

        player.stopFuelConsumption();
        stopGameplay();
    }

    /**
     * Stops the gameplay by disabling key press handling and stopping the fuel consumption timeline.
     */
    public void stopGameplay() {
        scene.setOnKeyPressed(null);
        player.fuelConsumptionTimeline.stop();
    }

    /**
     * Ends the game when the player runs out of fuel.
     */
    public void endGameForFuel() {
        Rectangle gameOverScreen = new Rectangle(0, 0, 1400, 1000);
        gameOverScreen.setFill(Color.GREEN);
        rootPane.getChildren().add(gameOverScreen);

        Label gameOverLabel = new Label("GAME OVER BECAUSE OF RUN OUT OF FUEL" + "\n" + "Money: " + player.getMoney() + " $");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setLayoutX(1400 * 0.5 - 550);
        gameOverLabel.setLayoutY(1000 * 0.5 - 50);
        rootPane.getChildren().add(gameOverLabel);

        quitButtonRect.setVisible(true);
        quitButtonLabel.setVisible(true);
        quitButtonRect.toFront();
        quitButtonLabel.toFront();

        player.stopFuelConsumption();
        stopGameplay();
    }
    private Rectangle addFuelButtonRect;
    private Label addFuelButtonLabel;

    /**
     * Sets up the quit and add fuel buttons on the game scene.
     */
    private void setupButtons() {
        quitButtonRect = new Rectangle(1300, 20, 100, 50);
        quitButtonRect.setFill(Color.PURPLE);
        quitButtonRect.setStroke(Color.BLACK);
        quitButtonLabel = new Label("Quit");
        quitButtonLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        quitButtonLabel.setTextFill(Color.WHITE);
        quitButtonLabel.setLayoutX(1310);
        quitButtonLabel.setLayoutY(30);

        addFuelButtonRect = new Rectangle(1150, 20, 100, 50); // Konum ve boyut ayarları
        addFuelButtonRect.setFill(Color.GREEN);
        addFuelButtonRect.setStroke(Color.BLACK);
        addFuelButtonLabel = new Label("Add Fuel");
        addFuelButtonLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
        addFuelButtonLabel.setTextFill(Color.WHITE);
        addFuelButtonLabel.setLayoutX(1160);
        addFuelButtonLabel.setLayoutY(30);

        rootPane.getChildren().addAll(quitButtonRect, quitButtonLabel, addFuelButtonRect, addFuelButtonLabel);

        quitButtonRect.setOnMouseClicked(event -> Platform.exit());
        addFuelButtonRect.setOnMouseClicked(event -> addFuel());
    }

    /**
     * Adds fuel to the player's tank if they have enough money.
     */
    private void addFuel() {
        if (player.getMoney() >= 1000) {
            player.setFuel(player.getFuel() + 1000);
            player.setMoney(player.getMoney() - 1000);
            updateUI();
        }
    }

    /**
     * Updates the UI elements to reflect the current state of the player.
     */
    private void updateUI() {
        infoLabel1.setText("Fuel: " + player.getFuel());
        infoLabel2.setText("Money: " + player.getMoney());
    }
}

