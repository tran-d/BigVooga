package engine;

import javafx.application.Application;
import javafx.stage.Stage;

public class BackendTester extends Application {

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		EngineController game = new GameMaster(20);

		World testWorld = new GameWorld();

		GameObject testObject = new GenericObject();

		testWorld.addGameObject(testObject);
		game.addWorld(testWorld);
		game.setCurrentWorld("world");
		
		System.out.println("Initializing game");
		game.start();

	}

}
