package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Gift extends Terrain{
	private static final int BURN_TURNS = 0;
	private static final double PROBABILITY = 0;
	private static final int REWARD = 0;
	
	public Gift(Point2D position) {
		super(position,"gift", 1, BURN_TURNS, PROBABILITY, REWARD);
	}
}
