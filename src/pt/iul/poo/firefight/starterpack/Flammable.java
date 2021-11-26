package pt.iul.poo.firefight.starterpack;

public interface Flammable {
	
	double getProbability();
	
	int getTurnsToBurn();
	
	int getImmunity();
	
	boolean burnt();	
	
	void burn();
	
	void douse();
}
