import java.util.LinkedList;
import java.util.List;

public class Dijkstra {

       
    public static void main(String[] args) {
        Node node0 = new Node("0");
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
 
        node0.addDestination(node1, 1);
        node0.addDestination(node2, 3);
        node0.addDestination(node3, 7);
 
        node1.addDestination(node0, 1);
        node1.addDestination(node2, 1);
 
        node2.addDestination(node0, 3);
        node2.addDestination(node1, 1);
        node2.addDestination(node3, 2);

        node3.addDestination(node0, 7);
        node3.addDestination(node2, 2);
 
        Graph graph = new Graph();
 
        graph.addNode(node0);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addNode(node3);
 
        
        for (Node node : graph.getNodes()) {

            graph = Graph.calculateShortestPathFromSource(graph, node);
            System.out.println(">>>>SOURCE NODE " + node.getName());
            for (Node n : graph.getNodes()) {
                List<Node> shortestPath = n.getShortestPath();
                System.out.println("Path for: " + n.getName()+",Path length: " + n.getDistance());
                for (Node pathNode : shortestPath) {
                    System.out.print("node"+pathNode.getName()+",");
                }
                System.out.println();
                System.out.println("------------------");
            }
        System.out.println("===================================");
        }

    }
}