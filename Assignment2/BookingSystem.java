import java.io.IOException;
import java.io.PrintWriter;

public class BookingSystem {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java BookingSystem <inputFilePath> <outputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            VoyageManager voyageManager = new VoyageManager(writer);
            CommandReader commandReader = new CommandReader(inputFilePath, outputFilePath, voyageManager);
            commandReader.processCommands();
        } catch (IOException e) {
            System.err.println("An error occurred while initializing the BookingSystem: " + e.getMessage());
        }
    }
}
