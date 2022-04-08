package edu.unca.csci202;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class Model {
	Iterable<Species> species;
	Iterable<Reaction> reactions;
	int numberOfSpecies;
	int numberOfReactions;
	HashMap<Reaction, ArrayList<Reaction>> dependencyGraph;
	
	public Model(Iterable<Species> species, Iterable<Reaction> reactions) {
		this.species=species;
		this.reactions=reactions;
		numberOfReactions=0;
		//build dependency graph
		dependencyGraph = new HashMap<Reaction, ArrayList<Reaction>>();
		for (Reaction r1 : reactions) {
			numberOfReactions++;
			dependencyGraph.put(r1, new ArrayList<Reaction>());
			// put this reaction in its own dependency graph
			dependencyGraph.get(r1).add(r1);
			// see which reactions are affected with r1 fires
			HashSet<Species> affectedSpecies = new HashSet<Species>();
			for (int i=0; i<r1.getReactants().length; i++) {
				affectedSpecies.add(r1.getReactants()[i]);
			}
			for (int i=0; i<r1.getProducts().length; i++) {
				affectedSpecies.add(r1.getProducts()[i]);
			}
			for (Reaction r2 : reactions) {
				// see if reactants depend on any affected species
				for (int i=0; i<r2.getReactants().length; i++) {
					if (r1!=r2 && affectedSpecies.contains(r2.getReactants()[i])) {
						dependencyGraph.get(r1).add(r2);
						break;
					}
				}
			}
			
		}
		//count number of species
		numberOfSpecies=0;
		for (Species s : species) {
			numberOfSpecies++;
		}
	}
	public Iterable<Species> getSpecies() {
		return species;
	}
	
	public Iterable<Reaction> getReactions() {
		return reactions;
	}
	
	public int getNumberOfSpecies() {
		return numberOfSpecies;
	}
	public int getNumberOfReactions() {
		return numberOfReactions;
	}
	
	public ArrayList<Reaction> getDependencies(Reaction r) {
		return dependencyGraph.get(r);
	}
 }
