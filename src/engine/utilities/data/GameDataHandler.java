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
//	public static final String IMAGE_PATH = "resources/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String DEFAULT_SPRITE_PATH = "data/DefaultSprites/";
	private static final String ALL_SPRITE_PATH = "data/AllSprites/";
	private String projectPath;
	
	public GameDataHandler(String projectName) {
		this.projectPath = PATH+projectName + "/";
		makeDirectory(projectPath);
		File file = new File(DEFAULT_SPRITE_PATH);
		if (!file.exists()){
			makeDirectory(DEFAULT_SPRITE_PATH);
		}
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
		String path = new File(projectPath+CONTROLLER_FILE).toURI().toString();
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
	
	private void saveSprite(SpriteObject SO, String path) throws IOException {
//		File dir = new File(path);
//		if (!dir.exists()){
//			makeDirectory(path);
//		}
		String toSave = SERIALIZER.toXML(SO);
		FileWriter writer = new FileWriter(path);
        writer.write(toSave);
        writer.close();
	}
	
	public void saveDefaultSprite(SpriteObject SO) throws IOException{
		String path = DEFAULT_SPRITE_PATH + SO.getName();
		saveSprite(SO, path);
	}
	
	public void saveUserCreatedSprite(SpriteObject SO) throws IOException{
		String allSpritePath = ALL_SPRITE_PATH + SO.getName();
		String projectSpritePath = projectPath+PROJECT_USER_SPRITE_PATH + SO.getName();
		saveSprite(SO, allSpritePath);
		saveSprite(SO, projectSpritePath);
	}
	
	public SpriteObject loadSprite(File spriteFile) throws FileNotFoundException {
		Scanner scanner = new Scanner(spriteFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		SpriteObject ret = (SpriteObject)SERIALIZER.fromXML(fileContents);
		return ret;
	}
	
	public File chooseSpriteFile(Stage stage) throws FileNotFoundException {
		FileChooser imageChooser = new FileChooser();
		File startDirectory = new File(ALL_SPRITE_PATH);
		if (!startDirectory.exists()){
			makeDirectory(ALL_SPRITE_PATH);
		}
		imageChooser.setInitialDirectory(startDirectory);
		imageChooser.setTitle("Choose Sprite");
		File file = imageChooser.showOpenDialog(stage);
		return file;
	}
}
