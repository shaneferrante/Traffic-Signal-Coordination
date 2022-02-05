import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
	
	private City[] population;
	private int popSize;
	private Layout layout;
	private int lights;
	private int generationCount;
	private double mutationFactor = 0.05;
	private Random random;
	private ArrayList<Double> avgFits;
	private Window window;
	
	public GeneticAlgorithm(int popSize, Layout layout, int lights, Window window) {
		this.window = window;
		this.popSize = popSize;
		this.layout = layout;
		this.lights = lights;
		this.random = new Random();
		generationCount = 0;
		avgFits = new ArrayList<Double>();
		createPopulation();
		int count = 5;
		while (count > 0) {
			doGeneration();
			avgFits.add(getAverageFit());
			if (avgFits.size() > 15 && avgFits.get(avgFits.size()-1) > avgFits.get(avgFits.size()-15)) count--;
			if (count == 0) {
				doGeneration();
				for (int i = 0; i < popSize; i++) {
					System.out.print(population[i].getFitness() + " ");
				}
				System.out.println();
				doGeneration();
				for (int i = 0; i < popSize; i++) {
					System.out.print(population[i].getFitness() + " ");
				}
				System.out.println();
				doGeneration();
				for (int i = 0; i < popSize; i++) {
					System.out.print(population[i].getFitness() + " ");
				}
				System.out.println();
			}
		}
	}
	
	public void doGeneration() {
		generationCount++;
		City[] fittest = sortPopulation();
		City[] newPopulation = generateNewPopulation(fittest);
		mutatePopulation(newPopulation);
		population = newPopulation;
		printInfo();
	}
	
	public void mutatePopulation(City[] newPopulation) {
		for (int i = 0; i < newPopulation.length; i++) {
			newPopulation[i].mutate(mutationFactor);
		}
	}
	
	public City cross(City parent1, City parent2) {
		double[][][] params1 = copyArr(parent1.getParameters());
		double[][][] params2 = copyArr(parent2.getParameters());
		double[][][] newParams = new double[params1.length][params1[0].length][params1[0][0].length];
		for (int i = 0; i < params1.length; i++) {
			for (int j = 0; j < params1[0].length; j++) {
				for (int k = 0; k < params1[0][0].length; k++) {
					newParams[i][j][k] = (params1[i][j][k] + params2[i][j][k])/2;
				}
			}
		}
		return new City(layout, lights, newParams);
	}
	
	public double[][][] copyArr(double[][][] arr) {
		double[][][] copy = new double[arr.length][arr[0].length][arr[0][0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				for (int k = 0; k < arr[0][0].length; k++) {
					copy[i][j][k] = arr[i][j][k];
				}
			}
		}
		return copy;
	}
	
	public City[] generateNewPopulation(City[] populationF) {
		City[] newPopulation = new City[popSize];
		for (int i = 0; i < popSize/4; i++) {
			newPopulation[i] = new City(layout, lights, populationF[i].getParameters());
			//newPopulation[i].addWindow(window);
		}
		for (int i = popSize/4; i < popSize; i++) {
			City parent1 = populationF[random.nextInt(popSize/2)];
			City parent2 = populationF[random.nextInt(popSize/2)];
			newPopulation[i] = cross(parent1, parent2);
		}
		return newPopulation;
	}
	
	public City[] sortPopulation() {
		ArrayList<City> sorted = new ArrayList<City>();
		ArrayList<Double> fits = new ArrayList<Double>();
		for (int i = 0; i < popSize; i++) {
			double fit = population[i].getFitness();
			int counter = 0;
			while(counter < sorted.size() && sorted.get(counter).getFitness() < fit) {
				counter++;
			}
			if (counter < sorted.size()) {
				sorted.add(counter, population[i]);
				fits.add(counter, fit);
			}
			else {
				sorted.add(population[i]);
				fits.add(fit);
			}
		}
		//System.out.println(fits);
		City[] fittest = new City[popSize];
		for (int i = 0; i < popSize; i++) {
			fittest[i] = sorted.get(i);
		}
		return fittest;
	}

	public void createPopulation() {
		population = new City[popSize];
		for (int i = 0; i < popSize; i++) {
			population[i] = new City(layout, lights);
			//population[i].addWindow(window);
			population[i] = new City(layout, lights, population[i].getParameters());
			//population[i].addWindow(window);
			population[i].start();
		}
		printInfo();
	}
	
	public void printInfo() {
		System.out.println("--------------------");
		System.out.println("Generation: " + generationCount);
		System.out.println("Average Fit: " + getAverageFit());
		System.out.println("Best Fit: " + getBestFit());
		System.out.println("Worst Fit: " + getWorstFit());
		System.out.println("STDEV: " + getStdev());
		if (generationCount == 0) {
			for (int i = 0; i < popSize; i++) {
				System.out.print(population[i].getFitness() + " ");
			}
			System.out.println();
		}
	}
	
	public double getAverageFit() {
		double sum = 0;
		for (City city : population) {
			sum += city.getFitness();
		}
		return sum/popSize;
	}
	
	public double getStdev() {
		double average = getAverageFit();
		double sum = 0;
		for (City city : population) {
			sum += Math.pow(city.getFitness()-average, 2);
		}
		return Math.sqrt(sum/population.length);
	}
	
	public double getBestFit() {
		double best = population[0].getFitness();
		for (City city : population) {
			if (city.getFitness() < best) best = city.getFitness();
		}
		return best;
	}
	
	public double getWorstFit() {
		double best = population[0].getFitness();
		for (City city : population) {
			if (city.getFitness() > best) best = city.getFitness();
		}
		return best;
	}
	
	public int getPopSize() {
		return popSize;
	}

	public City[] getPopulation() {
		return population;
	}
	
}
