import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code DiceGame} class represents the entry point for a dice game application.
 *
 * It reads player information and game moves from an input file, processes the game logic,
 * and outputs the results to an output file. The paths for the input and output files
 * are provided as command-line arguments.
 */
public class DiceGame {

    /**
     * The main method of the dice game. It initializes the game with players, processes each turn
     * based on input from a file, and writes the outcomes to another file.
     *
     * Command-line arguments:
     *  args[0]: Path to the input file.
     *  args[1]: Path to the output file.
     *
     * The input file should have the following format:
     * - The first line contains the number of players.
     * - The second line lists the names of the players, separated by commas.
     * - Subsequent lines represent each turn in the game.
     *
     * Each turn's outcome and the final game results are written to the specified output file.
     *
     * @param args the command-line arguments, expecting input and output file paths.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            return;
        }
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        Game game = new Game();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             PrintWriter writer = new PrintWriter(outputFilePath)) {
            int numberOfPlayers = Integer.parseInt(br.readLine().trim());
            String[] playerNames = br.readLine().split(",");
            for (String playerName : playerNames) {
                game.addPlayer(new Player(playerName.trim()));
            }

            String line;
            while ((line = br.readLine()) != null) {
                game.playTurn(line, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
