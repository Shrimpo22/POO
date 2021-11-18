package pt.iul.poo.firefight.starterpack;

public interface Flammable {
	
	double getProbability();
	
	int getTurnsToBurn();
	
	int burnt();
	
	void burn();
	
}
