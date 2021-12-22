package graph;

import java.util.*;

public class SimpleGraph<T>  {
    private HashMap<T, Vertex<T>> graph;
    private final boolean isOriented;

    public SimpleGraph(boolean isOriented) {
        this.isOriented = isOriented;
        this.graph = new HashMap<>();
    }

    public void clear() {
        this.graph = new HashMap<>();
    }

    public boolean contains(T vertex) {
        return graph.containsKey(vertex);
    }

    public Vertex<T> get(T vertex) {
        return graph.get(vertex);
    }

    public boolean areAdjacent(T src, T dest) throws NullPointerException {
        Vertex<T> srcVertex = graph.get(src);
        Vertex<T> destVertex = graph.get(dest);

        if (srcVertex == null || destVertex == null) {
            throw new NullPointerException();
        }

        return srcVertex.hasNeighbor(destVertex);
    }

    public void addVertex(T vertex) {
        Vertex<T> vertexNode = new Vertex<>(vertex);
        graph.put(vertex, vertexNode);
    }

    public void removeVertex(T vertex) throws NullPointerException {
        Vertex<T> vertexNode = graph.get(vertex);

        if (vertexNode == null)
            throw new NullPointerException();

        for (Vertex<T> possibleLink : graph.values()) {
            possibleLink.removeEdgeTo(vertexNode);
        }

        graph.remove(vertex);
    }

    public void addEdge(T from, T to, int weight) throws NullPointerException {

        if (from == null || to == null ) {
            throw new NullPointerException();
        }

        Vertex<T> fromVertex = graph.get(from);
        Vertex<T> toVertex = graph.get(to);

        if (this.isOriented) {
            OrientedEdge<T> orientedEdge = new OrientedEdge<>(fromVertex, toVertex, weight);
            fromVertex.addEdge(orientedEdge);
        } else {
            NonOrientedEdge<T> edge = new NonOrientedEdge<>(fromVertex, toVertex, weight);
            fromVertex.addEdge(edge);
        }
    }

    public void removeEdge(T from, T to) throws NullPointerException {
        Vertex<T> fromVertex = graph.get(from);
        Vertex<T> toVertex = graph.get(to);

        if (fromVertex == null || toVertex == null) {
            throw new NullPointerException();
        }

        if (fromVertex.hasNeighbor(toVertex)) {
            fromVertex.removeEdgeTo(toVertex);
        }
    }

    public List<T> getNeighborsFor(T vertex) throws NullPointerException {
        if (graph.get(vertex) == null) {
            throw new NullPointerException();
        }
        return graph.get(vertex).getNeighbors();
    }

    public void depthSearch(T start) throws NullPointerException {
        if (graph.get(start) == null) {
            throw new NullPointerException();
        }

        Collection<T> visited = new HashSet<>();
        visited.add(start);

        Stack<T> stack = new Stack<>();
        stack.push(start);

        System.out.print(start + " - ");
        while (!stack.empty()) {
            T current = stack.peek();
            T neighbor = null;

            for (T t : getNeighborsFor(current)) {
                neighbor = t;
                if (!visited.contains(neighbor)) {
                    break;
                }
            }

            if (neighbor != null && !visited.contains(neighbor)) {
                visited.add(neighbor);
                System.out.print(neighbor + " - ");
                stack.push(neighbor);
            } else {
                stack.pop();
            }
        }
        System.out.println();
    }

    public void breathSearch(T start) throws NullPointerException {
        if (graph.get(start) == null) {
            throw new NullPointerException();
        }

        Collection<T> visited = new HashSet<>();
        visited.add(start);

        Queue<T> queue = new ArrayDeque<>();
        queue.add(start);

        System.out.print(start + " - ");
        while (!queue.isEmpty()) {
            T current = queue.remove();
            T neighbor = null;

            for (T t : getNeighborsFor(current)) {
                neighbor = t;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    System.out.print(neighbor + " - ");
                    queue.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void createRandomGraph (int vertexNumber, int randomWeightSizeMin, int randomWeightSizeMax) {
        Random random = new Random();
        for (int i = 0; i < vertexNumber; i++) {
            addVertex((T) Integer.valueOf(i));
        }
        for (int i = 0; i < vertexNumber; i++) {
            for (int j = 0; j < vertexNumber; j++) {
                if (random.nextInt(10) <= 3) {
                    if (randomWeightSizeMin >= randomWeightSizeMax ) {
                        addEdge((T) Integer.valueOf(i), (T) Integer.valueOf(j), 0);
                    } else {
                        addEdge((T) Integer.valueOf(i), (T) Integer.valueOf(j), random.nextInt(randomWeightSizeMin, randomWeightSizeMax));
                    }
                }
            }
        }
    }

    @Override
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (HashMap.Entry<T, Vertex<T>> entry : graph.entrySet()) {
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append(entry.getValue().getNeighborsWithWeight().toString());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
