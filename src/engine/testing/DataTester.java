package engine.testing;

import java.util.function.Supplier;

import engine.GameMaster;
import engine.utilities.data.GameDataHandler;
import engine.utilities.data.LocalGameDataHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DataTester extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Supplier<String> sup = ()->LocalGameDataHandler.selectDirectory(stage).getPath();
		LocalGameDataHandler g = new LocalGameDataHandler(sup, "Test Project");
		g.addFileToProject(GameDataHandler.chooseFile(stage));
		g.saveGame(new GameMaster());
		System.out.println(g.knownProjects());
		Pane p = new Pane();
		p.getChildren().add(new ImageView(g.getImage("TestImage2.png")));
		stage.setScene(new Scene(p));
		stage.show();
	}

}
