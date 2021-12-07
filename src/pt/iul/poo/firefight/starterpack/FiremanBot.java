package pt.iul.poo.firefight.starterpack;

import java.util.List;
import java.util.Random;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class FiremanBot extends Mobile implements Tickable{
	private int iq;
	Random random = new Random();

	public FiremanBot(Point2D position) {
		super(position, "firemanbot", 3);
		this.iq = random.nextInt(100);
	}

	@SuppressWarnings("incomplete-switch")
	public void move(){

		if(random.nextInt(100) < iq) {
			Direction charge = getPosition().directionTo(scout());

			switch(charge) {
			case LEFT : setName("fireman_left"); break;
			case RIGHT: setName("fireman_right"); break;
			}

			setPosition(GameEngine.clamp(getPosition().plus(charge.asVector())));

		}else {

		}
	}

	public Point2D scout() {
		List<Fire> fires = game.getElements(o->o instanceof Fire && !((Fire)o).doused());
		int bestDistance = 10;
		Point2D fireToGo = null;
		for(Fire fire : fires) {

			if(getPosition().distanceTo(fire.getPosition())<bestDistance){
				bestDistance = getPosition().distanceTo(fire.getPosition());
				fireToGo = fire.getPosition();
			}
		}

		return fireToGo;
	}
}
