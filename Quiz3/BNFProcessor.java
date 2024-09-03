import java.util.*;
import java.io.*;
/**
 * BNFProcessor handles the expansion of BNF grammar rules based on the productions provided.
 */
public class BNFProcessor {
    /*
    In my BNF grammar handling classes, BNFReader and BNFProcessor, the use of Java Collections (HashMap and List)
    is important for efficient storage and processing. HashMap is employed to map each non-terminal symbol to its
    corresponding list of production rules. This facilitates the efficient parsing and organization of grammar rules
    as each line is read. Each List<String> holds multiple production rules for a non-terminal in a sequenced and
    accessible manner, ideal for ordered storage and rapid indexing. This combination of HashMap for fast retrieval
    and List for orderly storage significantly optimizes memory and runtime efficiency, enabling swift and accurate
    grammar expansion and parsing in the BNFProcessor class.
     */
    private final HashMap<String, List<String>> productions;

    public BNFProcessor(HashMap<String, List<String>> productions) {
        this.productions = productions;
    }
    /**
     * Generates expanded grammar strings starting from the specified symbol.
     *
     * @param startSymbol the non-terminal symbol to start expansion from
     * @return a string representing the expanded grammar
     */
    public String generateStrings(String startSymbol) {
        return expand(startSymbol);
    }

    private String expand(String symbol) {
        if (!productions.containsKey(symbol)) {
            return symbol;
        }
        List<String> productionsList = productions.get(symbol);
        List<String> expandedProductions = new ArrayList<>();
        for (String production : productionsList) {
            expandedProductions.add(expandPart(production));
        }
        return "(" + String.join("|", expandedProductions) + ")";
    }

    private String expandPart(String part) {
        StringBuilder expandedProduction = new StringBuilder();
        for (int i = 0; i < part.length(); i++) {
            char c = part.charAt(i);
            if (Character.isUpperCase(c)) {
                expandedProduction.append(expand(String.valueOf(c)));
            } else {
                expandedProduction.append(c);
            }
        }
        return expandedProduction.toString();
    }

    /**
     * Writes the expanded grammar to a specified file.
     *
     * @param filename the path to the output file
     * @param content the content to write to the file
     * @throws IOException if an I/O error occurs during writing
     */
    public void writeOutput(String filename, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(content);
        writer.close();
    }
}
