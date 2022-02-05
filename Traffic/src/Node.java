import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Node {
	
	//Fields
	private int x, y, index;
	private ArrayList<Road> connections = new ArrayList<Road>();
	private Layout layout;
	private TrafficLight light;
	
	//Constructors
	public Node(int x, int y, int index, Layout layout) {
		this.x = x;
		this.y = y;
		this.index = index;
		this.layout = layout;
	}
	
	//Methods
	public void addConnection(int num, int direction) {
		connections.add(new Road(this, layout.getNode(num), direction));
	}
	
	public void addConnection(Road road) {
		connections.add(road);
	}
	
	public void addLight() {
		light = new TrafficLight(this, Parameters.LIGHT_DEFAULT_TIME);
	}
	
	public void tick() {
		if (light != null) {
			light.tick();
		}
	}
	
	//Getters and Setters
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Node getUp() {
		for (Road road : connections) {
			if (road.getBottomNode().equals(this) && road.getDirection() == Road.N) return road.getTopNode();
		}
		return null;
	}
	
	public Node getDown() {
		for (Road road : connections) {
			if (road.getTopNode().equals(this) && road.getDirection() == Road.N) return road.getBottomNode();
		}
		return null;
	}
	
	public Node getRight() {
		for (Road road : connections) {
			if (road.getBottomNode().equals(this) && road.getDirection() == Road.E) return road.getTopNode();
		}
		return null;
	}
	
	public Node getLeft() {
		for (Road road : connections) {
			if (road.getTopNode().equals(this) && road.getDirection() == Road.E) return road.getBottomNode();
		}
		return null;
	}
	
	public ArrayList<Road> getConnections() {
		return connections;
	}
	
	public TrafficLight getLight() {
		return light;
	}
	
	public boolean hasLight() {
		return !(light == null);
	}
	
	//Draw Function
	public void draw(Graphics g) {
		int size = Parameters.lightSize;
		g.setColor(Color.BLACK);
		g.fillRect(x-10*size/20, y-10*size/20, 20*size/20, 20*size/20);
		g.drawString("" + index, x-20*size/20, y-10*size/20);
		for (Road road : connections) {
			if (road.getTopNode().equals(this)) {
				g.drawLine(road.getTopNode().getX(), road.getTopNode().getY(), road.getBottomNode().getX(), road.getBottomNode().getY());
			}
		}
		if (light != null) {
			light.draw(g);
		}
	}
}
