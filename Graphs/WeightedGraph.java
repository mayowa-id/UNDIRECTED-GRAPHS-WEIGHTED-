package com.Graphs;

import java.util.*;

public class WeightedGraph {
    private class  Node{
        private String label;
        public Node(String label) {
            this.label = label;
        }

        @Override
        public String toString() {

            return label;
        }
        private List <Edge> connectedEdges = new ArrayList<>();
        public void addEdge(Node to, int weight){
            connectedEdges.add(new Edge(this, to, weight));
        }
        public List<Edge> getConnectedEdges(){
            return connectedEdges;
        }
    }
    private class Edge {
        private Node from;
        private Node to;
        private int weight;

        public Edge(Node from, Node to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return from +"to"+ to;
        }
    }
    private Map<String , Node> nodes = new HashMap<>();


    public void addNode(String label){
        var node = new Node(label);
        nodes.putIfAbsent(label, node);
    }
    public void addEdge(String from, String to, int weight){
        var fromNode = nodes.get(from);
        if (fromNode == null)
            throw new IllegalArgumentException();

        var toNode = nodes.get(to);
        if (toNode == null)
            throw  new IllegalArgumentException();

        fromNode.addEdge(toNode, weight);
        toNode.addEdge(fromNode, weight);
    }
    public void print(){
        for (var node: nodes.values()){
            var edge = node.getConnectedEdges();
            if(!edge.isEmpty())
                System.out.println(node + " is connected to "+ edge );
        }
    }
    private class NodeEntry{
        private Node node;
        private int priority;

        public NodeEntry(Node node, int priority) {
            this.node = node;
            this.priority = priority;
        }
    }
public int getShortestDistance(String from,String to, int weight ){
        Map<Node, Integer> distances = new HashMap<>();
        for(var node : nodes.values())
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(nodes.get(from), 0);

        Set<Node> visited = new HashSet<>();

    PriorityQueue<NodeEntry> myQueue = new PriorityQueue<NodeEntry>(
    Comparator.comparingInt(ne -> ne.priority));
myQueue.add(new NodeEntry(nodes.get(from), 0));
while(!myQueue.isEmpty()){
    var current = myQueue.remove().node;
    visited.add(current);

    for(var edge : current.getConnectedEdges()){
        if(visited.contains(edge.to))
            continue;

        var newDistance = distances.get(current) + edge.weight;
        if(newDistance < distances.get(edge.to)){
            distances.replace(edge.to, newDistance);
myQueue.add(new NodeEntry(edge.to, newDistance));
        }
    }
}
return distances.get(nodes.get(to));
}
    //DIJKSTRA'S ALGORITHM
    public Path getShortestPath(String from,String to, int weight ){
      var toNode = nodes.get(to);
        Map<Node, Integer> distances = new HashMap<>();
        for(var node : nodes.values())
            distances.put(node, Integer.MAX_VALUE);
        distances.replace(nodes.get(from), 0);

        Map<Node, Node> previousNodes = new HashMap<>();

        Set<Node> visited = new HashSet<>();

        PriorityQueue<NodeEntry> myQueue = new PriorityQueue<NodeEntry>(
                Comparator.comparingInt(ne -> ne.priority));
        myQueue.add(new NodeEntry(nodes.get(from), 0));
        while(!myQueue.isEmpty()){
            var current = myQueue.remove().node;
            visited.add(current);

            for(var edge : current.getConnectedEdges()){
                if(visited.contains(edge.to))
                    continue;

                var newDistance = distances.get(current) + edge.weight;
                if(newDistance < distances.get(edge.to)){
                    distances.replace(edge.to, newDistance);
                    previousNodes.put(edge.to, current);
                    myQueue.add(new NodeEntry(edge.to, newDistance));
                }
            }
        }
        Stack <Node> myStack = new Stack<>();
        myStack.push(toNode);
        var previous = previousNodes.get(toNode);
        while (previous !=null){
            myStack.push(previous);
            previous = previousNodes.get(previous);
        }
var path = new Path();
        while (!myStack.isEmpty()) {
            path.addNode(myStack.pop().label);
        }

        return path;
    }
    public boolean hasCycle(){
        Set<Node> visitedNodes = new HashSet<>();

        for(var node: nodes.values()) {
            if (!visitedNodes.contains(node)
                    && hasCycle(node, null, visitedNodes))
                    return true;
        }
        return false;
    }

        private boolean hasCycle(Node node, Node parentNode, Set<Node> visitedNodes){
            visitedNodes.add(node);

            for (var edge : node.getConnectedEdges()){
            if (edge.to == parentNode)
            continue;

            if(visitedNodes.contains(edge.to))
            return true;

            if (hasCycle(edge.to, node, visitedNodes))
                return true;
            }
            return false;
        }

        //PRIM'S ALGORITHM
        public WeightedGraph getMinimumSpanningTree(){
        var tree = new WeightedGraph();

        PriorityQueue <Edge> edges = new PriorityQueue<>(
                Comparator.comparingInt(e -> e.weight)
        );

        var startNode = nodes.values().iterator().next();
        edges.addAll(startNode.getConnectedEdges());
        tree.addNode(startNode.label);
        
        if (edges.isEmpty()) //In case there are no edges
            return tree;

        while(tree.nodes.size() < nodes.size()){
            var minimumEdge = edges.remove();
            var nextNode = minimumEdge.to;

            if (tree.containsNode(nextNode.label))
                continue;

            tree.addNode(nextNode.label);
            tree.addEdge(minimumEdge.from.label,
                    nextNode.label, minimumEdge.weight);

            for (var edge : nextNode.getConnectedEdges())
                if (!tree.containsNode(edge.to.label))
                    edges.add(edge);
        }
        return tree;
        }
public boolean containsNode (String label){
        return nodes.containsKey(label);
}
    }

