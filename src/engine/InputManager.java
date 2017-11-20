package engine;

import java.util.ArrayList;
import java.util.List;

public class InputManager {

	private List<String> keysDown;
	private List<String> prevKeysDown;
	
	private boolean primaryButtonDown;
	private boolean prevPrimaryButtonDown;
	
	public InputManager() {
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
	
}
