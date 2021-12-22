package graphTest;

import graph.SimpleGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GraphTest {

    @Test
    void containsTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.addVertex("1");
        Assertions.assertTrue(graph.contains("1"));
    }

    @Test
    void adjacentTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addEdge("1" ,"2", 5);
        graph.addEdge("2" ,"1", 9);
        Assertions.assertTrue(graph.areAdjacent("1", "2"));
    }

    @Test
    void adjacentExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addEdge("1" ,"2", 5);
        graph.addEdge("2" ,"1", 9);
        graph.removeVertex("2");
        try {
            graph.areAdjacent("1", "2");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }
    @Test
    void addEdgeExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.addVertex("1");
        try {
            graph.addEdge("1" ,"2", 5);
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void removeVertexExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        try {
            graph.removeVertex("1");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void removeEdgeExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        try {
            graph.removeEdge("1", "2");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void neighborsExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        try {
            graph.getNeighborsFor("1");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void breathSearchExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        try {
            graph.breathSearch("1");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void depthSearchExceptionTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        try {
            graph.depthSearch("1");
        } catch (Exception e) {
            Assertions.assertSame(e.getClass(), NullPointerException.class);
        }
    }

    @Test
    void toStringTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");
        graph.addVertex("6");
        graph.addVertex("7");
        graph.addVertex("8");
        Assertions.assertSame(graph.toString(), "1[], 2[], 3[], 4[], 5[], 6[], 7[], 8[], ");
    }

    @Test
    void randomGraphTest() {
        SimpleGraph<String> graph = new SimpleGraph<>(true);
        graph.createRandomGraph(20, 0, 0);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
        graph = new SimpleGraph<>(true);
        graph.createRandomGraph(15, 5, 10);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
        graph = new SimpleGraph<>(true);
        graph.createRandomGraph(10, 15, 20);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
        graph = new SimpleGraph<>(true);
        graph.createRandomGraph(5, 25, 30);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
        graph = new SimpleGraph<>(true);
        graph.createRandomGraph(3, 35, 40);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
        graph = new SimpleGraph<>(true);
        graph.createRandomGraph(1, 45, 50);
        System.out.println(graph);
        System.out.println("|-----------------------------------------|");
    }

    @Test
    void hasNextTest() {
        SimpleGraph<Integer> graph = new SimpleGraph<>(true);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2, 0);
        Assertions.assertTrue(graph.get(1).iterator().hasNext());
    }

    @Test
    void randomGraphTest2() {
        SimpleGraph<Integer> graph = new SimpleGraph<>(false);
        graph.createRandomGraph(10, 5, 10);
        System.out.println(graph);
    }

}
