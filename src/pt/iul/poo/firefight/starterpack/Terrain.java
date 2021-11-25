package pt.iul.poo.firefight.starterpack;

import java.util.UUID;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement implements Flammable, Tickable {

//	private int BURN_TURNS = 0;
	private int turnsToBurn;
	private boolean burnt;
	private double probability;
	private int temporalImmunity = 0;


	public Terrain(Point2D position, int BURN_TURNS, double probability) {
		this.position = position;
		this.burnt = false;
//		this.BURN_TURNS = BURN_TURNS;
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
	
	
	@Override
	public int getLayer() {
		return 0;
	}


	public double getProbability() {
		return probability;
	}

	public int getTurnsToBurn() {
		return turnsToBurn;
	}

	public boolean burnt() {
		return burnt;
	}

	public void burn() {
		Debug.attribute("Terrain["+ this +"], turns to burn: ", turnsToBurn, 2);
		if(turnsToBurn > 1)
			turnsToBurn --;
		else
			burnt = true;
	}

	public int getImmunity() {
		return temporalImmunity;
	}

	public void douse() {
		temporalImmunity = 5;
	}


	@Override
	public void tick() {
		if(temporalImmunity > 0)
			temporalImmunity --;
	}

}
