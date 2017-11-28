package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import engine.sprite.BoundedImage;
import engine.sprite.DisplayableImage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import player.PlayerManager;

/**
 * 
 * @author Nikolas Bramblett, ...
 *
 */
public class GameMaster implements EngineController{
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DELAY = 1000/DEFAULT_FPS;
	
	private World currentWorld;
	private List<World> madeWorlds;
	private Timeline gameLoop;
	private GlobalVariables globalVars;
	private PlayerManager playerManager;
	
	public GameMaster() {
		// TODO Auto-generated constructor stub
		madeWorlds = new ArrayList<World>();
		
		
		globalVars = new GlobalVariables();
	}

	@Override
	public void start() {
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		gameLoop.play();
	}
	public void stop() {
		if(gameLoop != null)
			gameLoop.stop();
		gameLoop = null;
	}

	//Not sure we really need this
	@Override
	public void addListener(Runnable listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWorld(World w) {
		// TODO Auto-generated method stub
		w.addGlobalVars(globalVars);
		w.setPlayerManager(playerManager);
		madeWorlds.add(w);
	}
	
	@Override
	public void setCurrentWorld(String s) {
		// TODO Auto-generated method stub
		for(World w : madeWorlds) {
			if(w.isNamed(s)) {
				currentWorld = w;
				return;
			}
		}
		
		//If there is no named world, that's an error.
		System.out.println("Error Placeholder");
	}
	
	private void step() {
		currentWorld = ((GameLayer)currentWorld).getNextWorld();
		currentWorld.step();
		imageUpdate();
		playerManager.step();
	}
	
	@Override
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
		for(World w : madeWorlds)
			w.setPlayerManager(playerManager);
	}
	
	
	/**
	 * Passes image data to playermanager.
	 * Used in step.
	 */
	private void imageUpdate() {
		List<DisplayableImage> imageData = new ArrayList<DisplayableImage>();
		for(GameObject o: currentWorld.getAllObjects()){
			imageData.add(o.getImage());
		}
		Collections.sort(imageData);
		
		playerManager.setImageData(imageData);
	}
}
