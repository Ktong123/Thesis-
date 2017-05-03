package ga.tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route {
	// fitness flag
	private boolean isFitnessChanged = true;
	private double  fitness = 0;
	//array of cities
	private ArrayList<City> cities = new ArrayList<City>();
	//constructor that takes in geneticAlgorithm
	public Route(GeneticAlgorithm geneticAlgorithm) {
		geneticAlgorithm.getInitialRoute().forEach(x -> cities.add(null));
	}
	//initialize the list of cities then shuffle them
	public Route(ArrayList<City> cities) {
		this.cities.addAll(cities);
		Collections.shuffle(this.cities);
	}
	public ArrayList<City> getCities() {
		isFitnessChanged = true;
		return cities;
	}
	public double getFitness() {
		if (isFitnessChanged == true) {
			fitness = (1/calculateTotalDistance())*100;
			isFitnessChanged = false;
		}
		return fitness;
	}
	public double calculateTotalDistance() {
		int citiesSize = this.cities.size();
		return (int) (this.cities.stream().mapToDouble(x -> {
			int	cityIndex = this.cities.indexOf(x);
			double returnValue = 0;
			// gets distance between city 1 and 2, 2 and 3 etc
			if (cityIndex < citiesSize - 1) 
				returnValue = x.measureDistance(this.cities.get(cityIndex + 1));
			return returnValue;
			// adds the cities up from initial to last
		}).sum() + this.cities.get(0).measureDistance(this.cities.get(citiesSize - 1)));
		
	}
	// toString method prints out cities
	public String toString() { 
		return Arrays.toString(cities.toArray()); 
	}

}
