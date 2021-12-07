package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Mobile extends GameElement implements Movable{
	
	public Mobile(Point2D position, String name, int layer) {
		super(position, name, layer);
	}
	
	public static Mobile generate(String type, Point2D position) {

		switch(type) {
		case "FiremanBot" : return new FiremanBot(position);
		case "Fireman" : return new Fireman(position);
		case "Bulldozer" : return new Bulldozer(position);
		case "Fire" : return new Fire(position);

		default: throw new IllegalArgumentException();
		}
	}
	
	public void move() {
		
	}
	
}
