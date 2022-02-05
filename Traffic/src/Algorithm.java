
public class Algorithm {
	
	private TrafficLight[][] lights; 	
	private double[][][] parameters;
	//0-3: UDLR Target
	//4-7: UDLR Factor
	
	public Algorithm(TrafficLight[][] lights) {
		this.lights = lights;
		parameters = new double[lights.length][lights[0].length][8];
		generateRandomParameters();
		giveParameters();
	}
	
	public Algorithm(TrafficLight[][] lights, double[][][] parameters) {
		this.lights = lights;
		this.parameters = parameters;
		giveParameters();
	}
	
	public void generateRandomParameters() {
		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[0].length; j++) {
				for (int k = 0; k < 4; k++) {
					parameters[i][j][k] = Math.random()*Parameters.LIGHT_DEFAULT_TIME-Parameters.LIGHT_DEFAULT_TIME/2;
				}
				for (int k = 4; k < 8; k++) {
					parameters[i][j][k] = Math.random();
				}
			}
		}
	}
	
	public void mutate(double mutationFactor) {
		for (int i = 0; i < parameters.length; i++) {
			for (int j = 0; j < parameters[0].length; j++) {
				for (int k = 0; k < 4; k++) {
					if (Math.random() < mutationFactor) {
						parameters[i][j][k] = Math.random()*Parameters.LIGHT_DEFAULT_TIME-Parameters.LIGHT_DEFAULT_TIME/2;
					}
				}
				for (int k = 4; k < 8; k++) {
					if (Math.random() < mutationFactor) {
						parameters[i][j][k] = Math.random();
					}
				}
			}
		}
	}
	
	public double[][][] getParameters() {
		return parameters;
	}
	
	public void giveParameters() {
		for (int i = 0; i < lights.length; i++) {
			for (int j = 0; j < lights[0].length; j++) {
				lights[i][j].setParameters(parameters[i][j]);
			}
		}
	}
	
}
