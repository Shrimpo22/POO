package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public class Eucalyptus extends Terrain {
	private static final int BURN_TURNS = 5;
	private static final double PROBABILITY = 0.10;

	public Eucalyptus(Point2D position) {
		super(position, BURN_TURNS, PROBABILITY);
	}
	
	@Override
	public String getName() {
		if(burnt() == 0) {
			Debug.Check("eucaliptus", false);
			return "eucaliptus";
		}else {
			Debug.Check("burnteucaliptus", false);
			return "burnteucaliptus";
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
