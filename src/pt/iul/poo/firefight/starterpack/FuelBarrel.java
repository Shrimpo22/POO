package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class FuelBarrel extends Terrain{
	private static final int BURN_TURNS = 3;
	private static final double PROBABILITY = 0.9;
	private static final int REWARD = 2000;
	private boolean canEnter;

	public FuelBarrel(Point2D position) {
		super(position, "eucaliptus", 1, BURN_TURNS, PROBABILITY, REWARD);
		canEnter=true;
	}

	@Override
	public void burn() {
		if(getTurnsToBurn() > 1)
			setTurnsToBurn(getTurnsToBurn()-1) ;
		else if(canEnter){
			
			System.out.println("Boosh!");
			canEnter=false;
			game.addElement(generate('_', getPosition()));
			getFire().spread(getPosition().getWideNeighbourhoodPoints());
			setBurnt(true);
		}
	}

	@Override
	public String getName() {
		if(burnt()) {
			return "explosion";
		}else {
			return "fuelbarrel";
		}
	}
}
