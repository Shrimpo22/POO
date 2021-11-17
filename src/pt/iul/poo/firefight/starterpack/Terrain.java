package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement {
	public static Terrain generate(char type, Point2D position) {

		switch(type) {
		case 'p' : return new Pine(position);
		case 'e' : return new Eucalyptus(position);
		case 'm' : return new Undergrowth(position);
		case '_' : return new Land(position);

		default: throw new IllegalArgumentException();
		}
	}

}
