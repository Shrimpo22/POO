package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Movable extends GameElement{
	
	public static Movable generate(String type, Point2D position) {

		switch(type) {
		case "Fireman" : return new Fireman(position);
		case "Bulldozer" : return new Bulldozer(position);
		case "Fire" : return new Fire(position);

		default: throw new IllegalArgumentException();
		}
	}
	
}
