package watertank;

public interface WaterTankInterface {

	int fill (int amount);
	
	int drain (int amount);
	
	int getLevel();
	
	boolean isEmpty();
	
	boolean isFull();
}
