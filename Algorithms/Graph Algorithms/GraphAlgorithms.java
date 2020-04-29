import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.HashMap;

/**
 * My implementation of various graph algorithms.
 *
 * @author Ian Andrew McLeod
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least 1 of the given arguments is null."
                    + " Please enter existing data.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given start vertex is not in the graph."
                    + " Please enter an existing vertex.");
        }


        List<Vertex<T>> myList = new ArrayList<>(graph.getVertices().size());
        Set<Vertex<T>> visitedSet = new HashSet<>(graph.getVertices().size());
        Queue<Vertex<T>> myQueue = new ArrayBlockingQueue<>(graph.getVertices().size());
        visitedSet.add(start);
        myList.add(start);
        myQueue.add(start);
        while (!myQueue.isEmpty()) {
            Vertex<T> v = myQueue.poll();
            List<VertexDistance<T>> adjVert = graph.getAdjList().get(v);
            for (VertexDistance<T> vert: adjVert) {
                if (!visitedSet.contains(vert.getVertex())) {
                    visitedSet.add(vert.getVertex());
                    myList.add(vert.getVertex());
                    myQueue.add(vert.getVertex());
                }
            }

        }

        return myList;

    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least 1 of the given arguments is null."
                    + " Please enter existing data.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given start vertex is not in the graph."
                    + " Please enter an existing vertex.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>(graph.getVertices().size());
        List<Vertex<T>> myList = new ArrayList<>(graph.getVertices().size());
        return dfsHelper(start, graph, visitedSet, myList);




    }

    /**
     * Recursive helper method for dfs
     *
     * @param start the start vertex
     * @param graph the graph traversed
     * @param vs the visited set of vertices
     * @param myList a list of vertices visited in order
     * @param <T> the type of data in each vertex
     * @return a list of vertices visited in order
     */

    private static <T> List<Vertex<T>> dfsHelper(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> vs,
                                                List<Vertex<T>> myList) {
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        vs.add(start);
        myList.add(start);
        List<VertexDistance<T>> adjVert = adjList.get(start);
        for (VertexDistance<T> vert: adjVert) {
            if (!vs.contains(vert.getVertex())) {
                dfsHelper(vert.getVertex(), graph, vs, myList);
            }
        }

        return myList;

    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("At least 1 of the given arguments is null."
                    + " Please enter existing data.");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("The given start vertex is not in the graph."
                    + " Please enter an existing vertex.");
        }

        Set<Vertex<T>> visitedSet = new HashSet<>(graph.getVertices().size());
        PriorityQueue<VertexDistance<T>> myQueue = new PriorityQueue<>(graph.getVertices().size());
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>(graph.getVertices().size());
        for (Vertex<T> vert: graph.getVertices()) {
            distanceMap.put(vert, Integer.MAX_VALUE);
        }
        myQueue.add(new VertexDistance<T>(start, 0));
        while (!myQueue.isEmpty() && (visitedSet.size() != graph.getVertices().size())) {
            VertexDistance<T> vertexDistance = myQueue.poll();
            if (!visitedSet.contains(vertexDistance.getVertex())) {
                visitedSet.add(vertexDistance.getVertex());
                distanceMap.replace(vertexDistance.getVertex(), vertexDistance.getDistance());
                for (VertexDistance<T> vertex: graph.getAdjList().get(vertexDistance.getVertex())) {
                    if (!visitedSet.contains(vertex.getVertex())) {
                        myQueue.add(new VertexDistance<T>(vertex.getVertex(), vertexDistance.getDistance()
                                + vertex.getDistance()));

                    }
                }
            }

        }

        return distanceMap;

    }
}
