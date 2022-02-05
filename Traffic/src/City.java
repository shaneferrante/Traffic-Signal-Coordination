import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class City {
	
	//Fields
	private Layout layout;
	private ArrayList<Node> nodes;
	private Window window;
	private ArrayList<Car> cars = new ArrayList<Car>();
	private int totalTicks;
	private int totalWaitTime;
	private int totalTrafficLights;
	private Algorithm alg;
	private double[][][] parameters;
	Random rand = new Random();
	
	//Constructors
	public City(Layout layout, int lights) {
		this.layout = layout;
		this.nodes = layout.getNodes();
		createLights(lights);
		TrafficLight[][] lightsArr = createLightsArr();
		alg = new Algorithm(lightsArr);
		totalTicks = 0;
		//start();
	}
	
	public City(Layout layout, int lights, double[][][] parameters) {
		this.layout = layout;
		this.nodes = layout.getNodes();
		createLights(lights);
		TrafficLight[][] lightsArr = createLightsArr();
		alg = new Algorithm(lightsArr, parameters);
		totalTicks = 0;
		//start();
	}
	
	//Methods
	public void addWindow(Window window) {
		this.window = window;
	}
	
	public void mutate(double mutationFactor) {
		alg.mutate(mutationFactor);
	}
	
	public TrafficLight[][] createLightsArr() {
		TrafficLight[][] lightsArr = new TrafficLight[Parameters.ROWS][Parameters.COLUMNS];
		for (int i = 0; i < Parameters.ROWS; i++) {
			for (int j = 0; j < Parameters.COLUMNS; j++) {
				lightsArr[i][j] = nodes.get(i*Parameters.COLUMNS+j).getLight();
			}
		}
		return lightsArr;
	}
	
	public void createLights(int lights) {
		for (int i = 0; i < lights; i++) {
			nodes.get(i).addLight();
		}
	}
	
	public Road getConnection(Node node1, Node node2) {
		for (Road road : node1.getConnections()) {
			if ((road.getTopNode().equals(node1) && road.getBottomNode().equals(node2)) || 
				(road.getTopNode().equals(node2) && road.getBottomNode().equals(node1))) {
				return road;
			}
		}
		System.out.println("Error");
		return null;
	}
	
	public ArrayList<Node> getRoute(Node startNode, Node endNode) {
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> start = new ArrayList<Node>();
		start.add(startNode);
		int length = 0;
		paths.add(start);
		while(length < Parameters.MAX_LENGTH) {
			ArrayList<ArrayList<Node>> newPaths = new ArrayList<ArrayList<Node>>();
			for (ArrayList<Node> path : paths) {
				Node lastNode = path.get(path.size()-1);
				for (Road road : lastNode.getConnections()) {
					ArrayList<Node> newPath = new ArrayList<Node>();
					for (Node node : path) {
						newPath.add(node);
					}
					if (road.getTopNode().equals(lastNode)) {
						newPath.add(road.getBottomNode());
					}
					if (road.getBottomNode().equals(lastNode)) {
						newPath.add(road.getTopNode());
					}
					if (newPath.get(newPath.size()-1).equals(endNode)) {
						return newPath;
					}
					if (!path.contains(newPath.get(newPath.size()-1))) {
						newPaths.add(newPath);
					}
				}
			}
			paths = newPaths;
			length++;
		}
		System.out.println("Error");
		return null;
	}
	
	public void start() {
		while(totalTicks < Parameters.TICKSPERTRIAL) {
			tick();
			if (Parameters.MODE.equals("Display")) {
				window.repaint();
			}
			try {
				if (Parameters.SLEEP_TIME > 0) Thread.sleep(Parameters.SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double getFitness() {
		return totalWaitTime/(double)totalTrafficLights;
	}
	
	public void tick() {
		totalTicks++;
		for (Node node : nodes) {
			node.tick();
		}
		for (int i = 0; i < cars.size(); i++) {
			cars.get(i).tick();
			if (!cars.get(i).isTravelling()) {
				totalTrafficLights += cars.get(i).getTrafficLights();
				totalWaitTime += cars.get(i).getWaitTime();
				cars.remove(i);
				i--;
			}
			
		}
		if (totalTicks % Parameters.TIME_PER_CAR == 0) {
			if (cars.size() < Parameters.MAX_CARS) createCar();
		}
	}
	
	public void printInfo() {
		System.out.println("Total Ticks " + totalTicks);
		System.out.println("Total Wait Time: " + totalWaitTime);
		System.out.println("Total Traffic Lights: " + totalTrafficLights);
		System.out.println("Avg Wait Time/Light: " + totalWaitTime/(double)totalTrafficLights);
		System.out.println();
	}
	
	public void createCar() {
 		int node1 = rand.nextInt(nodes.size());
		int node2 = rand.nextInt(nodes.size());
		if (node1 == node2) {
			createCar();
		}
		else {
			Car newCar = new Car(this, nodes.get(node1), nodes.get(node2));
			cars.add(newCar);
		}
	}
	
	public double [][][] getParameters() {
		return alg.getParameters();
	}
	
	//Draw Function
	public void draw(Graphics g) {
		layout.draw(g);
		for (Car car : cars) {
			car.draw(g);
		}
	}
}
