package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public class Undergrowth extends Terrain{
	private static final int BURN_TURNS = 3;
	private static final double PROBABILITY = 0.15;

	public Undergrowth(Point2D position) {
		super(position, BURN_TURNS, PROBABILITY);
	}

	@Override
	public String getName() {
		if(burnt() == 0) {
			Debug.Check("grass", false);
			return "grass";
		}else {
			Debug.Check("burntgrass", false);
			return "burnt";
		}
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
