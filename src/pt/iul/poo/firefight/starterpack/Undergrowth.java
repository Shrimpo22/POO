package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Undergrowth extends Terrain{

	public Undergrowth(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "grass";
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
