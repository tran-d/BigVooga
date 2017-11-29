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
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String DEFAULT_SPRITE_FOLDER = "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = "CustomSprites/";
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
		makeSpriteDirectories();
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
		FileInputStream in = new FileInputStream("resources/"+KNOWN_PROJECTS+".properties");
		prop.load(in);
		in.close();
		prop.put(projectName, "Modified " + LocalDateTime.now());
		FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS);
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
	
	public void saveSpriteToDirectory(SpriteObject SO, String directory) throws Exception{
		if (!directoryExists(directory)){
			makeDirectory(directory);
		}
		String spriteName = directory+SO.getName();
		
		try{
			saveSprite(SO, spriteName);
		} catch (Exception e){
			throw new Exception("Could not save sprite");
		}
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
	
	public String getProjectPath(){
		return this.projectPath;
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
	
	private boolean isValidDirectory(File directory){
		return directory.isDirectory()&&!directory.getName().startsWith(".");
	}

	public String getDefaultSpriteDirectoryPath() {
		String ret = projectPath + PROJECT_USER_SPRITE_PATH + DEFAULT_SPRITE_FOLDER;
//		System.out.println("default path: "+ret);
		return ret;
	}

	public String getCustomSpriteDirectoryPath() {
		String ret = projectPath + PROJECT_USER_SPRITE_PATH + CUSTOM_SPRITE_FOLDER;
//		System.out.println("custom path: "+ret);
		return ret;
	}
	
	private ArrayList<SpriteObject> loadSpritesFromDirectory(File directory) throws Exception{
		System.out.println("Directory in loadSpritesFromDirectory: "+ directory);
		if (!isValidDirectory(directory)) {
			throw new Exception("Not a directory");
		}
		File[] files = directory.listFiles();
		ArrayList<SpriteObject> ret = new ArrayList<SpriteObject>();
		for (File f : files) {
			try {
				SpriteObject dummy = loadSprite(f);
				ret.add(dummy);

			} catch (Exception e) {
				// do nothing
			}
		}
		return ret;
	}
	
	private ArrayList<SpriteObject> loadSpritesFromDirectoryName(String filePath) throws Exception {
		File directory = new File(filePath);
		return loadSpritesFromDirectory(directory);
	}
	
	public Map<String, ArrayList<SpriteObject>> loadSpritesFromNestedDirectories(String rootDirectory) {
		File file  = new File(rootDirectory);
		if (!isValidDirectory(file)){
			return null;
		}
		System.out.println("Still going");
		Map<String, ArrayList<SpriteObject>> ret = new HashMap<String, ArrayList<SpriteObject>>();
		
		File[] files = file.listFiles();
		for (File f: files){
			try{
				ArrayList<SpriteObject> val = loadSpritesFromDirectory(f);
				String name = f.getName();
				ret.put(name, val);
			} catch (Exception e){
				e.printStackTrace();
				//Do nothing
			}
			
		}
		return ret;		
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
