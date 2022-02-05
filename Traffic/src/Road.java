
public class Road {
	
	//Fields
	public static final int length = 10;
	private Node topNode;
	private Node bottomNode;
	private int directionTop; //Either 0, 1, 2, 3
	private int directionBottom;
	
	public static final int N = 0;
	public static final int E = 1;
	public static final int S = 2;
	public static final int W = 3;
	
	public Road(Node topNode, Node bottomNode, int direction) {
		this.topNode = topNode;
		this.bottomNode = bottomNode;
		this.directionTop = direction;
		this.directionBottom = (direction + 2)%4;
	}
	
	public Node getTopNode() {
		return topNode;
	}

	public Node getBottomNode() {
		return bottomNode;
	}
	
	public Node getOtherNode(Node node) {
		if (node.equals(topNode)) return bottomNode;
		else if (node.equals(bottomNode)) return topNode;
		System.out.println("Error");
		return null;
	}
	
	public int getDirection() {
		return directionTop%2;
	}
}
