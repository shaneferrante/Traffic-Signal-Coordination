import javax.swing.JFrame;
import javax.swing.plaf.metal.MetalToggleButtonUI;

public class TrafficRunner {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Window window = new Window(Parameters.WIDTH, Parameters.HEIGHT);
		frame.setTitle("Traffic Coordination");
		frame.add(window);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(Parameters.WIDTH + 23, Parameters.HEIGHT + 57);
		frame.setVisible(Parameters.MODE.equals("Display"));
		if (Parameters.MODE.equals("Display")) {
			Layout layout = createLayout(Parameters.ROWS, Parameters.COLUMNS);
			int lights = Parameters.ROWS*Parameters.COLUMNS;
			City city = new City(layout, lights);
			city.addWindow(window);
			window.setCity(city);
			city.start();
		}
		runTrial(window);
	}
	
	public static double average(double[] arr) {
		double sum = 0;
		for (double num : arr) {
			sum += num;
		}
		return sum/arr.length;
	}
	
	public static double stDev(double[] arr) {
		double average = average(arr);
		double sum = 0;
		for (double num : arr) {
			sum += Math.pow(num-average, 2);
		}
		return Math.sqrt(sum/arr.length);
	}
	
	public static void runTrial(Window window) {
		Layout layout = createLayout(Parameters.ROWS, Parameters.COLUMNS);
		int lights = Parameters.ROWS*Parameters.COLUMNS;
//		City city = new City(layout, lights);
//		window.setCity(city);
//		city.start();
//		//city.printInfo();
//		return city;
		GeneticAlgorithm alg = new GeneticAlgorithm(100, layout, lights, window);
	}
	
	public static Layout createLayout(int rows, int columns) {
		int[][] nodePoints = createPoints(rows, columns);
		int[][] connections = createConnections(rows, columns);
		return new Layout(nodePoints, connections);
	}
	
	
	public static int[][] createPoints(int rows, int columns) {
		int numCities = rows*columns + 2*(rows+columns);
		int[][] nodePoints = new int[numCities][2];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				nodePoints[columns*i+j][0] = Parameters.WIDTH*(j+1)/(columns+1);
				nodePoints[columns*i+j][1] = Parameters.HEIGHT*(i+1)/(rows+1);
			}
		}
		for (int i = 0; i < columns; i++) {
			nodePoints[rows*columns+i][0] = Parameters.WIDTH*(i+1)/(columns+1);
			nodePoints[rows*columns+i][1] = Parameters.HEIGHT/(rows+1)/2;
		}
		for (int i = 0; i < columns; i++) {
			nodePoints[(rows+1)*columns+i][0] = Parameters.WIDTH*(i+1)/(columns+1);
			nodePoints[(rows+1)*columns+i][1] = Parameters.HEIGHT*rows/(rows+1) + Parameters.HEIGHT/(rows+1)/2;
		}
		for (int i = 0; i < rows; i++) {
			nodePoints[(rows+2)*columns+i][0] = Parameters.WIDTH/(columns+1)/2;
			nodePoints[(rows+2)*columns+i][1] = Parameters.HEIGHT*(i+1)/(rows+1);
		}
		for (int i = 0; i < rows; i++) {
			nodePoints[(rows+2)*columns+rows+i][0] = Parameters.WIDTH/(columns+1)/2 + Parameters.WIDTH*columns/(columns+1);
			nodePoints[(rows+2)*columns+rows+i][1] = Parameters.HEIGHT*(i+1)/(rows+1);
		}
		return nodePoints;
	}
	
	public static int[][] createConnections(int rows, int columns) {
		int numRoads = (rows+1)*columns+(columns+1)*rows;
		int[][] connections = new int[numRoads][3];
		for(int i = 0; i < rows; i++) {
			for (int j = 0; j < columns-1; j++) {
				connections[i*(columns-1)+j][0] = i*columns+j;
				connections[i*(columns-1)+j][1] = i*columns+j+1;
				connections[i*(columns-1)+j][2] = Road.E;
			}
		}
		for(int i = 0; i < columns; i++) {
			for (int j = 0; j < rows-1; j++) {
				int completed = rows*(columns-1);
				connections[completed + i*(rows-1) + j][0] = j*columns+i;
				connections[completed + i*(rows-1) + j][1] = (j+1)*columns+i;
				connections[completed + i*(rows-1) + j][2] = Road.S;
			}
		}
		for (int i = 0; i < columns; i++) {
			int completed = rows*(columns-1) + columns*(rows-1);
			connections[completed + i][0] = rows*columns+i;
			connections[completed + i][1] = i;
			connections[completed + i][2] = Road.S;
		}
		for (int i = 0; i < columns; i++) {
			int completed = rows*(columns-1) + columns*(rows);
			connections[completed + i][0] = columns*(rows-1)+i;
			connections[completed + i][1] = columns*(rows+1)+i;
			connections[completed + i][2] = Road.S;
		}
		for (int i = 0; i < rows; i++) {
			int completed = rows*(columns-1) + columns*(rows+1);
			connections[completed + i][0] = columns*(rows+2)+i;
			connections[completed + i][1] = i*columns;
			connections[completed + i][2] = Road.E;
		}
		for (int i = 0; i < rows; i++) {
			int completed = rows*(columns) + columns*(rows+1);
			connections[completed + i][0] = (i+1)*columns-1;
			connections[completed + i][1] = (rows+2)*columns+rows+i;
			connections[completed + i][2] = Road.E;
		}
		return connections;
	}
	
}
