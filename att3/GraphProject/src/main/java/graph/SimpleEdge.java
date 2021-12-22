package graph;

public interface SimpleEdge<T> {

    Vertex<T> getDirectionVertex();

    Vertex<T> getSecondVertex();

    Vertex<T> getFirstVertex();

    int getWeight();

}
