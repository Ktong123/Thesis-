package ga.tutorial;

public class City {

	// initialize variables
	private double xCoord;
	private double yCoord;
	private String name;
	// create a city
	public City(String name, double xCoord, double yCoord) {
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	//Get methods to return the variables
	public String getName() {
		return name;
	}
	public double getYCoord() {
		return this.yCoord;
	}
	public double getXCoord() {
		return this.xCoord;
	}
	public String toString() {
		return getName();
	}
	public double measureDistance(City city) {
		
		double x = city.getXCoord() - this.getXCoord();
		double y = city.getYCoord() - this.getYCoord();
		double a = Math.sqrt(Math.pow(x, 2) + Math.pow(y,  2));
		/*
		System.out.println(x);
		System.out.println(y);
		System.out.println(a);*/
		return a;
	}
}
