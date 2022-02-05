import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ToCSV {
	
	public static void main(String[] args) {
		File input = new File("C:\\Users\\shane\\OneDrive\\Desktop\\workspace\\Traffic\\src\\in.txt");
		PrintWriter writer = null;
		Scanner sc = null;
		try {
			sc = new Scanner(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer = new PrintWriter("C:\\Users\\shane\\OneDrive\\Desktop\\workspace\\Traffic\\src\\out.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			String generation = sc.nextLine();
			generation += "\n" + sc.nextLine();
			while(sc.hasNextLine()) {
				String next = sc.nextLine();
				if (next.equals("-------------------------------------------------------------------------------------------------------------------------------")) {
					break;
				}
				generation += "\n" + next;
			}
			runTrial(generation, writer);
		}
		sc.close();
		//writer.write("testMain");
		writer.close();
	}
	
	public static void runTrial(String trial, PrintWriter writer) {
		//writer.println("testTrial");
		ArrayList<String> avg = new ArrayList<String>();
		ArrayList<String> best = new ArrayList<String>();
		ArrayList<String> worst = new ArrayList<String>();
		ArrayList<String> stdev = new ArrayList<String>();
		Scanner sc = new Scanner(trial);
		String startLine = sc.nextLine();
		writeStartingData(startLine, writer);
		sc.nextLine();
		sc.nextLine();
		while (sc.hasNextLine()) {
			String generation = "";
			generation += sc.nextLine();
			while(sc.hasNextLine()) {
				String next = sc.nextLine();
				if (next.equals("--------------------")) {
					break;
				}
				generation += "\n" + next;
			}
			writeGeneration(generation, writer, avg, best, worst, stdev);
		}
		writer.print("Gen,");
		for (int i = 0; i < avg.size(); i++) {
			writer.print(i+",");
		}
		writer.println();
		writer.print("Avg,");
		for (String val : avg) {
			writer.print(val + ",");
		}
		writer.println();
		writer.print("Best,");
		for (String val : best) {
			writer.print(val + ",");
		}
		writer.println();
		writer.print("Worst,");
		for (String val : worst) {
			writer.print(val + ",");
		}
		writer.println();
		writer.print("Stdev,");
		for (String val : stdev) {
			writer.print(val + ",");
		}
		writer.println();
		writer.println();
		sc.close();
	}
	
	public static void writeStartingData(String info, PrintWriter writer) {
		ArrayList<String> cells = new ArrayList<String>();
		Scanner sc = new Scanner(info);
		cells.add("SIZE,");
		cells.add(sc.next());
		cells.add(sc.next());
		sc.next(); sc.next(); 
		cells.add(sc.next().toUpperCase() + ",");
		sc.next();
		cells.add(sc.next());
		cells.add("MUTATION_FACTOR,");
		sc.next(); 
		sc.next(); 
		sc.next();
		String mf = sc.next();
		cells.add(mf.substring(0, mf.length()-1)+",");
		String line = "";
		for (String cell : cells) {
			line += cell;
		}
		writer.println(line);
		sc.close();
	}
	
	public static void writeGeneration(String generation, PrintWriter writer, ArrayList<String> avg, ArrayList<String> best, ArrayList<String> worst, ArrayList<String> stdev) {
		//writer.write("testGen");
		Scanner sc = new Scanner(generation);
		sc.nextLine();
		String avgS = sc.nextLine();
		avg.add(avgS.substring(avgS.lastIndexOf(" ")+1));
		String bestS = sc.nextLine();
		best.add(bestS.substring(bestS.lastIndexOf(" ")+1));
		String worstS = sc.nextLine();
		worst.add(worstS.substring(worstS.lastIndexOf(" ")+1));
		String stdevS = sc.nextLine();
		stdev.add(stdevS.substring(stdevS.lastIndexOf(" ")+1));
		if (sc.hasNextLine()) {
			writer.print("Gen " + (avg.size()-1) + " values,");
			while(sc.hasNext()) {
				writer.print(sc.next()+",");
			}
			writer.println();
		}
		sc.close();
	}
	
}
