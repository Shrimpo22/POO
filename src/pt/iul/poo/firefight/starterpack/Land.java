package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Land extends Terrain {

	public Land(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "land";
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
