import java.io.PrintWriter;
import java.util.*;

/**
 * Represents a graph structure with nodes, edges, and various functionalities to find shortest paths, barely connected paths 
 * and analyze the graph.
 */
public class Graph {
    private Map<String, List<Edge>> adjacencyList;
    private List<Edge> edges;
    private String startPoint;
    private String endPoint;
    private int totalDistance;
    public static int sumOfBarelyConnectedMap = 0;
    Reader reader = new Reader();
    public List<String> outputLines;


    /**
     * Constructs a new Graph object with an empty adjacency list, edge list, and initializes
     * other properties.
     */
    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.edges = new ArrayList<>();
        this.totalDistance = 0;
        this.outputLines = new ArrayList<>();

    }
    /**
     * Adds a line of output to the list of output lines.
     *
     * @param line the line to be added
     */
    public void addOutputLine(String line) {
        outputLines.add(line);
    }

    /**
     * Prints the output lines to the given writer.
     *
     * @param writer the writer to print the output to
     */
    public void printOutput(PrintWriter writer) {
        for (int i = 0; i < outputLines.size(); i++) {
            writer.print(outputLines.get(i));
            if (i < outputLines.size() - 1) {
                writer.println();
            }
        }
    }

    /**
     * Adds an edge to the graph with the given source and destination nodes, distance, and identifier.
     *
     * @param edge the edge to be added
     */
    public void addEdge(Edge edge) {
        adjacencyList.putIfAbsent(edge.getSource(), new ArrayList<>());
        adjacencyList.get(edge.getSource()).add(edge);
        adjacencyList.putIfAbsent(edge.getDestination(), new ArrayList<>());
        Edge reverseEdge = new Edge(edge.getDestination(), edge.getSource(), edge.getDistance(), edge.getId(), false);
        adjacencyList.get(edge.getDestination()).add(reverseEdge);
        edges.add(edge);
    }

    /**
     * Sets the start and end points of the graph.
     *
     * @param startPoint the start point of the graph
     * @param endPoint   the end point of the graph
     */
    public void setStartEndPoints(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Returns the adjacency list of the graph.
     *
     * @return the adjacency list of the graph
     */
    public Map<String, List<Edge>> getAdjacenyList() {
        return adjacencyList;
    }

    /**
     * Returns the start point of the graph.
     *
     * @return the start point of the graph
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * Returns the end point of the graph.
     *
     * @return the end point of the graph
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Finds the shortest path between the start and end points in the graph using Dijkstra's algorithm.
     *
     * @param graph the graph in which to find the shortest path
     * @return the list of nodes representing the shortest path
     */
    public List<String> findShortestPath(Graph graph) {
        Map<String, List<Edge>> adjacencyList = graph.getAdjacenyList();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Set<String> visited = new HashSet<>();
        String startPoint = graph.getStartPoint();
        String endPoint = graph.getEndPoint();

        adjacencyList.keySet().forEach(vertex -> distances.put(vertex, Integer.MAX_VALUE));

        if (!distances.containsKey(startPoint)) {
            return new ArrayList<>();
        }

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        distances.put(startPoint, 0);
        nodeQueue.add(new Node(startPoint, 0));

        while (!nodeQueue.isEmpty()) {
            Node currentNode = nodeQueue.poll();
            String currentCityName = currentNode.vertex;

            if (visited.contains(currentCityName)) {
                continue;
            }

            visited.add(currentCityName);

            if (!adjacencyList.containsKey(currentCityName)) {
                continue;
            }

            List<Edge> edges = adjacencyList.get(currentCityName);
            if (edges != null) {
                edges.sort(Comparator.comparingInt(Edge::getDistance).thenComparingInt(Edge::getId));
            }

            for (Edge edge : edges) {
                String destination = edge.getDestination();
                int newDistance = distances.get(currentCityName) + edge.getDistance();
                if (newDistance < distances.getOrDefault(destination, Integer.MAX_VALUE)) {
                    distances.put(destination, newDistance);
                    predecessors.put(destination, currentCityName);
                    nodeQueue.add(new Node(destination, newDistance));
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String step = endPoint;
        while (step != null && predecessors.containsKey(step)) {
            path.addFirst(step);
            step = predecessors.get(step);
        }

        path.add(0, startPoint);

        if (path.isEmpty() || !path.getFirst().equals(startPoint)) {
            outputLines.add("No valid path found from '" + startPoint + "' to '" + endPoint + "'.");
            return new ArrayList<>();
        }

        int totalDistance = distances.get(endPoint);
        outputLines.add("Fastest Route from " + startPoint + " to " + endPoint + " (" + totalDistance + " KM):");

        for (int i = 0; i < path.size() - 1; i++) {
            String city = path.get(i);
            String nextCity = path.get(i + 1);
            Edge connectingEdge = findOriginalEdge(city, nextCity);
            if (connectingEdge != null) {
                outputLines.add(String.valueOf(connectingEdge));
            } else {
                outputLines.add("No direct edge found between " + city + " and " + nextCity);
            }
        }

        return path;
    }

    /**
     * Finds the original edge between two nodes according to input file, regardless of direction.
     *
     * @param source      the source node
     * @param destination the destination node
     * @return the original edge between the nodes
     */
    private Edge findOriginalEdge(String source, String destination) {
        for (Edge edge : edges) {
            if ((edge.getSource().equals(source) && edge.getDestination().equals(destination)) ||
                    (edge.getSource().equals(destination) && edge.getDestination().equals(source))) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Visits a node, marking it as visited and adding its edges to the priority queue.
     *
     * @param nodeName the node to visit
     * @param visited  the set of visited nodes
     * @param pq       the priority queue of edges
     */
    private void visit(String nodeName, Set<String> visited, PriorityQueue<Edge> pq) {
        visited.add(nodeName);
        List<Edge> edges = adjacencyList.get(nodeName);

        if (edges == null) {
            return;
        }

        for (Edge edge : edges) {
            if (!visited.contains(edge.getDestination())) {
                pq.add(edge);
            }
        }
    }
    
     /**
     * Finds the shortest path using a temporary edge list and Dijkstra's algorithm.
     *
     * @param edgeList the list of edges to use
     * @return the list of nodes representing the shortest path
     */
    public List<String> findShortestPath(List<Edge> edgeList) {
        Map<String, List<Edge>> tempAdjacencyList = new HashMap<>();
        for (Edge edge : edgeList) {
            tempAdjacencyList.putIfAbsent(edge.getSource(), new ArrayList<>());
            tempAdjacencyList.get(edge.getSource()).add(edge);
            tempAdjacencyList.putIfAbsent(edge.getDestination(), new ArrayList<>());
            Edge reverseEdge = new Edge(edge.getDestination(), edge.getSource(), edge.getDistance(), edge.getId());
            tempAdjacencyList.get(edge.getDestination()).add(reverseEdge);
        }

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Set<String> visited = new HashSet<>();

        tempAdjacencyList.keySet().forEach(vertex -> distances.put(vertex, Integer.MAX_VALUE));

        if (!distances.containsKey(startPoint)) {
            return new ArrayList<>();
        }

        PriorityQueue<Node> nodeQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        distances.put(startPoint, 0);
        nodeQueue.add(new Node(startPoint, 0));

        while (!nodeQueue.isEmpty()) {
            Node currentNode = nodeQueue.poll();
            String currentCityName = currentNode.vertex;

            if (visited.contains(currentCityName)) {
                continue;
            }

            visited.add(currentCityName);

            if (!tempAdjacencyList.containsKey(currentCityName)) {
                continue;
            }

            List<Edge> edges = tempAdjacencyList.get(currentCityName);
            if (edges != null) {
                edges.sort(Comparator.comparingInt(Edge::getDistance).thenComparingInt(Edge::getId));
            }

            for (Edge edge : edges) {
                String destination = edge.getDestination();
                int newDistance = distances.get(currentCityName) + edge.getDistance();
                if (newDistance < distances.getOrDefault(destination, Integer.MAX_VALUE)) {
                    distances.put(destination, newDistance);
                    predecessors.put(destination, currentCityName);
                    nodeQueue.add(new Node(destination, newDistance));
                }
            }
        }

        LinkedList<String> path = new LinkedList<>();
        String step = endPoint;
        while (step != null && predecessors.containsKey(step)) {
            path.addFirst(step);
            step = predecessors.get(step);
        }

        path.add(0, startPoint);

        if (path.isEmpty() || !path.getFirst().equals(startPoint)) {
            outputLines.add("No valid path found from '" + startPoint + "' to '" + endPoint + "'.");
            return new ArrayList<>();
        }

        int totalDistance = distances.get(endPoint);
        outputLines.add("Fastest Route from " + startPoint + " to " + endPoint + " on Barely Connected Map " + "(" + totalDistance + " KM):");
        for (int i = 0; i < path.size() - 1; i++) {
            String city = path.get(i);
            String nextCity = path.get(i + 1);
            Edge connectingEdge = tempAdjacencyList.get(city).stream()
                    .filter(edge -> edge.getDestination().equals(nextCity))
                    .findFirst()
                    .orElse(null);
            if (connectingEdge != null) {
                if (connectingEdge.isOriginalOrientation()) {
                    outputLines.add(String.valueOf(connectingEdge));
                } else {
                    Edge originalEdge = findOriginalEdge(connectingEdge.getSource(), connectingEdge.getDestination());
                    if (originalEdge != null) {
                        outputLines.add(String.valueOf(originalEdge));
                    } else {
                        outputLines.add("No direct edge found between " + connectingEdge.getSource() + " and " + connectingEdge.getDestination());
                    }
                }
            } else {
                outputLines.add("No direct edge found between " + city + " and " + nextCity);
            }
        }
        int totalPathDistance2 = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city = path.get(i);
            String nextCity = path.get(i + 1);
            Edge connectingEdge = adjacencyList.get(city).stream()
                    .filter(edge -> edge.getDestination().equals(nextCity))
                    .findFirst()
                    .orElse(null);
            if (connectingEdge != null) {
                totalPathDistance2 += connectingEdge.getDistance();
            }
        }
        return path;
    }



    /**
     * Finds the barely connected map (minimum spanning tree) of the graph using Prim's algorithm.
     *
     * @return the list of edges in the barely connected map
     */
    public List<Edge> findBarelyConnectedMap() {
        List<Edge> mst = new ArrayList<>();
        PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getDistance).thenComparingInt(Edge::getId));
        Set<String> visited = new HashSet<>();
        visit(startPoint, visited, edgeQueue);

        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            String source = edge.getSource();
            String destination = edge.getDestination();

            if (visited.contains(source) && visited.contains(destination)) {
                continue;
            }

            mst.add(edge);

            if (!visited.contains(source)) {
                visit(source, visited, edgeQueue);
            }

            if (!visited.contains(destination)) {
                visit(destination, visited, edgeQueue);
            }
        }

        Collections.sort(mst, Comparator.comparingInt(Edge::getDistance).thenComparingInt(Edge::getId));
        outputLines.add("Roads of Barely Connected Map is:");
        for (Edge edge : mst) {
            if (edge.isOriginalOrientation()) {
                outputLines.add(String.valueOf(edge));
            } else {
                Edge originalEdge = findOriginalEdge(edge.getSource(), edge.getDestination());
                if (originalEdge != null) {
                    outputLines.add(String.valueOf(originalEdge));
                } else {
                    outputLines.add("No direct edge found between " + edge.getSource() + " and " + edge.getDestination());
                }
            }
        }

        sumOfBarelyConnectedMap = 0;
        for (Edge edge : mst) {
            sumOfBarelyConnectedMap += edge.getDistance();
        }

        this.edges.clear();
        this.edges.addAll(mst);
        this.adjacencyList.clear();

        for (Edge edge : mst) {
            addEdge(edge);
        }
        return mst;
    }
    /**
     * Analyzes and outputs the ratio of construction material usage between the barely connected
     * and original maps.
     */
    public void ratioOfAmount() {
        outputLines.add("Analysis:");
        outputLines.add(String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f", (double) sumOfBarelyConnectedMap / reader.getTotalDistance()));
    }

    /**
     * Calculates the total distance of a given path.
     *
     * @param path the list of nodes representing the path
     * @return the total distance of the path
     */
    public int calculatePathDistance(List<String> path) {
        int totalPathDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city = path.get(i);
            String nextCity = path.get(i + 1);
            Edge connectingEdge = adjacencyList.get(city).stream()
                    .filter(edge -> edge.getDestination().equals(nextCity))
                    .findFirst()
                    .orElse(null);
            if (connectingEdge != null) {
                totalPathDistance += connectingEdge.getDistance();
            }
        }
        return totalPathDistance;
    }

    /**
     * Represents a node in the graph with a vertex and a distance.
     */
    private class Node {
        String vertex;
        int distance;

        Node(String vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "vertex='" + vertex + '\'' +
                    ", distance=" + distance +
                    '}';
        }
    }
}

