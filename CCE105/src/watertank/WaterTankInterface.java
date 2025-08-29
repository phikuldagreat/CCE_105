package watertank;

public interface WaterTankInterface {

	int fill ();
	
	int drain ();
	
	int getLevel();
	
	boolean isEmpty();
	
	boolean isFull();
}
