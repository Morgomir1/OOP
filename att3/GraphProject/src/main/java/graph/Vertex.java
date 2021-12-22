package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Vertex<T> implements Iterable<Vertex<T>> {
    T info;
    ArrayList<SimpleEdge<T>> neighbors;

    Vertex(T info) {
        this.info = info;
        neighbors = new ArrayList<>();
    }

    private SimpleEdge<T> getEdgeTo(Vertex<T> target) {

        for (SimpleEdge<T> current : neighbors) {
            if (current.getDirectionVertex().equals(target)) {
                return current;
            }
        }

        return null;
    }

    @Override
    public Iterator<Vertex<T>> iterator() {
        return new VertexIterator<T>(neighbors);
    }

    public void addEdge(SimpleEdge<T> edge) {
        if (!neighbors.contains(edge)) {
            neighbors.add(edge);
        }
    }

    public boolean hasNeighbor(Vertex<T> neighbor) {

        for (Vertex<T> tVertex : this) {
            if (tVertex.equals(neighbor)) {
                return true;
            }
        }

        return false;
    }

    public void removeEdgeTo(Vertex<T> neighbor) {
        SimpleEdge<T> edge = getEdgeTo(neighbor);
        neighbors.remove(edge);
    }

    public List<T> getNeighbors() {
        List<T> neighbors = new ArrayList<>();

        for (int i = 0; i < this.neighbors.size(); i++) {
            if (this.neighbors.get(i) instanceof NonOrientedEdge) {
                neighbors.add(this.neighbors.get(i).getSecondVertex().info);
            } else {
                neighbors.add(this.neighbors.get(i).getDirectionVertex().info);
            }
        }

        return neighbors;
    }

    public HashMap<T, Integer> getNeighborsWithWeight() {
        HashMap<T, Integer> neighbors = new HashMap<>();

        for (int i = 0; i < this.neighbors.size(); i++) {
            if (this.neighbors.get(i) instanceof NonOrientedEdge) {
                neighbors.put(this.neighbors.get(i).getSecondVertex().info, this.neighbors.get(i).getWeight());
            } else {
                neighbors.put(this.neighbors.get(i).getDirectionVertex().info, this.neighbors.get(i).getWeight());
            }
        }

        return neighbors;
    }

    public T value() {
        return info;
    }
}
