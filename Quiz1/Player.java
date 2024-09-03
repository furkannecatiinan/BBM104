import java.io.PrintWriter;

/**
 * Represents a player in a dice game. Each player has a name and a score that
 * can be updated based on the outcome of dice rolls.
 */
class Player {
    private String name;
    private int score;

    /**
     * Constructs a new Player with the specified name. The initial score is set to 0.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Simulates the player throwing two dice. Depending on the outcome, the player's
     * score is updated. If both dice show 1, the player's score is reset to 0.
     * If any one of the dice shows 1, the score does not change. Otherwise, the score
     * is increased by the sum of the dice. The outcome is printed to the provided PrintWriter.
     *
     * @param dice1 The value of the first dice.
     * @param dice2 The value of the second dice.
     * @param writer The PrintWriter to which the outcome of the throw is written.
     */
    public void throwDice(int dice1, int dice2, PrintWriter writer) {
        if (dice1 == 1 && dice2 == 1) {
            score = 0; // Resets score if both dice are 1.
            writer.println(name + " threw 1-1. Game over " + name + "!");
        } else {
            if (dice1 == 1 || dice2 == 1) {
                writer.println(name + " threw " + dice1 + "-" + dice2 + " and " + name + "’s score is " + score + ".");
                return;
            } else {
                int points = dice1 + dice2;
                score += points;
                writer.println(name + " threw " + dice1 + "-" + dice2 + " and " + name + "’s score is " + score + ".");
            }
        }
    }

    /**
     * Allows the player to skip their turn. The current score is printed to the
     * provided PrintWriter without any changes to the score.
     *
     * @param writer The PrintWriter to which the action of skipping the turn is written.
     */
    public void skipTurn(PrintWriter writer) {
        writer.println(name + " skipped the turn and " + name + "’s score is " + score + ".");
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current score of the player.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }
}
