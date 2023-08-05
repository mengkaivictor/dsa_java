import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.management.Query;
import javax.management.QueryEval;

/**
 * Your implementation of various graph traversal algorithms.
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you should use is the adjacency
     * list from graph. DO NOT create new instances of Map for BFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the bfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<Vertex<T>> result = new ArrayList<>();

        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()){
            Vertex<T> cur = queue.poll();
            result.add(cur);
            
            List<VertexDistance<T>> neighbors = adjList.get(cur);
            for (VertexDistance<T> neighbor: neighbors) {
                Vertex<T> neighbor_vertex = neighbor.getVertex();
                if (!visited.contains(neighbor_vertex)){
                    visited.add(neighbor_vertex);
                    queue.add(neighbor_vertex);
                }
            }
        }

        return result;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: This method should be implemented recursively. You may need to
     * create a helper method.
     *
     * You may import/use java.util.Set, java.util.List, and any classes that
     * implement the aforementioned interfaces, as long as they are efficient.
     *
     * The only instance of java.util.Map that you may use is the adjacency list
     * from graph. DO NOT create new instances of Map for DFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the dfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<Vertex<T>> result = new ArrayList<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();

        List<Vertex<T>> res = dfs_helper(result, visited, start, adjList);
        return res;
    }

    private static <T> List<Vertex<T>> dfs_helper(List<Vertex<T>> result, Set<Vertex<T>> visited, Vertex<T> cur, Map<Vertex<T>, List<VertexDistance<T>>> adjList){
        visited.add(cur);
        result.add(cur);

        List<VertexDistance<T>> neighbors = adjList.get(cur);
        for (VertexDistance<T> neighbor: neighbors){
            Vertex<T> neighbor_vertex = neighbor.getVertex();
            if (!visited.contains(neighbor_vertex)){
                dfs_helper(result, visited, neighbor_vertex, adjList);
            }
        }
        
        return result;
    }
}