package player;

import java.util.ArrayList;
import java.util.List;

import engine.DisplayableImage;
import engine.EngineController;
import javafx.scene.input.KeyCode;

public class PlayerManager {

	private GameDisplay gameDisplay;
	private EngineController engineController;
	
	private List<String> keysDown;
	private List<String> prevKeysDown;
	
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
	
	public List<String> getKeysDown() {
		return keysDown;
	}
	
	public List<String> getPrevKeysDown() {
		return prevKeysDown;
	}
	
	public void setKeyPressed(KeyCode keyCode) {
		keysDown.add(keyCode.getName());
	}
	
	public void setKeyReleased(KeyCode keyCode) {
		keysDown.remove(keyCode.getName());
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
	 * @param images
	 */
	public void setImageData(List<DisplayableImage> images) {
		gameDisplay.setUpdatedImages(images);
	}
	
}
