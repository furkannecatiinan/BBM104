import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * The Reader class provides methods to read graph data from an input file and create a Graph object.
 */
public class Reader {
    /**
     * The total distance of all edges read from the input file.
     */

    public static int totalDistance = 0;
 
    /**
     * Reads the input file and creates a Graph object.
     *
     * @param inputFilePath the path to the input file
     * @return the Graph object created from the input file data
     */
    public static Graph readInputFile(String inputFilePath) {
        Graph graph = new Graph();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] cities = line.split("\t");
                String startPoint = cities[0];
                String endPoint = cities[1];
                graph.setStartEndPoints(startPoint, endPoint);
            }

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    String source = parts[0];
                    String destination = parts[1];
                    int distance = Integer.parseInt(parts[2]);
                    int id = Integer.parseInt(parts[3]);
                    totalDistance += distance;

                    Edge edge = new Edge(source, destination, distance, id);
                    graph.addEdge(edge);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;

    }
    /**
     * Returns the total distance of all edges read from the input file.
     *
     * @return the total distance
     */
    public int getTotalDistance() {
        return totalDistance;
    }
}