package player;

import java.util.ArrayList;
import java.util.List;

import engine.GameMaster;

public class PlayerManager {

	private GameDisplay gameDisplay;
	private GameMaster gameMaster;
	
	private List<String> keysDown;
	private List<String> prevKeysDown;
	
	private boolean primaryButtonDown;
	private boolean prevPrimaryButtonDown;
	
	public PlayerManager() {
		keysDown = new ArrayList<>();
		prevKeysDown = new ArrayList<>();
		primaryButtonDown = false;
		prevPrimaryButtonDown = false;
	}
	
	public List<String> getKeysDown() {
		return keysDown;
	}
	
	public List<String> getPrevKeysDown() {
		return prevKeysDown;
	}
	
	public boolean isPrimaryButtonDown() {
		return primaryButtonDown;
	}
	
	public boolean isPrevPrimaryButtonDown() {
		return prevPrimaryButtonDown;
	}
	
	public void setDisplay(GameDisplay currentGameDisplay) {
		gameDisplay = currentGameDisplay;
	}
	
	public void setEngine(GameMaster currentGameMaster) {
		gameMaster = currentGameMaster;
	}
	
}
