package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

public abstract class Terrain extends GameElement implements Flammable, Rewardable, Tickable{

	private int reward;
	private int turnsToBurn;
	private boolean burnt;
	private double probability;
	private int temporalImmunity = 0;

	public Terrain(Point2D position, String name, int layer, int BURN_TURNS, double probability, int reward) {
		super(position, name, layer);
		this.reward = reward;
		this.burnt = false;
		turnsToBurn = BURN_TURNS;
		this.probability = probability;
	}

	public static Terrain generate(char type, Point2D position) {

		switch(type) {
		case 'b' : return new FuelBarrel(position);
		case 'a' : return new Abies(position);
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

	public void setTurnsToBurn(int turnsToBurn) {
		this.turnsToBurn = turnsToBurn;

	}

	public int getImmunity() {
		return temporalImmunity;
	}

	public void setVulnerability() {
		temporalImmunity = 0;
	}

	public boolean burnt() {
		return burnt;
	}

	public void setBurnt(boolean burnt) {
		this.burnt = burnt;
	}

	public void burn() {
		if(turnsToBurn > 1)
			turnsToBurn --;
		else
			burnt = true;
	}

	public void douse() {
		temporalImmunity = 5;
	}

	public int reward() {
		return reward;
	}

	@Override
	public void tick() {
		if(temporalImmunity > 0)
			temporalImmunity --;
	}

}
