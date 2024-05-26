package com.Graphs;

public class Main {
    public static void main (String [] args){
//Testing our implementation
        var graph = new WeightedGraph();
        graph.addNode("BedFrame");
        graph.addNode("Mattress");
        graph.addNode("Pillows");
        graph.addNode("PillowCase");
        graph.addEdge("BedFrame","Mattress",3);
        graph.addEdge("Mattress", "PillowCase", 4);
        graph.addEdge("Pillows","PillowCase", 5);
        graph.addEdge("BedFrame", "Pillows", 1);
        graph.addEdge("Mattress", "Pillows", 2);

        var myTree = graph.getMinimumSpanningTree();
        myTree.print();
    }
}
