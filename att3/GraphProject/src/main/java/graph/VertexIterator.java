package graph;

import java.util.Collection;
import java.util.Iterator;

class VertexIterator<T> implements Iterator<Vertex<T>> {
    Iterator<SimpleEdge<T>> iterator;

    VertexIterator(Collection<SimpleEdge<T>> neighbors) {
        iterator = neighbors.iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Vertex<T> next() {
        if (iterator.next() instanceof OrientedEdge) {
            return iterator.next().getDirectionVertex();
        } else {
            return iterator.next().getSecondVertex();
        }

    }
}
