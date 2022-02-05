import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Car {
	
	//Fields
	private Road road;
	private boolean directionTop;
	private int ticksRemaining;
	private int routePosition;
	private City city;
	private ArrayList<Node> route = new ArrayList<Node>();
	private Node startNode, endNode;
	private boolean travelling;
	private int waitTime = 0;
	
	//Constructors
	public Car(City city, Node startNode, Node endNode) {
		this.city = city;
		this.startNode = startNode;
		this.endNode = endNode;
		route = city.getRoute(startNode, endNode);
		road = city.getConnection(route.get(0), route.get(1));
		directionTop = road.getTopNode().equals(route.get(1));
		ticksRemaining = Road.length;
		routePosition = 0;
		travelling = true;
	}
	
	//Methods
	private void printRoute() {
		for (Node node : route) {
			System.out.print(node.getIndex()+" ");
		}
		System.out.println();
	}
	
	public void tick() {
		if (ticksRemaining > 0) ticksRemaining--;
		else {
			if (route.get(routePosition+1)==endNode) {
				travelling = false;
			}
			else {
				if (route.get(routePosition+1).getLight()==null || route.get(routePosition+1).getLight().isGreen(road)) {
					routePosition++;
					ticksRemaining = Road.length;
					road = city.getConnection(route.get(routePosition), route.get(routePosition+1));
					directionTop = road.getTopNode().equals(route.get(routePosition+1));
				}
				
				else if (!route.get(routePosition+1).getLight().isGreen(road)) {
					waitTime++;
				}
			}
		}
	}
	
	public int getTrafficLights() {
		if (route.size() >= 2) {
			return route.size()-2;		
		}
		return 0;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public boolean isTravelling() {
		return travelling;
	}
	
	//Draw Function
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		int xPos, yPos;
		if (directionTop) {
			xPos = (int)(road.getBottomNode().getX()*ticksRemaining/(double)Road.length + 
					road.getTopNode().getX()*(Road.length-ticksRemaining)/(double)Road.length);
			yPos = (int)(road.getBottomNode().getY()*ticksRemaining/(double)Road.length + 
					road.getTopNode().getY()*(Road.length-ticksRemaining)/(double)Road.length);
		}
		else {
			xPos = (int)(road.getTopNode().getX()*ticksRemaining/(double)Road.length + 
					road.getBottomNode().getX()*(Road.length-ticksRemaining)/(double)Road.length);
			yPos = (int)(road.getTopNode().getY()*ticksRemaining/(double)Road.length + 
					road.getBottomNode().getY()*(Road.length-ticksRemaining)/(double)Road.length);
		}
		g.fillRect(xPos, yPos, Parameters.CAR_SIZE, Parameters.CAR_SIZE);
	}
}
