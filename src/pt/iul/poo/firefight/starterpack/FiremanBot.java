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
			if(scout()== null)
				return;
			Direction charge = getPosition().directionTo(scout());
			System.out.println("Charge! "+charge);

			switch(charge) {
			case LEFT : setName("firemanbot_left"); break;
			case RIGHT: setName("firemanbot_right"); break;
			}

			Fire fire = (Fire) game.findElement(GameEngine.clamp(getPosition().plus(charge.asVector())), o -> o instanceof Fire);
			if(fire != null) {
				fire.douse(charge);
			}else if(canMoveTo(GameEngine.clamp(getPosition().plus(charge.asVector()))))
				setPosition(GameEngine.clamp(getPosition().plus(charge.asVector())));
		}else {
			boolean hasMoved = false;

			while (hasMoved == false)  {

				Direction randDir = Direction.random();
				Point2D newPosition = getPosition().plus(randDir.asVector());
				
				Fire fire = (Fire) game.findElement(newPosition, o -> o instanceof Fire);
				if(fire != null) {
					fire.douse(randDir);
					hasMoved = true;
				}else if (canMoveTo(newPosition)) {
					setPosition(newPosition);
					hasMoved = true;
				}
			}
		}
	}

	public Point2D scout() {
		List<Fire> fires = game.getElements(o->o instanceof Fire && !((Fire)o).doused());
		int bestDistance = 100;
		Point2D fireToGo = null;
		for(Fire fire : fires) {

			if(getPosition().distanceTo(fire.getPosition())<bestDistance){
				bestDistance = getPosition().distanceTo(fire.getPosition());
				fireToGo = fire.getPosition();
			}
		}
		System.out.println("Fire bot!"+ fireToGo);
		return fireToGo;
	}

	public boolean canMoveTo(Point2D p) {
		
		if(game.findElement(p, o->o instanceof Fireman || o instanceof FiremanBot) != null)
			return false;
		if(!game.getElements(o->o.getPosition().equals(p) && o instanceof Drivable).isEmpty())
			return false;
		
					
		if (p.getX() < 0) return false;
		if (p.getY() < 0) return false;
		if (p.getX() >= GameEngine.GRID_WIDTH) return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT) return false;
		return true;
	}

	@Override
	public void tick() {
		move();
	}

}
