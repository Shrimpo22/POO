package pt.iul.poo.firefight.starterpack;

import debug.Debug;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement implements Flammable {

	private int turnsToBurn;
	private boolean burnt;
	private double probability;
	private int temporalImmunity = 0;
	
	public Terrain(Point2D position, String name, int layer, int BURN_TURNS, double probability) {
		super(position, name, layer);
		this.burnt = false;
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

	public int getImmunity() {
		return temporalImmunity;
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

	public void douse() {
		temporalImmunity = 5;
	}

	@Override
	public void tick() {
		if(temporalImmunity > 0)
			temporalImmunity --;
	}

}
