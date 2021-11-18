package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Land extends Terrain {

	public Land(Point2D position) {
		super(position, 0, 0);
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
