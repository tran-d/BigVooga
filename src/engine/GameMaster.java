package engine;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameMaster implements EngineController{
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_DELAY = 1000/DEFAULT_FPS;
	
	private World currentWorld;
	private List<World> madeWorlds;
	private Timeline gameLoop;
	private VariableContainer varContainer;

	public GameMaster() {
		this(DEFAULT_DELAY);
	}
	
	public GameMaster(int MILLIS_DELAY) {
		// TODO Auto-generated constructor stub
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLIS_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		
		varContainer = new VariableContainer();
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

		gameLoop.play();
	}

	@Override
	public void addListener(Runnable listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addWorld(World w) {
		// TODO Auto-generated method stub
		w.addGlobalVars(varContainer);
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
	private void step()
	{
		currentWorld.step();
	}

}
