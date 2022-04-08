package edu.unca.csci202;
import java.util.Random;

/**
 * A Reaction wrapper class for use with NRM simulator
 * 
 * @author ksanft
 *
 */
public class NRMReaction implements Comparable<NRMReaction> {

	protected Reaction reaction;
	protected double nextFiringTime;
	protected Random rand;
	
	public NRMReaction(Reaction reaction) {
		this.reaction=reaction;
		nextFiringTime=Double.POSITIVE_INFINITY;
		rand = new Random();
	}
	
	public double getNextFiringTime() {
		return nextFiringTime;
	}
	
	public void generateNextFiringTime(double currentTime) {
		double prop = reaction.propensity();
		if (prop==0) {
			nextFiringTime=Double.POSITIVE_INFINITY;
		} else {
			nextFiringTime=currentTime+Math.log(1-rand.nextDouble())/(-prop);
		}
	}
	
	public Reaction getReaction() {
		return reaction;
	}
	
	@Override
	public int compareTo(NRMReaction o) {
		if (nextFiringTime<o.nextFiringTime) {
			return -1;
		} else if (nextFiringTime>o.nextFiringTime) {
			return 1;
		}
		return 0;
	}

}
