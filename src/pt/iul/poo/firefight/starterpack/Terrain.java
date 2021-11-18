package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement implements Flammable {
	
	private int BURN_TURNS = 0;
	private int turnsToBurn;
	private int burnt;
	private double probability;
	
	
	public Terrain(Point2D position, int BURN_TURNS, double probability) {
		this.position = position;
		this.burnt = 0;
		this.BURN_TURNS = BURN_TURNS;
		turnsToBurn = BURN_TURNS;
		this.probability = probability;
		
	}
	
	public static Terrain generate(char type, Point2D position) {

		switch(type) {
		case 'p' : return new Pine(position);
		case 'e' : return new Eucalyptus(position);
		case 'm' : return new Undergrowth(position);
		case '_' : return new Land(position);

		default: throw new IllegalArgumentException();
		}
	}
	
	public double getProbability() {
		return probability;
	}
	
	public int getTurnsToBurn() {
		return turnsToBurn;
	}
	
	public int burnt() {
		return burnt;
	}
	
	public void burn() {
		if(turnsToBurn > 0)
			turnsToBurn --;
		else
			burnt = 1;
	}

}
