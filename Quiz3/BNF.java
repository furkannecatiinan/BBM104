import java.io.IOException;
import java.util.HashMap;
import java.util.List;
/**
 * Main class to execute the BNF grammar reading and processing.
 */
public class BNF {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java BNF <inputFilePath> <outputFilePath>");
            return;
        }
        BNFReader reader = new BNFReader();
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        try {
            reader.readGrammar(inputFilePath);
            HashMap<String, List<String>> productions = reader.getProductions();
            BNFProcessor processor = new BNFProcessor(productions);
            String result = processor.generateStrings("S");  // "S" varsayılan başlangıç sembolü olarak kabul edilir
            processor.writeOutput(outputFilePath, result);
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }
}
