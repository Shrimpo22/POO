package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Abies extends Terrain{
	private static final int BURN_TURNS = 20;
	private static final double PROBABILITY = 1;
	private static final int REWARD = 1500;

	public Abies(Point2D position) {
		super(position, "abies", 0, BURN_TURNS, PROBABILITY, REWARD);
	}
	
	@Override
	public String getName() {
		if(burnt()) {
			return "burntabies";
		}else {
			return "abies";
		}
	}
}
