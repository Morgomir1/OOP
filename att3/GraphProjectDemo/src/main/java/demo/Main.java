package demo;

import graph.SimpleGraph;

public class Main {

    public static void main(String[] args) {
        System.out.println("|----------------------------------------------------------|");
        SimpleGraph<Integer> graph = new SimpleGraph<>(false);
        graph.createRandomGraph(10, 0, 10);
        System.out.println(graph);
        System.out.println("|----------------------------------------------------------|");
        graph.clear();
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 5);
        graph.addEdge(1, 4, 5);
        graph.addEdge(1, 5, 5);
        graph.addEdge(1, 6, 5);
        System.out.println(graph.getNeighborsFor(1));
        System.out.println("|----------------------------------------------------------|");
        graph.clear();
        graph.createRandomGraph(10, 10, 15);
        graph.depthSearch(1);
        System.out.println("|----------------------------------------------------------|");
        graph.createRandomGraph(10, 10, 15);
        graph.breathSearch(1);
        System.out.println("|----------------------------------------------------------|");
        graph.clear();
        graph.createRandomGraph(10, 5, 5);

    }
}
