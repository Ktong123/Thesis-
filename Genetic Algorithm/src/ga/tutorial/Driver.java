package ga.tutorial;

import java.util.ArrayList;
import java.util.Arrays;

public class Driver {
	
	//setting up an array of cities
	public ArrayList<City> initialRoute = new ArrayList<City>(Arrays.asList(
		new City("A", 42, 71),
		new City("B", 29, 95),
		new City("C", 30, 97),
		new City("D", 37, 122),
		new City("E", 39, 104),
		new City("F", 34, 118),
		new City("G", 41, 87),
		new City("H", 40, 74),
		new City("I", 32, 96),
		new City("J", 47, 122)
	));
	
	public static void main(String[] args) {
		// Instantiate the driver
		Driver driver = new Driver();
		// Instantiate the population
		Population population = new Population(GeneticAlgorithm.POPULATION_SIZE, driver.initialRoute);
		// sort routes by fitness
		population.sortRoutesByFitness();
		// genetic algorithm instance
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(driver.initialRoute);
		int generationNumber = 0;
		System.out.println("Generation Number is #" +generationNumber);
		driver.printPopulation(population);
		while (generationNumber < GeneticAlgorithm.NUMB_OF_GENERATIONS) {
			System.out.println("Generation Number is #" +(generationNumber+1));
			population = geneticAlgorithm.evolve(population);
			population.sortRoutesByFitness();
			driver.printPopulation(population);
			generationNumber++;
		}
		System.out.println("Best Route so Far:" + population.getRoutes().get(0));
		System.out.println("with a distance of: " +String.format("%.2f", population.getRoutes().get(0).calculateTotalDistance()));
	}

	public void printPopulation(Population population) {
		population.getRoutes().forEach(x -> {
			System.out.println(Arrays.toString(x.getCities().toArray()) + " |  " +
					String.format("%.4f", x.getFitness()) +"  |  "+ String.format("%.2f", x.calculateTotalDistance()));
		});
		System.out.println("");
	}
	
}