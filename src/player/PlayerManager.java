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
	
	private final EventHandler<KeyEvent> keyEventHandler;
	private final EventHandler<MouseEvent> mouseEventHandler;
	
	private List<KeyCode> keysDown;
	private List<KeyCode> prevKeysDown;
	
	private boolean primaryButtonDown;
	private boolean prevPrimaryButtonDown;
	
	public PlayerManager() {
		keysDown = new ArrayList<>();
		prevKeysDown = new ArrayList<>();
		primaryButtonDown = false;
		prevPrimaryButtonDown = false;
		
		keyEventHandler = new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				keysDown.add(keyEvent.getCode());
			}
		};
		
		mouseEventHandler = new EventHandler<MouseEvent>() {
			public void handle(final MouseEvent mouseEvent) {
				primaryButtonDown = mouseEvent.isPrimaryButtonDown();
			}
		};
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
		//TODO engine
	}
	
	public void setPrimaryButtonUp(double x, double y) {
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
	
	public void step() {
		prevKeysDown = keysDown;
		keysDown.clear();
		keyEventHandler.handle(keyEvent);					//how to get keyEvent?
		
		prevPrimaryButtonDown = primaryButtonDown;
		primaryButtonDown = false;
		mouseEventHandler.handle(mouseEvent);				//how to get mouseEvent?

	/**
	 * INCOMPLETE: for handling the given image data at each step.
	 * @param images
	 */
	public void getImageData(List<BoundedImage> images) {
	
	}
	
}
