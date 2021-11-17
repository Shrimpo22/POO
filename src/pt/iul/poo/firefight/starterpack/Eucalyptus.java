package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Eucalyptus extends Terrain {

	public Eucalyptus(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "eucaliptus";
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
