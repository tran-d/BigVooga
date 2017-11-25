package engine.utilities.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import authoring.SpriteObject;
import engine.EngineController;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Ian Eldridge-Allegra
 */
public class GameDataHandler {
	private static final XStream SERIALIZER = new XStream(new DomDriver());
	private static final String KNOWN_PROJECTS = "resources/KnownProjectNames.properties";
	public static final String PATH = "data/UserCreatedGames/";
//	public static final String IMAGE_PATH = "resources/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String DEFAULT_SPRITE_PATH = "data/DefaultSprites/";
	private static final String ALL_SPRITE_PATH = "data/AllSprites/";
	private String projectPath;
	private String projectName;
	
	public GameDataHandler(String projectName) {
		this.projectName = projectName;
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
        addToKnownProjects();
	}
	
	/**
	 * Modified from
	 * https://stackoverflow.com/questions/22370051/how-to-write-values-in-a-properties-file-through-java-code
	 * Updates a specific properties file to include projectName in its keyset.
	 * @throws IOException 
	 */
	private void addToKnownProjects() throws IOException {
		Properties prop = new Properties();
		FileInputStream in = new FileInputStream(KNOWN_PROJECTS);
		prop.load(in);
		in.close();
		prop.put(projectName, "Modified " + LocalDateTime.now());
		FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS);
		prop.store(out, null);
		out.close();
	}
	
	public Map<String, String> knownProjectsWithDateModified(){
		Map<String, String> result = new HashMap<>();
		ResourceBundle bundle = ResourceBundle.getBundle(KNOWN_PROJECTS);
		Enumeration<String> projects = bundle.getKeys();
		while(projects.hasMoreElements()) {
			String p = projects.nextElement();
			result.put(p, bundle.getString(p));
		}
		return result;
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
	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}
	
	public static File chooseFileForImageSave(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files (.png)","*.png"));
		return fileChooser.showSaveDialog(stage);
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
		return (SpriteObject)SERIALIZER.fromXML(fileContents);
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
