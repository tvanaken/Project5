package edu.unca.csci202;

public class Species {
	protected String name;
	protected double initialPopulation;
	protected double currentPopulation;
	
	public Species(String name, double initialPopulation) {
		this.name=name;
		this.initialPopulation=initialPopulation;
	}
	
	public void reset() {
		currentPopulation=initialPopulation;
	}
	
	public double getCurrentPopulation() {
		return currentPopulation;
	}
	
	public String getName() {
		return name;
	}
	
	public void changeCurrentPopulation(double delta) {
		currentPopulation+=delta;
	}
	
	public String toString() {
		return name+", currentPopulation="+currentPopulation+", initialPopulation="+initialPopulation;
	}
}
