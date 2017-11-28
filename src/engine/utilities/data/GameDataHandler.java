package engine.utilities.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import authoring.SpriteObject;
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
	// public static final String IMAGE_PATH = "resources/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String DEFAULT_SPRITE_FOLDER = "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = "CustomSprites/";
	private String projectPath;

	public GameDataHandler(String projectName) {
		this.projectPath = PATH + projectName + "/";
		makeProjectDirectory();
		makeSpriteDirectories();
	}

	public void saveGame(EngineController controller) throws IOException {
		String toSave = SERIALIZER.toXML(controller);
		FileWriter writer = new FileWriter(projectPath + CONTROLLER_FILE);
		writer.write(toSave);
		writer.close();
	}

	public EngineController loadGame() throws FileNotFoundException {
		File controllerFile = new File(projectPath + CONTROLLER_FILE);
		Scanner scanner = new Scanner(controllerFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return (EngineController) SERIALIZER.fromXML(fileContents);
	}

	public Image getImage(String fileName) throws URISyntaxException {
		String path = new File(projectPath + CONTROLLER_FILE).toURI().toString();
		return new Image(path);
	}

	public File addChosenFileToProject(Stage stage) throws IOException {
		File file = chooseFile(stage);
		addFileToProject(file);
		return file;
	}

	public void addFileToProject(File file) throws IOException {
		if (file != null)
			Files.copy(file.toPath(), Paths.get(projectPath + file.getName()), StandardCopyOption.REPLACE_EXISTING);
	}

	// See javadocs for FileChooser -- this code is modified from that source
	public File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}
	
	private void makeProjectDirectory(){
		if (directoryExists(projectPath)) {
			makeDirectory(projectPath);
		}
	}

	private static void makeDirectory(String path) {
		File file = new File(path);
		file.mkdirs();
	}
	
	private boolean directoryExists(String path){
		File file = new File(path);
		return file.exists();
	}

	private void saveSprite(SpriteObject SO, String path) throws IOException {
		String toSave = SERIALIZER.toXML(SO);
		FileWriter writer = new FileWriter(path);
		writer.write(toSave);
		writer.close();
	}

	public void saveDefaultSprite(SpriteObject SO) throws IOException {
		String path = makeValidFileName(getDefaultSpriteDirectoryPath() + SO.getName());
		saveSprite(SO, path);
	}
	
	public String makeValidFileName(String path){
		int counter = 1;
		while (directoryExists(path)){
			path = path+Integer.toString(counter);
			counter++;
		}
		return path;
	}

	public void saveUserCreatedSprite(SpriteObject SO) throws IOException {
		String newSpritePath = makeValidFileName(getCustomSpriteDirectoryPath()+SO.getName());
		saveSprite(SO, newSpritePath);
	}

	public SpriteObject loadSprite(File spriteFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(spriteFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		SpriteObject ret = (SpriteObject) SERIALIZER.fromXML(fileContents);
		return ret;
	}

	public File chooseSpriteFile(Stage stage) throws FileNotFoundException {
		FileChooser imageChooser = new FileChooser();
		File startDirectory = new File(getCustomSpriteDirectoryPath());
		imageChooser.setInitialDirectory(startDirectory);
		imageChooser.setTitle("Choose Sprite");
		File file = imageChooser.showOpenDialog(stage);
		return file;
	}

	private String getDefaultSpriteDirectoryPath() {
		return projectPath + PROJECT_USER_SPRITE_PATH + DEFAULT_SPRITE_FOLDER;
	}

	private String getCustomSpriteDirectoryPath() {
		return projectPath + PROJECT_USER_SPRITE_PATH + CUSTOM_SPRITE_FOLDER;
	}

	private void makeSpriteDirectories() {
		ArrayList<String> pathsToMake = new ArrayList<String>();
		pathsToMake.add(getDefaultSpriteDirectoryPath());
		pathsToMake.add(getCustomSpriteDirectoryPath());

		for (String s : pathsToMake) {
			File file = new File(s);
			if (!file.exists()) {
				makeDirectory(s);
			}
		}
	}
}
