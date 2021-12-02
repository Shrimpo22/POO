package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Eucalyptus extends Terrain {
	private static final int BURN_TURNS = 5;
	private static final double PROBABILITY = 1;
	private static final int REWARD = 500;

	public Eucalyptus(Point2D position) {
		super(position, "eucaliptus", 0, BURN_TURNS, PROBABILITY, REWARD);
	}
	
	@Override
	public String getName() {
		if(burnt()) {
			return "burnteucaliptus";
		}else {
			return "eucaliptus";
		}
	}
}
