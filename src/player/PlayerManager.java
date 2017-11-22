package player;

import java.util.ArrayList;
import java.util.List;

import engine.EngineController;
import engine.GameMaster;
import javafx.scene.input.KeyCode;

public class PlayerManager {

	private GameDisplay gameDisplay;
	private EngineController engineController;
	
	private List<KeyCode> keysDown;
	private List<KeyCode> prevKeysDown;
	
	private boolean primaryButtonDown;
	private boolean prevPrimaryButtonDown;
	
	public PlayerManager() {
		keysDown = new ArrayList<>();
		prevKeysDown = new ArrayList<>();
		primaryButtonDown = false;
		prevPrimaryButtonDown = false;
	}
	
	public List<KeyCode> getKeysDown() {
		return keysDown;
	}
	
	public List<KeyCode> getPrevKeysDown() {
		return prevKeysDown;
	}
	
	public void setKeyPressed(KeyCode keyCode) {
		keysDown.add(keyCode);
	}
	
	public void setKeyReleased(KeyCode keyCode) {
		keysDown.remove(keyCode);
	}
	
	public void setPrimaryButtonPressed(double x, double y) {
		//TODO engine
	}
	
	public void setPrimaryButtonReleased(double x, double y) {
		//TODO engine
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
	
	public void setEngine(EngineController currentEngineController) {
		engineController = currentEngineController;
	}
	
}
