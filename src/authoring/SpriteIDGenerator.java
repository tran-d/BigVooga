package authoring;

public class SpriteIDGenerator {
	
	private static SpriteIDGenerator instance = null;
	private int nextID;
	
	private SpriteIDGenerator(){
		nextID = 0;
	}
	
	public static SpriteIDGenerator getInstance(){
		if (instance ==null){
			instance = new SpriteIDGenerator();
		}
		return instance;
	}
	
	public int getUniqueID(){
		return nextID++;
	}

}
