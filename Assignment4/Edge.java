/**
 * Represents an edge in a graph with a source and destination node, a distance, 
 * an identifier, and an orientation flag indicating the original direction.
 */
public class Edge {
    protected final String source;
    protected final String destination;
    private final int distance;
    private final int id;
    private final boolean originalOrientation;

    /*
     * Constructs an edge with the given source and destination nodes, distance, identifier, and orientation flag.
     * 
     * @param source              the source node of the edge
     * @param destination         the destination node of the edge
     * @param distance            the distance between the source and destination
     * @param id                  the unique identifier of the edge
     * @param originalOrientation the original orientation flag of the edge
     */
    public Edge(String source, String destination, int distance, int id, boolean originalOrientation) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.id = id;
        this.originalOrientation = originalOrientation;
    }

    /*
     * Constructs an edge with the given source and destination nodes, distance, and identifier.
     * 
     * @param source    the source node of the edge
     * @param destination the destination node of the edge
     * @param distance  the distance between the source and destination
     * @param id        the unique identifier of the edge
     */
    public Edge(String source, String destination, int distance, int id) {
        this(source, destination, distance, id, true);
    }

    /*
     * Returns the source node of the edge.
     */
    public String getSource() {
        return source;
    }

    /*
     * Returns the destination node of the edge.
     */
    public String getDestination() {
        return destination;
    }

    /*
     * Returns the distance between the source and destination nodes.
     */
    public int getDistance() {
        return distance;
    }

    /*
     * Returns the unique identifier of the edge.
     */
    public int getId() {
        return id;
    }

    /*
     * Returns the original orientation flag of the edge.
     */
    public boolean isOriginalOrientation() {
        return originalOrientation;
    }

    /*
     * Returns the string representation of the edge.
     */
    @Override
    public String toString() {
        if (originalOrientation) {
            return source + "\t" + destination + "\t" + distance + "\t" + id;
        } else {
            return destination + "\t" + source + "\t" + distance + "\t" + id;
        }
    }
}
