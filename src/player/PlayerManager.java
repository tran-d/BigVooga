package player;

import java.util.ArrayList;
import java.util.List;

import engine.BoundedImage;
import engine.EngineController;
import engine.GameMaster;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PlayerManager {

	private GameDisplay gameDisplay;
	private EngineController engineController;
	
	private List<KeyCode> keysDown;
	private List<KeyCode> prevKeysDown;
	
	private boolean primaryButtonDown;
	private boolean prevPrimaryButtonDown;
	private double clickX;
	private double clickY;
	
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
	
	public void setPrimaryButtonDown(double x, double y) {
		primaryButtonDown = true;
		clickX = x;
		clickY = y;
	}
	
	public double getClickX() {
		return clickX;
	}
	
	public double getClickY() {
		return clickY;
	}
	
	public void setPrimaryButtonUp(double x, double y) {
		primaryButtonDown = false;
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
	
	public void step() {
		prevKeysDown = keysDown;
		prevPrimaryButtonDown = primaryButtonDown;
	}
		
	/**
	 * INCOMPLETE: for handling the given image data at each step.
	 * @param images
	 */
	public void getImageData(List<BoundedImage> images) {
	
	}
	
}
