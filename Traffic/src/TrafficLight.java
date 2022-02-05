import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class TrafficLight {
	
	private Node node;
	private double totalDuration;
	private int currentState; //0 for NS, 1 for WE, 2 for NONE
	private double timeLeft;
	private Random rand;
	private double[] parameters;
	
	public TrafficLight(Node node, double totalDuration) {
		this.node = node;
		this.totalDuration = totalDuration;
		currentState = 0;
		timeLeft = totalDuration/2;
		rand = new Random();
	}
	
	public void tick() {
		timeLeft--;
		if (timeLeft <= 0) {
			if (rand.nextInt(Parameters.CROSS_PROBABILITY) == 0) {
				currentState = 2;
				timeLeft = Parameters.CROSS_TIME;
			}
			else {
				currentState = (currentState+1)%2;
				double currentDuration = totalDuration;
				Node currentNode = node.getUp();
				if (currentNode.hasLight()) {
					TrafficLight currentLight = currentNode.getLight();
					double l1 = getPos(), l2 = currentLight.getPos();
					double x = l1-l2;
					if (x < -totalDuration/2) x += totalDuration;
					if (x > totalDuration/2) x -= totalDuration;
					double y = parameters[0]-x;
					if (y < -totalDuration/2) y += totalDuration;
					if (y > totalDuration/2) y -= totalDuration;
					currentDuration -= parameters[4]*y*0.25;
				}
				currentNode = node.getDown();
				if (currentNode.hasLight()) {
					TrafficLight currentLight = currentNode.getLight();
					double l1 = getPos(), l2 = currentLight.getPos();
					double x = l1-l2;
					if (x < -totalDuration/2) x += totalDuration;
					if (x > totalDuration/2) x -= totalDuration;
					double y = parameters[1]-x;
					if (y < -totalDuration/2) y += totalDuration;
					if (y > totalDuration/2) y -= totalDuration;
					currentDuration -= parameters[5]*y*0.25;
				}
				currentNode = node.getLeft();
				if (currentNode.hasLight()) {
					TrafficLight currentLight = currentNode.getLight();
					double l1 = getPos(), l2 = currentLight.getPos();
					double x = l1-l2;
					if (x < -totalDuration/2) x += totalDuration;
					if (x > totalDuration/2) x -= totalDuration;
					double y = parameters[2]-x;
					if (y < -totalDuration/2) y += totalDuration;
					if (y > totalDuration/2) y -= totalDuration;
					currentDuration -= parameters[6]*y*0.25;
				}
				currentNode = node.getUp();
				if (currentNode.hasLight()) {
					TrafficLight currentLight = currentNode.getLight();
					double l1 = getPos(), l2 = currentLight.getPos();
					double x = l1-l2;
					if (x < -totalDuration/2) x += totalDuration;
					if (x > totalDuration/2) x -= totalDuration;
					double y = parameters[3]-x;
					if (y < -totalDuration/2) y += totalDuration;
					if (y > totalDuration/2) y -= totalDuration;
					currentDuration -= parameters[7]*y*0.25;
				}
				timeLeft = currentDuration/2;
			}
		}
	}
	
	public double getPos() {
		if (timeLeft > totalDuration/2) return currentState*totalDuration/2;
		return currentState*totalDuration/2 + totalDuration/2-timeLeft;
	}
	
	public void setParameters(double[] parameters) {
		this.parameters = parameters;
	}
	
	public boolean isGreen(Road road) {
		return currentState == road.getDirection();
	}
	
	public void draw(Graphics g) {
		int size = Parameters.lightSize;
		if (currentState == 0) {
			g.setColor(Color.GREEN);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()-10*size/20, 14*size/20, 3*size/20);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()+17*size/20-10*size/20, 14*size/20, 3*size/20);
			g.setColor(Color.RED);
			g.fillRect(node.getX()-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
			g.fillRect(node.getX()+17*size/20-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
		}
		else if (currentState == 1) {
			g.setColor(Color.RED);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()-10*size/20, 14*size/20, 3*size/20);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()+17*size/20-10*size/20, 14*size/20, 3*size/20);
			g.setColor(Color.GREEN);
			g.fillRect(node.getX()-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
			g.fillRect(node.getX()+17*size/20-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
		}
		else if(currentState == 2) {
			g.setColor(Color.RED);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()-10*size/20, 14*size/20, 3*size/20);
			g.fillRect(node.getX()+3*size/20-10*size/20, node.getY()+17*size/20-10*size/20, 14*size/20, 3*size/20);
			g.setColor(Color.RED);
			g.fillRect(node.getX()-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
			g.fillRect(node.getX()+17*size/20-10*size/20, node.getY()+3*size/20-10*size/20, 3*size/20, 14*size/20);
		}
	}
	
}
