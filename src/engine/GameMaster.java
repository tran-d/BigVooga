package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.player.PlayerManager;
import engine.sprite.BoundedImage;
import engine.sprite.DisplayableImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Controls the game flow, and passes game info to PlayerManager.
 * @author Nikolas Bramblett, ...
 *
 */
public class GameMaster implements EngineController{
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DELAY = 1000/DEFAULT_FPS;
	
	private GameWorld currentWorld;
	private List<GameWorld> madeWorlds;
	private Timeline gameLoop;
	private GlobalVariables globalVars;
	private PlayerManager playerManager;
	
	private GameObjectFactory blueprintManager;
	
	public GameMaster() {
		// TODO Auto-generated constructor stub
		madeWorlds = new ArrayList<>();
		
		
		globalVars = new GlobalVariables();
	}
	
	
	/**
	 * Begins the gameloop, will continue until stop() is called.
	 */
	@Override
	public void start() {
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		gameLoop.play();
	}
	/**
	 * Ends Gameloop
	 */
	public void stop() {
		if(gameLoop != null)
			gameLoop.stop();
		gameLoop = null;
	}

	@Override
	public void addWorld(GameWorld w) {
		// TODO Auto-generated method stub
		w.addGlobalVars(globalVars);
		madeWorlds.add(w);
	}
	
	/** 
	 * Sets which world will be displayed in Manager and be affected by the GameLoop.
	 * @param {String} s- Name of the world being set as current.
	 */
	@Override
	public void setCurrentWorld(String s) {
		// TODO Auto-generated method stub
		for(GameWorld w : madeWorlds) {
			if(w.isNamed(s)) {
				currentWorld = w;
				return;
			}
		}
		
		//If there is no named world, that's an error.
		System.out.println("Error Placeholder");
	}
	
	private void step() {
		currentWorld = currentWorld.getNextWorld();
		currentWorld.step();
		imageUpdate();
		playerManager.step();
	}
	
	@Override
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
		for(GameWorld w : madeWorlds) {
			w.setPlayerManager(playerManager);
		}
	}
	
	
	/**
	 * Passes image data to playermanager.
	 * Used in step.
	 */
	private void imageUpdate() {
		List<DisplayableImage> imageData = new ArrayList<>();
		double cameraXTranslate = 0;
		double cameraYTranslate = 0;
		for(GameObject o: currentWorld.getAllObjects()){
			imageData.add(o.getImage());
			if(o.getTags().contains("Player")) {		//TODO: make constant
				cameraXTranslate = o.getDouble(GameObject.X_COR);
				cameraYTranslate = o.getDouble(GameObject.Y_COR);
			}
		}
		Collections.sort(imageData);
		playerManager.setImageData(imageData, cameraXTranslate, cameraYTranslate);
	}

	@Override
	public void addBlueprints(GameObjectFactory f) {
		// TODO Auto-generated method stub
		blueprintManager = f;
	}
}
