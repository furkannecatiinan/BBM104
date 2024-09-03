import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * BNFReader reads and parses BNF grammar rules from a file into a HashMap.
 * Each non-terminal symbol is mapped to a list of its production rules,
 * allowing for efficient access and manipulation of the grammar.
 */
public class BNFReader {

    private HashMap<String, List<String>> productions;

    public BNFReader() {
        this.productions = new HashMap<>();
    }
    /**
     * Reads BNF grammar from a specified file and stores it in the productions HashMap.
     * Each line should contain a non-terminal followed by "->" and a list of productions separated by "|".
     *
     * @param filename the path to the file containing the BNF grammar
     * @throws IOException if an I/O error occurs reading from the file
     */
    public void readGrammar(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("->");
            if (parts.length < 2) {
                continue;
            }
            String nonTerminal = parts[0].trim();
            String[] rules = parts[1].split("\\|");
            productions.put(nonTerminal, Arrays.asList(rules));
        }
        reader.close();
    }
    /**
     * Returns the HashMap containing all non-terminals and their corresponding production rules.
     *
     * @return the productions HashMap
     */
    public HashMap<String, List<String>> getProductions() {
        return productions;
    }
}
