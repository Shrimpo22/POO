package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends Movable {

	public Bulldozer(Point2D position) {
		this.position = position;
	}
	
	@Override
	public String getName() {
		return "bulldozer_down";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 1;
	}
}
