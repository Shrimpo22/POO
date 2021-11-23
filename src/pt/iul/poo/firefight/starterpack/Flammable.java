package pt.iul.poo.firefight.starterpack;

public interface Flammable {
	
	double getProbability();
	
	int getTurnsToBurn();
	
	boolean burnt();
	
	boolean burning();
	
	void burn();
	
	void ignite();
	
	int getImmunity();
	
	void douse();
}
