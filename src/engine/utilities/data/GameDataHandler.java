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

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.SpriteObject;
import engine.EngineController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Ian Eldridge-Allegra
 */
public class GameDataHandler {
	private static final XStream SERIALIZER = setupXStream();	
	private static final String KNOWN_PROJECTS = "KnownProjectNames";
	public static final String PATH = "data/UserCreatedGames/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String KNOWN_PROJECTS_PATH = "resources/"+KNOWN_PROJECTS+".properties";
	private Map<String, Image> cache = new HashMap<>();
	
	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] {
				Point2D.class
		});
		xstream.allowTypesByWildcard(new String[] {
		    "engine.**", "java.**"
		});
		return xstream;
	}
	
	private String projectPath;
	private String projectName;
	
	public GameDataHandler(String projectName) {
		this.projectName = projectName;
		this.projectPath = PATH+projectName + "/";
		makeDirectory(projectPath);
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
		FileInputStream in = new FileInputStream(KNOWN_PROJECTS_PATH);
		prop.load(in);
		in.close();
		prop.put(projectName, "Modified " + LocalDateTime.now());
		FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS_PATH);
		prop.store(out, null);
		out.close();
	}
	
	public static Map<String, String> knownProjectsWithDateModified(){
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
		File controllerFile = new File(projectPath + CONTROLLER_FILE);
		Scanner scanner = new Scanner(controllerFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return (EngineController) SERIALIZER.fromXML(fileContents);
	}

	public Image getImage(String fileName) throws URISyntaxException {

		if(cache.containsKey(fileName))
			return cache.get(fileName);
		String path = new File(projectPath + fileName).toURI().toString();
		Image i = new Image(path);
		cache.put(fileName, i);
		return i;

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

	
	//See javadocs for FileChooser -- this code is modified from that source
	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}
	
	private void makeProjectDirectory(){
		if (!directoryExists(projectPath)) {
			makeDirectory(projectPath);
		}
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
		return null;
//		return projectPath + PROJECT_USER_SPRITE_PATH + DEFAULT_SPRITE_FOLDER;
	}

	private String getCustomSpriteDirectoryPath() {
		return null;
//		return projectPath + PROJECT_USER_SPRITE_PATH + CUSTOM_SPRITE_FOLDER;
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
