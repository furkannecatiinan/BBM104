import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game logic for a dice game. This class manages the players
 * in the game, processes turns based on dice rolls, and determines the game outcome.
 */
class Game {
    private List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    /**
     * Adds a new player to the game.
     *
     * @param player The player to be added to the game.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Processes a single turn in the game based on the input line, which represents
     * the dice roll outcome, and updates the game state accordingly. It also handles
     * the game's turn logic, including skipping turns and removing players under
     * certain conditions.
     *
     * @param line The string representing the dice roll outcome, formatted as "dice1-dice2".
     * @param writer The PrintWriter to output the results of the turn.
     */
    public void playTurn(String line, PrintWriter writer) {
        if (players.isEmpty()) return; // No action if no players are in the game.

        Player currentPlayer = players.get(currentPlayerIndex);
        if ("0-0".equals(line)) {
            // Skip the current player's turn.
            currentPlayer.skipTurn(writer);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            // Parse the dice rolls and process the turn.
            String[] diceRolls = line.split("-");
            int dice1 = Integer.parseInt(diceRolls[0]);
            int dice2 = Integer.parseInt(diceRolls[1]);
            currentPlayer.throwDice(dice1, dice2, writer);

            // Remove the player from the game if they rolled double ones.
            if (dice1 == 1 && dice2 == 1) {
                players.remove(currentPlayerIndex);

                // Adjust the currentPlayerIndex accordingly.
                if (currentPlayerIndex == players.size()) {
                    currentPlayerIndex = 0;
                }
            } else {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            }
        }

        // Declare the winner if only one player remains.
        if (players.size() == 1) {
            writer.print(players.get(0).getName() + " is the winner of the game with the score of " +
                    players.get(0).getScore() + ". Congratulations " + players.get(0).getName() + "!");
        }
    }
}
