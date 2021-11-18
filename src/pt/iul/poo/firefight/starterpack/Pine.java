package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public class Pine extends Terrain {
	private static final int BURN_TURNS = 10;
	private static final double PROBABILITY = 0.5;

	public Pine(Point2D position) {
		super(position, BURN_TURNS, PROBABILITY);
	}

	@Override
	public String getName() {
		if(burnt() == 0) {
			Debug.Check("pine", false);
			return "pine";
		}else {
			Debug.Check("burntpine", false);
			return "burntpine";
		}
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
