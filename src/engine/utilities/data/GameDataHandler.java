package engine.utilities.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import engine.EngineController;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Ian Eldridge-Allegra
 */
public class GameDataHandler {
	private static final XStream SERIALIZER = new XStream(new DomDriver());
	public static final String PATH = "data/UserCreatedGames/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private String projectPath;
	
	public GameDataHandler(String projectName) {
		this.projectPath = PATH+projectName + "/";
		makeDirectory(projectPath);
	}
	
	public void saveGame(EngineController controller) throws IOException {
		String toSave = SERIALIZER.toXML(controller);
		FileWriter writer = new FileWriter(projectPath+CONTROLLER_FILE);
        writer.write(toSave);
        writer.close();
	}
	
	public EngineController loadGame() throws FileNotFoundException {
		File controllerFile = new File(projectPath+CONTROLLER_FILE);
		Scanner scanner = new Scanner(controllerFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return (EngineController)SERIALIZER.fromXML(fileContents);
	}
	
	public Image getImage(String fileName) throws URISyntaxException {
		String path = new File(projectPath+CONTROLLER_FILE).toURI().toString(); //TODO clean this up
		return new Image(path);
	}
	
	public File addChosenFileToProject(Stage stage) throws IOException {
		File file = chooseFile(stage);
		addFileToProject(file);
		return file;
	}
	
	public void addFileToProject(File file) throws IOException {
		if(file != null)
			Files.copy(file.toPath(), Paths.get(projectPath+file.getName()), StandardCopyOption.REPLACE_EXISTING);
	}
	
	//See javadocs for FileChooser -- this code is modified from that source
	public File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}
	
	private static void makeDirectory(String path) {
		File file = new File(path);
		file.mkdirs();
	}
}
