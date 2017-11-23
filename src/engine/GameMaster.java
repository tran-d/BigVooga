package engine;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameMaster implements EngineController{
	
	private World currentWorld;
	private List<World> madeWorlds;
	private Timeline gameLoop;
	private GlobalVariables varContainer;

	public GameMaster(int MILLIS_DELAY) {
		// TODO Auto-generated constructor stub
		madeWorlds = new ArrayList<World>();
		
		gameLoop = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLIS_DELAY), e -> step());
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
		
		varContainer = new GlobalVariables();
		
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
