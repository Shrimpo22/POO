package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Pine extends Terrain {
	private static final int BURN_TURNS = 10;
	private static final double PROBABILITY = 0.05;
	private static final int REWARD = 1000;

	public Pine(Point2D position) {
		super(position, "pine", 0, BURN_TURNS, PROBABILITY, REWARD);
	}

	@Override
	public String getName() {
		if(burnt()) {
			return "burntpine";
		}else {
			return "pine";
		}
	}

	
}
