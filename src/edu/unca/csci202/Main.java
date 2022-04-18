package edu.unca.csci202;

import java.util.Arrays;

/**
 * 
 * driver class to time the linear complexity solution
 * vs the logarithmic complexity solution
 * 
 * @author ksanft
 *
 */

public class Main {

	public static void main(String[] args) {
		System.out.println("Timing linear code...");
		int copies=16;
//		for (int i=0; i<8; i++) {
//			Model m = createModel(copies);
//			// construct a heap large enough to hold the number of reaction in the model
//			HashMinHeapLinear<NRMReaction> heap = new HashMinHeapLinear<NRMReaction>(m.getNumberOfReactions());
//			// construct simulation object (it will fill heap)
//			NRM solver = new NRM(m, heap);
//			// run a simulation
//			System.out.println(m.getNumberOfReactions()+","+solver.simulate(1000000));
//			// double the size of the model for next iteration
//			copies*=2;
//		}

		System.out.println("Timing logarithmic code...");
		copies=2;
		for (int i=0; i<8; i++) {
			Model m = createModel(copies);
			HashMinHeapLogarithmic<NRMReaction> heap2 = new HashMinHeapLogarithmic<NRMReaction>(m.getNumberOfReactions());
			NRM solver2 = new NRM(m, heap2);
			System.out.println(m.getNumberOfReactions()+","+solver2.simulate(1000000));
			// double the size of the model for next iteration
			copies*=2;
		}
	}

	public static Model createModel(int copies) {
		int numberOfSpecies = 4*copies;// 4 species per model
		int numberOfReactions = 4*copies;// 4 reactions per model
		
		Species[] s = new Species[numberOfSpecies];
		Reaction[] r = new Reaction[numberOfReactions];
		
		for (int i=0; i<copies; i++) {
			s[i*4] = new Species("susceptible"+i,80000);
			s[i*4+1] = new Species("exposed(asymptomatic)"+i,1);
			s[i*4+2] = new Species("infected"+i,0);
			s[i*4+3] = new Species("recovered"+i,0);
			r[i*4] = new Reaction("infection(from asymptomatic)"+i, 0.00001,
					// susceptible and exposed interact
					new Species[] {s[i*4],s[i*4+1]},
					// one of each
					new double[] {1,1},
					// result is two exposed
					new Species[] {s[i*4+1]},
					new double[] {2});
			r[i*4+1] = new Reaction("infection(from symptomatic)"+i, 0.000001,
					// susceptible and exposed interact
					new Species[] {s[i*4],s[i*4+2]},
					// one of each
					new double[] {1,1},
					// result is exposed and infected
					new Species[] {s[i*4+1],s[i*4+2]},
					new double[] {1,1});
			r[i*4+2] = new Reaction("become symptomatic"+i,1.0/5,
					// exposed becomes infected 
					new Species[] {s[i*4+1]},
					new double[] {1},
					new Species[] {s[i*4+2]},
					new double[] {1});
			r[i*4+3] = new Reaction("recover"+i,1.0/14,
					new Species[] {s[i*4+2]},
					new double[] {1},
					new Species[] {s[i*4+3]},
					new double[] {1});
		}
		return new Model(Arrays.asList(s),Arrays.asList(r));
	}

}
