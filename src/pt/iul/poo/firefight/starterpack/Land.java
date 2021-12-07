package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends Terrain {

	public Land(Point2D position) {
		super(position, "land", 0, 0, 0, 0);
	}
	
	@Override
	public void burn() {
	}

}
