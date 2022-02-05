import java.awt.Graphics;
import java.util.ArrayList;

public class Layout {
	
	//Fields
	ArrayList<Node> nodes = new ArrayList<Node>();

	//Constructors
	public Layout(int[][] nodePoints, int[][] connections) {
		createNodes(nodePoints);
		createConnections(connections);
	}
	
	//Methods
	public void createNodes(int[][] nodePoints) {
		for (int i = 0; i < nodePoints.length; i++) {
			nodes.add(new Node(nodePoints[i][0], nodePoints[i][1], i, this));
		}
	}
	
	public void createConnections(int[][] connections) {
		for (int i = 0; i < connections.length; i++) {
			Road road = new Road(nodes.get(connections[i][0]), nodes.get(connections[i][1]), connections[i][2]);
			nodes.get(connections[i][0]).addConnection(road);
			nodes.get(connections[i][1]).addConnection(road);
		}
	}
	
	public void printNodeInfo() {
		for (Node node : nodes) {
			System.out.println("Node " + node.getIndex() + ": " + node.getConnections().size());
		}
	}
	
	//Getters and Setters
	public Node getNode(int num) {
		return nodes.get(num);
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	//Draw Function
	public void draw(Graphics g) {
		for (Node node : nodes) {
			node.draw(g);
		}
	}
}
