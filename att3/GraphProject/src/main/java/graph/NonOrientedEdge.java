package graph;

public class NonOrientedEdge<T> implements SimpleEdge {
    Vertex<T> fisrt;
    Vertex<T> second;
    int weight;

    NonOrientedEdge(Vertex<T> fisrt, Vertex<T> second, int weight) {
        this.fisrt = fisrt;
        this.second = second;
        this.weight = weight;
    }

    @Override
    public Vertex<T> getSecondVertex() {
        return second;
    }

    @Override
    public Vertex<T> getFirstVertex() {
        return fisrt;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Vertex<T> getDirectionVertex() {
        return null;
    }
}
