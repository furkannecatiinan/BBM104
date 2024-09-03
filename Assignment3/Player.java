import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * This class represents the player in the game, handling player movement and interactions with the game environment.
 */
public class Player {
    public Timeline fuelConsumptionTimeline;
    private ImageView imageView;
    private int fuel = 3000;
    private int money = 0;
    private int haul = 0;
    private int gameWidth;
    private int gameHeight;
    private Label fuelLabel;
    private Label moneyLabel;
    private Label haulLabel;
    private Pane rootPane;
    private GameScene gameScene;
    private Timeline movementTimer;

    /**
     * Constructs a Player object with the specified game width, height, labels, and root pane.
     *
     * @param gameWidth the width of the game scene
     * @param gameHeight the height of the game scene
     * @param fuelLabel the label displaying the player's fuel
     * @param moneyLabel the label displaying the player's money
     * @param haulLabel the label displaying the player's haul
     * @param rootPane the root pane of the game scene
     */
    public Player(int gameWidth, int gameHeight, Label fuelLabel, Label moneyLabel, Label haulLabel, Pane rootPane) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.fuelLabel = fuelLabel;
        this.moneyLabel = moneyLabel;
        this.haulLabel = haulLabel;
        this.rootPane = rootPane;


        Image image = new Image("file:src/assets/drill/drill_25.png");
        imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setX(150);
        imageView.setY(50);
        startMovementTimer();
        initFuelConsumption();

    }

    /**
     * Starts a timer for player movement.
     */
    private void startMovementTimer() {
        Timeline noMovementTimer = new Timeline(new KeyFrame(Duration.seconds(0.8), event -> {
            double currentY = imageView.getY();
            double newY = currentY + 50;
            if (newY <= gameHeight - imageView.getFitHeight()) {
                if (!isPathClear((int) imageView.getX() / 50, (int) newY / 50)) {
                    return;
                }
                imageView.setY(newY);
            }
        }));
        noMovementTimer.setCycleCount(Animation.INDEFINITE);
        noMovementTimer.play();
    }
    /**
     * Initializes the fuel consumption timer.
     */
    private void initFuelConsumption() {
        fuelConsumptionTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            decreaseFuel();
        }));
        fuelConsumptionTimeline.setCycleCount(Timeline.INDEFINITE);
        fuelConsumptionTimeline.play();
    }

    /**
     * Handles key press events for player movement.
     *
     * @param code the key code corresponding to the key pressed
     */
    public void handleKeyPress(KeyCode code) {
        double newX = imageView.getX();
        double newY = imageView.getY();
        double oldX = newX;
        double oldY = newY;
        decreaseFuel();
        switch (code) {

            case UP:
                newY -= 50;
                if (newY >= 0 && isPathClear((int) newX / 50, (int) newY / 50)) {
                    imageView.setY(newY);
                    changeDrillImage("file:src/assets/drill/drill_27.png");
                } else {
                    newY = oldY;
                    changeDrillImage("file:src/assets/drill/drill_27.png");
                }
                break;
            case DOWN:
                newY += 50;
                if (newY <= gameHeight - imageView.getFitHeight() && dig((int) newX / 50, (int) newY / 50)) {
                    imageView.setY(newY);
                    changeDrillImage("file:src/assets/drill/drill_40.png");
                } else {
                    newY = oldY;
                    changeDrillImage("file:src/assets/drill/drill_40.png");
                }
                break;
            case LEFT:
                newX -= 50;
                if (newX >= 0 && dig((int) newX / 50, (int) newY / 50)) {
                    imageView.setX(newX);
                    changeDrillImage("file:src/assets/drill/drill_01.png");
                } else {
                    newX = oldX;
                    changeDrillImage("file:src/assets/drill/drill_01.png");
                }
                break;
            case RIGHT:
                newX += 50;
                if (newX + imageView.getFitWidth() <= gameWidth && dig((int) newX / 50, (int) newY / 50)) {
                    imageView.setX(newX);
                    changeDrillImage("file:src/assets/drill/drill_59.png");
                } else {
                    newX = oldX;
                    changeDrillImage("file:src/assets/drill/drill_59.png");
                }
                break;
            default:
                break;
        }
    }
    /**
     * Checks if the path is clear for the player to move to the specified position.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the path is clear, false otherwise
     */
    public boolean isPathClear(int x, int y) {
        if (x >= 0 && x < gameScene.tileInfos.size() && y >= 0 && y < gameScene.tileInfos.get(x).size()) {
            TileInfo tile = gameScene.tileInfos.get(x).get(y);
            return tile.getType() == Blocks.BlockType.EMPTY;
        }
        return false;
    }

    /**
     * Digs at the specified position, updating the player's money and haul accordingly.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return true if the dig was successful, false otherwise
     */
    public boolean dig(int x, int y) {
        if (x >= 0 && x < gameScene.tileInfos.size() && y >= 0 && y < gameScene.tileInfos.get(x).size()) {
            TileInfo tile = gameScene.tileInfos.get(x).get(y);
            if (tile.getType() == Blocks.BlockType.LAVA) {
                gameScene.endGameForLava();
                return false;
            } else if (tile.getType() == Blocks.BlockType.EMPTY || tile.getValue() >= 0) {
                ImageView blockImage = gameScene.tiles.get(x).get(y);
                money += tile.getValue();
                haul += tile.getWeight();
                tile.setType(Blocks.BlockType.EMPTY);
                rootPane.getChildren().remove(blockImage);
                decreaseFuel();
                updateUI();
                return true;
            }
        }
        return false;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public int getFuel() {
        return fuel;
    }
    public int getMoney() {
        return money;
    }
    public int getHaul() {
        return haul;
    }

    /**
     * Changes the drill image to the specified image path, for movement animation.
     *
     * @param imagePath the path to the new drill image
     */
    private void changeDrillImage(String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }

    /**
     * Decreases the player's fuel by 10 units, ending the game if the fuel reaches 0.
     */
    public void decreaseFuel() {
        fuel -= 10;
        if (fuel <= 0) {
            fuel = 0;
            gameScene.endGameForFuel();
            stopFuelConsumption();
        }
        updateUI();
    }

    /**
     * Updates the player's UI elements to reflect the current fuel, money, and haul values.
     */
    public void updateUI() {
        Platform.runLater(() -> {
            fuelLabel.setText("Fuel: " + fuel);
            moneyLabel.setText("Money: " + money);
            haulLabel.setText("Haul: " + haul);
        });
    }
    /**
     * Sets the labels for the player's fuel, money, and haul.
     *
     * @param fuelLabel the label displaying the player's fuel
     * @param moneyLabel the label displaying the player's money
     * @param haulLabel the label displaying the player's haul
     */
    public void setLabels(Label fuelLabel, Label moneyLabel, Label haulLabel) {
        this.fuelLabel = fuelLabel;
        this.moneyLabel = moneyLabel;
        this.haulLabel = haulLabel;
    }
    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }
    /**
     * Stops the fuel consumption timer.
     */
    public void stopFuelConsumption() {
        if (fuelConsumptionTimeline != null) {
            fuelConsumptionTimeline.stop();
        }
    }
    public void setFuel(int i) {
        fuel = i;
    }
    public void setMoney(int i) {
        money = i;
    }
}
