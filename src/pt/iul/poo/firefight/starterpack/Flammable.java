package pt.iul.poo.firefight.starterpack;

public interface Flammable {
	
	void setAblaze(Fire fire);
	
	Fire getFire();
	
	double getProbability();
	
	int getTurnsToBurn();
	
	void setTurnsToBurn(int turnsToBurn);
	
	int getImmunity();
	
	boolean burnt();
	
	void setBurnt(boolean burnt);
	
	void burn();
	
	void douse();
}
