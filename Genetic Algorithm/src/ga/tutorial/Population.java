package ga.tutorial;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Population {

	//array of routes
	private ArrayList<Route> routes = new ArrayList<Route>(GeneticAlgorithm.POPULATION_SIZE);
	//constructor
	public Population(int populationSize, GeneticAlgorithm geneticAlgorithm) {
		IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(geneticAlgorithm.getInitialRoute())));
	}
	//constructor that initializes all the routes
	public Population(int populationSize, ArrayList<City> cities) {
		IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(cities)));
	}
	//get method for the routes
	public ArrayList<Route> getRoutes() { 
		return routes; 
	}
	// solve routes by their fitness
	public void sortRoutesByFitness() {
		routes.sort((route1, route2) -> {
			int flag = 0;
			if (route1.getFitness() > route2.getFitness())
				flag = -1;
			else if (route1.getFitness() < route2.getFitness())
				flag = 1;
			return flag;
		});
	}
	
}
