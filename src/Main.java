import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import engine.GameMaster;
import engine.utilities.data.GameDataHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		testData(stage);
	}

	private void testData(Stage stage) throws IOException, FileNotFoundException, URISyntaxException {
		GameDataHandler data = new GameDataHandler("SaverTest3");
		data.addChosenFileToProject(stage);
		data.saveGame(new GameMaster(50));
		data.loadGame();
		data.getImage("HexGrid.PNG");
	}
}
