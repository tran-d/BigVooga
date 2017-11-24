package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import player.PlayerManager;

public class GameMaster implements EngineController{
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DELAY = 1000/DEFAULT_FPS;
	
	private World currentWorld;
	private List<World> madeWorlds;
	private Timeline gameLoop;
	private VariableContainer globalVars;
	private PlayerManager playerManager;
	
	public GameMaster(PlayerManager playerManager) {
		// TODO Auto-generated constructor stub
		madeWorlds = new ArrayList<World>();
		
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(DEFAULT_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		globalVars = new GlobalVariables();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		gameLoop.play();
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
		madeWorlds.add(w);
		
	}
	
	@Override
	public void setCurrentWorld(String s) {
		// TODO Auto-generated method stub
		for(World w: madeWorlds)
		{
			if(w.isNamed(s)) {
				currentWorld = w;
				return;
			}
		}
		
		//If there is no named world, that's an error.
		System.out.println("Error Placeholder");
	}
	
	private void step() {
		
		currentWorld = ((GameWorld)currentWorld).getNextWorld();
		
		currentWorld.step();
		imageUpdate();
		
	}
	
	@Override
	public void setPlayerManager(PlayerManager currentPlayerManager) {
		playerManager = currentPlayerManager;
	}
	
	
	/**
	 * Passes image data to playermanager.
	 * Used in step.
	 */
	private void imageUpdate()
	{
		List<BoundedImage> imageData = new ArrayList<BoundedImage>();
		for(GameObject o: ((GameWorld)currentWorld).getAllObjects()){
			imageData.add(o.getImage());
		}
		playerManager.getImageData(imageData);
	}
}
