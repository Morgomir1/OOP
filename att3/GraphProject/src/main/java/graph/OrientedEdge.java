package graph;

public class OrientedEdge<T> implements SimpleEdge {
    Vertex<T> from;
    Vertex<T> to;
    int weight;

    OrientedEdge(Vertex<T> from, Vertex<T> to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public Vertex<T> getSecondVertex() {
        return null;
    }

    @Override
    public Vertex<T> getFirstVertex() {
        return null;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Vertex<T> getDirectionVertex() {
        return to;
    }
}
