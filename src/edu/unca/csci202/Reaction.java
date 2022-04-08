package edu.unca.csci202;

public class Reaction {
	protected String name;
	protected double rate; // reaction rate
	protected Species[] reactants;
	protected double[] reactantStoichiometry;
	protected int fireCount;
	
	protected Species[] products;
	protected double[] productStoichiometry;

	public Reaction(String name, double rate, Species[] reactants, double[] reactantStoich,
			Species[] products, double[] productStoich) {
		this.name=name;
		this.rate=rate;
		this.reactants=reactants;
		reactantStoichiometry=reactantStoich;
		this.products=products;
		productStoichiometry=productStoich;
		fireCount=0;
		// WARNING: NO VALIDITY CHECKS!
	}
	
	// calculate the reate at which the reaction is occurring
	public double propensity() {
		if (reactants.length==0) {
			return rate;
		} else if (reactants.length==1) {
			if (reactantStoichiometry[0]==1) {
				return rate*reactants[0].getCurrentPopulation();
			} else if (reactantStoichiometry[0]==2) {
				return rate/2*reactants[0].getCurrentPopulation()*(reactants[0].getCurrentPopulation()-1);
			}
		} else {
			return rate*reactants[0].getCurrentPopulation()*reactants[1].getCurrentPopulation();
		}
		//unreachable
		return 0;
	}

	// fire the reaction
	// decrement the population of reactants by reactantStoichiometry
	// increment the population of products by productStoichiometry
	public void fire() {
		for (int i=0; i<reactants.length; i++) {
			reactants[i].changeCurrentPopulation(-reactantStoichiometry[i]);
		}
		for (int i=0; i<products.length; i++) {
			products[i].changeCurrentPopulation(productStoichiometry[i]);
		}
		fireCount++;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Species[] getReactants() {
		return reactants;
	}

	public double[] getReactantStoichiometry() {
		return reactantStoichiometry;
	}

	public Species[] getProducts() {
		return products;
	}

	public double[] getProductStoichiometry() {
		return productStoichiometry;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;//+reactants+reactantStoichiometry+products+productStoichiometry;
	}
	
	public int getFireCount() {
		return fireCount;
	}
}
