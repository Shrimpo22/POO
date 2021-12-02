package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Undergrowth extends Terrain{
	private static final int BURN_TURNS = 3;
	private static final double PROBABILITY = 0.15;
	private static final int REWARD = 250;

	public Undergrowth(Point2D position) {
		super(position,"grass", 0, BURN_TURNS, PROBABILITY, REWARD);
	}

	@Override
	public String getName() {
		
		if(burnt()) {
			return "burnt";

		}else {
			return "grass";
		}
	}

}
