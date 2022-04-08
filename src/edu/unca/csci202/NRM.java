package edu.unca.csci202;
import java.util.ArrayList;
import java.util.HashMap;

public class NRM {
	protected Model model;
	protected NRMReaction[] reactions;
	protected HashMinHeapADT<NRMReaction> heap;
	protected HashMap<Reaction,Integer> lookup;//to find Reaction objects in NRMReaction array
	protected double currentTime;
	
	public NRM(Model m, HashMinHeapADT<NRMReaction> h) {
		model=m;
		reactions=new NRMReaction[m.getNumberOfReactions()];
		lookup=new HashMap<Reaction,Integer>();
		heap=h;
		// create NRMReaction objects
		// insert elements into heap
		int rxnCount=0;
		for (Reaction r : model.getReactions()) {
			reactions[rxnCount] = new NRMReaction(r);
			lookup.put(r,rxnCount);
			heap.insert(reactions[rxnCount]);
			rxnCount++;
		}
		if (!heap.isInitialized() || heap.getElementCount()!=rxnCount) {
			System.out.println("Error constructing NRM heap. Terminating");
			System.exit(1);
		}
	}
		
	protected void reset() {
		currentTime=0.0;
		// set species' populations to initial populations
		for (Species s : model.getSpecies()) {
			s.reset();
		}
		// generate next reaction times, update heap
		for (int i=0; i<reactions.length; i++) {
			reactions[i].generateNextFiringTime(0.0);
			heap.update(reactions[i]);
		}
	}
	
	// run a number of simulation steps, return time in ms
	// time does not include setup time
	public double simulate(int steps) {
		reset();
		if (heap.getMin().getNextFiringTime()==Double.POSITIVE_INFINITY) {
			System.out.println("Model is invalid (0 propensity at initial condition)");
			return 0;
		}

		//start timer
		final long startTime = System.currentTimeMillis();

		for (int count=0; count<steps; count++) {
			if (heap.getMin().getNextFiringTime()==Double.POSITIVE_INFINITY) {
				System.out.println("calling reset (will slow down timing results--recommend reducing steps)!");
				reset();
			}
			// fire next reaction
			NRMReaction rxn = heap.getMin();
			rxn.getReaction().fire();
			currentTime=rxn.getNextFiringTime();
			ArrayList<Reaction> dependencies = model.getDependencies(rxn.getReaction()); 
			for (Reaction dependentRxn : dependencies) {
				reactions[lookup.get(dependentRxn)].generateNextFiringTime(currentTime);
				heap.update(reactions[lookup.get(dependentRxn)]);
			}
		}
		
		final long endTime = System.currentTimeMillis();

		return (double)(endTime - startTime);

	}
}
