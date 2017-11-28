package player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import engine.EngineController;
import engine.sprite.DisplayableImage;
import javafx.scene.input.KeyCode;

/**
 * Acts as the intermediary class between the game engine and player.
 * @author Samarth and Ian
 *
 */
public class PlayerManager {

	private GameDisplay gameDisplay;
	
	private Set<String> keysDown = new HashSet<>();
	private Set<String> prevKeysDown;
	
	private boolean primaryButtonDown = false;
	private boolean prevPrimaryButtonDown = false;
	private double clickX;
	private double clickY;
	
	public PlayerManager() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getKeysDown() {
		return keysDown;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getPrevKeysDown() {
		return prevKeysDown;
	}
	
	public void setKeyPressed(KeyCode keyCode) {
		keysDown.add(keyCode.getName());
		System.out.println("por que no working");
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
	
	public void step() {
		prevKeysDown = new HashSet<>(keysDown);
		prevPrimaryButtonDown = primaryButtonDown;
	}
		
	/**
	 * Passes the images added to the game maps in authoring to the Game Display.
	 * @param images - The list of images to be displayed
	 */
	public void setImageData(List<DisplayableImage> images) {
		gameDisplay.setUpdatedImages(images);
	}
	
}
