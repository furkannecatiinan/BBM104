import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * The Main class serves as the entry point for the program. It reads input from a file,
 * processes the graph to find the shortest paths and the minimum spanning tree (barely connected map),
 * and writes the results to an output file.
 */
public class MapAnalyzer {

    /**
     * The main method which is the entry point of the program.
     * It processes the input file to create a graph, finds the shortest paths and the barely connected map,
     * and writes the results to the output file.
     *
     * @param args the command line arguments, where the first argument is the input file path and the second argument is the output file path
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Main <inputFilePath> <outputFilePath>");
            return;
        }
        String inputFilePath = args[0];
        String outputFilePath = args[1];
        Graph graph = Reader.readInputFile(inputFilePath);
        try(PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
            List<String> shortestPath = graph.findShortestPath(graph);
            int shortestPathDistanceOriginal = graph.calculatePathDistance(shortestPath);
            List<Edge> barelyConnectedPath = graph.findBarelyConnectedMap();
            List<String> shortestBarelyConnectedPath = graph.findShortestPath(barelyConnectedPath);
            int shortestBarelyConnectedPathDistance = graph.calculatePathDistance(shortestBarelyConnectedPath);
            graph.ratioOfAmount();
            graph.addOutputLine("Ratio of Fastest Route Between Barely Connected and Original Map: " + String.format("%.2f", (double) shortestBarelyConnectedPathDistance / shortestPathDistanceOriginal));
            graph.printOutput(writer);
        }
        catch (IOException e) {
            e.printStackTrace();
    }
}
}
