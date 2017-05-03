package ga.tutorial;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GeneticAlgorithm {
	
	// probability that the chromosome or gene will do mutation
	public static final double MUTATION_RATE = 0.25;
	// tournament selection for crossover
	public static final int TOURNAMENT_SELECTION_SIZE = 3;
	//number of routes in a generation
	public static final int POPULATION_SIZE = 10;
	// elite routes will not be subject to crossover or mutation
	public static final int NUMB_OF_ELITE_ROUTES = 1;
	// number of generations (0 to 29)
	public static final int NUMB_OF_GENERATIONS = 50;
	
	private ArrayList<City> initialRoute = null;
	//constructor that calls initialRoute from the driver class
	public GeneticAlgorithm(ArrayList<City> initialRoute) { 
		this.initialRoute = initialRoute; 
	}
	//get method that returns the initalRoute
	public ArrayList<City> getInitialRoute() {
		return initialRoute;
	}
	// public evolve method that will apply crossover and mutation to population
	public Population evolve(Population population) {
		return mutatePopulation(crossoverPopulation(population));
	}
	// crossover method
	Population crossoverPopulation(Population population) {
		// instantiate the new population given the size of the old
		Population crossoverPopulation = new Population(population.getRoutes().size(), this);
		// move the elite chromosomes
		IntStream.range(0, NUMB_OF_ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
		IntStream.range(NUMB_OF_ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
			Route route1 = selectTournamentPopulation(population).getRoutes().get(0);
			Route route2 = selectTournamentPopulation(population).getRoutes().get(0);
			crossoverPopulation.getRoutes().set(x, crossoverRoute(route1, route2));
		});
		return crossoverPopulation;
	}
	// mutation method
	Population mutatePopulation(Population population) {
		// for each non-elite route, we call the mutate route method
		population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= NUMB_OF_ELITE_ROUTES).forEach(x -> mutateRoute(x));
		return population;
	}
	// crossoverPopulation will call crossoverRoute method
	// example crossoverRoute method
	// route 1:                     [NY, San Fran, Houston, Chicago, Boston, Austin, Seattle, Denver, Dallas, LA]
	// route 2:                     [LA, Seattle, Austin, Boston, Denver, NY, Houston, Dallas, San Fran, Chicago]
	// intermediate crossover Route [NY, San Fran, Houston, Chicago, Boston, null, null, null, null, null]
	// final  crossover Route       [NY, San Fran, Hosuton, Chicago, Boston, LA, Seattle, Austin, Denver, Dallas]
	Route crossoverRoute(Route route1, Route route2) {
		Route crossoverRoute = new Route(this);
		Route tempRoute1 = route1;
		Route tempRoute2 = route2;
		if (Math.random() < 0.5) {
			tempRoute1 = route2;
			tempRoute2 = route1;
		}
		for (int x = 0; x < crossoverRoute.getCities().size()/2; x++) 
			crossoverRoute.getCities().set(x, tempRoute1.getCities().get(x));
		return fillNullsinCrossoverRoute(crossoverRoute, tempRoute2);
	}
	// this takes the intermediate CR and route 2 and fills the 'null' to form the final route
	private Route fillNullsinCrossoverRoute(Route crossoverRoute, Route route) {
		route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(cityX -> {
			for (int y = 0; y < route.getCities().size(); y++) {
				if (crossoverRoute.getCities().get(y) == null) {
					crossoverRoute.getCities().set(y, cityX);
					break;
				}
			}
		});
		return crossoverRoute;
	}
	
	// mutatePopulation will call mutateRoute method
	// example route mutation
	// original route: [Boston, Denver, LA, Austin, NY, Seattle, Chicago, San Fran, Dallas, Houston]
	// mutated route: [Boston, Denver, NY, Austin, LA, Seattle, San Fran, Chicago, Dallas, Houston]
	// the code takes 2 cities and flips them to form the mutated route
	Route mutateRoute(Route route) {
		route.getCities().stream().filter(x -> Math.random() < MUTATION_RATE).forEach(cityX -> {
			int y = (int) (route.getCities().size() * Math.random());
			City cityY = route.getCities().get(y);
			route.getCities().set(route.getCities().indexOf(cityX), cityY);
			route.getCities().set(y,cityX);
		});
		return route;
	}
	// route1 and route2 are obtained using tournament selection
	// there are 3 routes in the tournament population
	Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
		IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes().set(
				x, population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
		tournamentPopulation.sortRoutesByFitness();
		return tournamentPopulation;
	}
}
