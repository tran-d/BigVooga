package engine.utilities.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.AbstractSpriteObject;
import authoring.InventoryObject;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManager;
import authoring_UI.DraggableGrid;
import authoring_UI.LayerDataConverter;
import authoring_UI.MapDataConverter;
import authoring_UI.SpriteDataConverter;
import engine.EngineController;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Handles data for the project. When initialized, a projectName is given, which
 * constructs a directory in data for all data applicable to that project.
 * Images can be loaded in to this directory from other locations, and the
 * Engine can be serialized into the same directory.
 * 
 * @author Ian Eldridge-Allegra and other?
 */


public class GameDataHandler {
	private static final XStream SERIALIZER = setupXStream();
	private static final String KNOWN_PROJECTS = "KnownProjectNames";
	public static final String PATH = "data/UserCreatedGames/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String KNOWN_PROJECTS_PATH = "resources/" + KNOWN_PROJECTS + ".properties";
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String PROJECT_WORLD_PATH = "WorldsTEST";
	private static final String PROJECT_LAYER_PATH = "LayersTEST";
	private static final String PROJECT_LAYER_SPRITE_PATH = "SpritesTEST";
	private static final String DEFAULT_SPRITE_FOLDER = "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = "CustomSprites/";
	private final String INVENTORY_SPRITE_FOLDER = "InventorySprites/";
	private static final String DEFAULT_CATEGORY = "General/";
	private static final String RESOURCES = "resources/";
	private static  Path RESOURCES_PATH;
	private Map<String, Image> cache = new HashMap<>();
	private String projectPath;
	private String projectName;
	private int spriteCount = 0;

	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}
	
	public GameDataHandler() {
		this("Test Project");
	}
	
	public GameDataHandler(String projectName) {
		RESOURCES_PATH = Paths.get(RESOURCES).toAbsolutePath();
		this.projectName = projectName;
		this.projectPath = PATH + projectName + "/";
		makeDirectory(projectPath);
		makeSpriteDirectories();
		makeWorldAndLayerAndSpriteDirectories();
	}

	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * @param controller
	 *            to serialize
	 * @throws IOException
	 */
	public void saveGame(EngineController controller) throws IOException {
		String toSave = SERIALIZER.toXML(controller);

		FileWriter writer = new FileWriter(projectPath + CONTROLLER_FILE);
		writer.write(toSave);
		writer.close();
		addToKnownProjects();
	}

	/**
	 * Modified from
	 * https://stackoverflow.com/questions/22370051/how-to-write-values-in-a-properties-file-through-java-code
	 * Updates a specific properties file to include projectName in its keyset.
	 * 
	 * Adds the project name to a properties file of all file names.
	 * 
	 * @throws IOException
	 */
	private void addToKnownProjects() {
		Properties prop = new Properties();

		try {
			FileInputStream in = new FileInputStream(KNOWN_PROJECTS_PATH);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		prop.put(projectName, "Modified " + LocalDateTime.now());

		try {
			FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS_PATH);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("KNOWN PROJECTS NOT FOUND");// TODO
																	// improve
																	// this
		}
	}

	/**
	 * @return A map of all project names to the modified date.
	 */
	public static Map<String, String> knownProjectsWithDateModified() {
		Map<String, String> result = new HashMap<>();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(KNOWN_PROJECTS);
			Enumeration<String> projects = bundle.getKeys();
			while (projects.hasMoreElements()) {
				String p = projects.nextElement();
				result.put(p, bundle.getString(p));
			}
		} catch (MissingResourceException e) {
			// Intentionally Blank
		}
		return result;
	}

	/**
	 * @return The loaded EngineController from the project
	 * @throws FileNotFoundException
	 */
	public EngineController loadGame() throws FileNotFoundException {
		System.out.println("Trying to load game");
		File controllerFile = new File(projectPath + CONTROLLER_FILE);
		Scanner scanner = new Scanner(controllerFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return (EngineController) SERIALIZER.fromXML(fileContents);
	}

	/**
	 * @param fileName
	 *            simple file name of file in the project directory
	 * @return The Image
	 * @throws URISyntaxException
	 */
	public Image getImage(String fileName) throws URISyntaxException {
		if (cache.containsKey(fileName)){
			return cache.get(fileName);
		}
		String path = new File(fileName).toURI().toString();
		Image i = new Image(path);
		cache.put(fileName, i);
		return i;
	}

	/**
	 * @param stage
	 *            To present the dialog
	 * @return The chosen File that was added to the project
	 * @throws IOException
	 */
	public File addChosenFileToProject(Stage stage) throws IOException {
		File file = chooseFile(stage);
		addFileToProject(file);
		return file;
	}

	/**
	 * @param file
	 *            Adds the file to the project
	 * @throws IOException
	 */
	public void addFileToProject(File file) throws IOException {
		if (file != null)
			Files.copy(file.toPath(), Paths.get(projectPath + file.getName()), StandardCopyOption.REPLACE_EXISTING);
	}

	// See javadocs for FileChooser -- this code is modified from that source
	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}

	private void makeProjectDirectory() {
		if (!directoryExists(projectPath)) {
			makeDirectory(projectPath);
		}
	}

	/**
	 * @param stage
	 *            To present dialog
	 * @return File Chosen
	 */
	public static File chooseFileForImageSave(Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files (.png)", "*.png"));
//		File f = new File("/");
//		Path p = Paths.get(f.getName());
//		System.out.println("p: "+p);
		System.out.println("Respath: "+RESOURCES_PATH);
		File newFile = fileChooser.showOpenDialog(window);
//		File newFile = fileChooser.showSaveDialog(window);
//		fileChooser.
		
		try {
			addImageFileToResources(newFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newFile;
		
	}
	
	public static void addImageFileToResources(File file) throws IOException {
		if (file != null){
//			Path newPath = Paths.get(RESOURCES_PATH);
			System.out.println("newPath: "+RESOURCES_PATH);
//			Files.
//			BufferedImage BI = new BufferedImage()
//			Files.createFile(newPath, file);
//			Files.
//			FileWriter FW = new FileWriter(file);
//			FW.write(file);
//			 FW.close();
			Path p = Paths.get(RESOURCES_PATH.toString(), file.getName());
			System.out.println("Path p: "+p);
		
			System.out.println();
			Files.copy(file.toPath(), p, StandardCopyOption.REPLACE_EXISTING);
	}

	}
	private static void makeDirectory(String path) {
		File file = new File(path);
		if (!file.exists()){
		file.mkdirs();
		}
	}

	private boolean directoryExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	public void saveSprite(AbstractSpriteObject SO, String path) throws Exception {
		if (SO.getSavePath()==null) { //  || !path.equals(SO.getSavePath())) {
			//path = this.makeValidFileName(path); // PATH WILL NEVER BE null.
			SO.setSavePath(path);
		}
		SpriteDataConverter SDC = new SpriteDataConverter(SO);
		saveSprite(SDC, path);	
	}

	private void saveSprite(SpriteDataConverter SO, String path) throws IOException {
		//TODO: make category folder
		System.out.println("SAVE SPRITE to: " + path);
		String toSave = SERIALIZER.toXML(SO);
		FileWriter writer = new FileWriter(path);
		writer.write(toSave);
		writer.close();
	}

	public void saveDefaultSprite(AbstractSpriteObject SO) throws Exception {
		// SpriteDataConverter SDC = new SpriteDataConverter(SO);
		String BasicPath = getDefaultSpriteDirectoryPath() + getDefaultCategory() + SO.getName();
		// if (!BasicPath.equals(SO.getSavePath())){
		// BasicPath = makeValidFileName(BasicPath);
		// }
		saveSprite(SO, BasicPath);
	}

	private String getDefaultCategory() {
		return DEFAULT_CATEGORY;
	}

	public String makeValidFileName(String path) { 
		path = path + "/";
		if (!directoryExists(path)) {
			System.out.println("MAKE DIRECTORY FIRST TIME");
			makeDirectory(path);
		}
		int counter = 1;
		String previousIntAdded = Integer.toString(counter);
		path = path + previousIntAdded;
		File temp = new File(path);
		while (temp.exists()) {
				path = path.substring(0, path.lastIndexOf(previousIntAdded)); // undo
				counter++;
				previousIntAdded = Integer.toString(counter);
				path = path + previousIntAdded;
				temp = new File(path);
		}
		return path;
		//int ind = path.lastIndexOf(File.pathSeparator);
//		System.out.println("PATH: " + path);
//		System.out.println(ind);
//		if (ind > 0) {
//			System.out.println("GOES THROUGH IF STATEMENT");
//			String parent = path.substring(0, ind + 1);
//			if (!directoryExists(parent)) {
//				System.out.println("MAKE DIRECTORY NOW");
//				makeDirectory(parent);
//			}
//		}
//		int counter = 1;
//		String previousIntAdded = Integer.toString(counter);
//		path = path + previousIntAdded;
//		while (directoryExists(path)) {
//			int undoIndex = path.lastIndexOf(previousIntAdded);
//			path = path.substring(0, undoIndex); // undo. 
//			counter++;
//			path = path + Integer.toString(counter);
//		}
//		return path;
//		ARCHANAS VERSION: 
//		while (true) {
//			if (!directoryExists(path)) {
//				System.out.println("MAKE DIRECTORY");
//				makeDirectory(path);
//				break;
//			}
//			else {
//				path = path.substring(0, path.lastIndexOf(previousIntAdded)); // undo
//				counter++;
//				previousIntAdded = Integer.toString(counter);
//				path = path + previousIntAdded;
//			}
//		}
	}

	public String getProjectPath() {
		return this.projectPath;
	}

	public void saveUserCreatedSprite(SpriteObject SO) throws Exception {
		String newSpritePath = makeValidFileName(
				getCustomSpriteDirectoryPath() + this.getDefaultCategory() + SO.getName());
		saveSprite(SO, newSpritePath);
	}

	public AbstractSpriteObject loadSprite(File spriteFile) throws Exception {
		if (!isValidFile(spriteFile)){
			throw new Exception("Invalid file to load");
		}
		Scanner scanner = new Scanner(spriteFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
//		System.out.println("FileContents: "+fileContents);
		scanner.close();
//		Uncomment next two lines if proxy doesnt work
		SpriteDataConverter SDC = (SpriteDataConverter) SERIALIZER.fromXML(fileContents);
		AbstractSpriteObject ret = SDC.createSprite();
		System.out.println("File: "+spriteFile);
		return ret;
	}
	
	public SpriteObjectGridManager loadLayer(File layerFile) throws Exception {
		if (!isValidFile(layerFile)){
			throw new Exception("Invalid file to load");
		}
		Scanner scanner = new Scanner(layerFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		LayerDataConverter SDC = (LayerDataConverter) SERIALIZER.fromXML(fileContents);
		SpriteObjectGridManager ret = SDC.createLayer();
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

	private boolean isValidDirectory(File directory) {
		return directory.isDirectory() && !directory.getName().startsWith(".");
	}
	
	private boolean isValidFile(File in){
		return in.exists()&& !in.getName().startsWith(".");
	}

	public String getDefaultSpriteDirectoryPath() {
		String ret = projectPath + PROJECT_USER_SPRITE_PATH + DEFAULT_SPRITE_FOLDER;
		// System.out.println("default path: "+ret);
		return ret;
	}
	
	public String getInventorySpriteDirectoryPath() {
		String ret = projectPath + PROJECT_USER_SPRITE_PATH + INVENTORY_SPRITE_FOLDER;
		// System.out.println("default path: "+ret);
		return ret;
	}

	public String getCustomSpriteDirectoryPath() {
		String ret = projectPath + PROJECT_USER_SPRITE_PATH + CUSTOM_SPRITE_FOLDER;
		return ret;
	}
	
	public String getInitializingWorldDirectoryPath() {
		String ret = projectPath + PROJECT_WORLD_PATH + "/";
		return ret;
	}
	
	public String getInitializingLayerDirectoryPath(int num) {
		String ret = this.getInitializingWorldDirectoryPath() + PROJECT_LAYER_PATH + num + "/";
		return ret;
	}
	
	public String getWorldDirectoryPath() {
		String ret = projectPath + PROJECT_WORLD_PATH; // TODO 
		return ret;
	}
	
	public String getLayerDirectoryPath(int num) {
		String ret = this.getInitializingWorldDirectoryPath() + PROJECT_LAYER_PATH + num + "/";
		return ret;
	}
	
	public String getLayerSpritesDirectoryPath(int num) {
		String ret = this.getInitializingLayerDirectoryPath(num) + PROJECT_LAYER_SPRITE_PATH;
		return ret;
	}
	
	public void saveWorld(MapDataConverter MDC, String path) throws Exception {
		 String toSave = SERIALIZER.toXML(MDC);
		 FileWriter writer = new FileWriter(path);
		 writer.write(toSave);
		 writer.close();
	}
	
	public void saveWorld(DraggableGrid DG, String path) throws Exception { // didn't check for null path
		if (DG.getSavePath() == null || ! path.equals(DG.getSavePath())) {
			path = this.makeValidFileName(path);
			DG.setSavePath(path);
		}
		MapDataConverter MDC = new MapDataConverter(DG);
		saveWorld(MDC, path);
	}
	
	public void saveWorld(DraggableGrid DG) throws Exception { // called by MainAuthoringGUI
		List<SpriteObjectGridManager> SOGMList = DG.getGrids();
		int count = 0;
		int layerCount = 0;
		for (SpriteObjectGridManager SOGM : SOGMList) {
			layerCount++;
			List<SpriteObject> spriteObjects = SOGM.getActiveSpriteObjects();
			System.out.println("SIZE OF SOGM " + spriteObjects.size());
			for (SpriteObject SO : spriteObjects) {
				String path = this.getLayerSpritesDirectoryPath(layerCount);
				path = this.makeValidFileName(path);
				saveSprite(SO, path);
				count++;
			}
			saveLayer(SOGM, layerCount);
			System.out.println(SOGM.getName() + " count: " + count);
		}
		System.out.println("ULTIMATE COUNT of sprite objects: " + count);
		String worldPath = this.getWorldDirectoryPath();
		saveWorld(DG, worldPath);
	}
	
	private void saveLayer(SpriteObjectGridManager SOGM, int num) throws Exception {
		LayerDataConverter LDC = new LayerDataConverter(SOGM);
		String path = this.makeValidFileName(getInitializingLayerDirectoryPath(num));
		saveLayer(LDC, path);
	}
	
	private void saveLayer(LayerDataConverter LDC, String path) throws Exception {
		 String toSave = SERIALIZER.toXML(LDC);
		 System.out.println("LAYER PATH: " + path);
		 FileWriter writer = new FileWriter(path);
		 writer.write(toSave);
		 writer.close();
	}
	
	private DraggableGrid loadWorld(File worldFile) throws Exception {
		if (!isValidFile(worldFile)){
			throw new Exception("Invalid file to load");
		}
		Scanner scanner = new Scanner(worldFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		MapDataConverter MDC = (MapDataConverter) SERIALIZER.fromXML(fileContents);
		DraggableGrid ret = MDC.createMap(); // TODO this will not totally work
		return ret;
	}
	
	private List<DraggableGrid> loadWorldsFromDirectory(File directory) throws Exception { // TODO doesn't need parameter bc GDH is unique for every game?
		List<DraggableGrid> worlds = new ArrayList<>();
		if (!isValidDirectory(directory)) {
			throw new Exception("Not a directory");
		}
		File[] files = directory.listFiles();
		for (File f : files) {
			
			try {
				DraggableGrid temp = loadWorld(f);
				worlds.add(temp);

			} catch (Exception e) {
			}
		}
		return worlds;
	}

	private List<AbstractSpriteObject> loadSpritesFromDirectory(File directory) throws Exception {
		System.out.println("Directory in loadSpritesFromDirectory: " + directory);
		if (!isValidDirectory(directory)) {
			throw new Exception("Not a directory");
		}
		File[] files = directory.listFiles();
		List<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		for (File f : files) {
			try {
				AbstractSpriteObject dummy = loadSprite(f);
				ret.add(dummy);

			} catch (Exception e) {
//				e.printStackTrace();
				// do nothing
			}
		}
		return ret;
	}
	
	public List<DraggableGrid> loadWorldsFromWorldDirectory(){
		List<DraggableGrid> DG_LIST = new ArrayList<DraggableGrid>();
		try{
			DG_LIST = loadWorldsFromDirectoryName(this.getInitializingWorldDirectoryPath());
		} catch (Exception e){
			DG_LIST = new ArrayList<DraggableGrid>();
		}
		return DG_LIST;
	}

	public List<DraggableGrid> loadWorldsFromDirectoryName(String filePath) throws Exception {
		File directory = new File(filePath);
		return loadWorldsFromDirectory(directory);
	}

	private List<AbstractSpriteObject> loadSpritesFromDirectoryName(String filePath) throws Exception {
		File directory = new File(filePath);
		return loadSpritesFromDirectory(directory);
	}

	public Map<String, List<AbstractSpriteObject>> loadSpritesFromNestedDirectories(String rootDirectory) {
		File file = new File(rootDirectory);
		if (!isValidDirectory(file)) {
			return null;
		}
		System.out.println("Still going, root directory: "+rootDirectory);
		Map<String, List<AbstractSpriteObject>> ret = new HashMap<String, List<AbstractSpriteObject>>();
		File[] files = file.listFiles();
		for (File f : files) {
			if (isValidDirectory(f)) {
				try {
					List<AbstractSpriteObject> val = loadSpritesFromDirectory(f);
					String name = f.getName();
					ret.put(name, val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	private void makeWorldAndLayerAndSpriteDirectories() {
		File file1 = new File(getInitializingWorldDirectoryPath());
		File file2 = new File(getInitializingLayerDirectoryPath(1));
		File file3 = new File(getLayerSpritesDirectoryPath(1));
		if (! file1.exists()) {
			makeDirectory(getInitializingWorldDirectoryPath());
		}
		if (! file2.exists()) {
			for (int i = 1; i < 4; i++) {
				makeDirectory(getInitializingLayerDirectoryPath(i));
				makeDirectory(getInitializingLayerDirectoryPath(i));
				makeDirectory(getInitializingLayerDirectoryPath(i));
			}
		}
		if (! file3.exists()) {
			for (int i = 1; i < 4; i++) {
				makeDirectory(getLayerSpritesDirectoryPath(1));
				makeDirectory(getLayerSpritesDirectoryPath(2));
				makeDirectory(getLayerSpritesDirectoryPath(3));
			}
		}
	}

	private void makeSpriteDirectories() {
		List<String> pathsToMake = new ArrayList<String>();
		pathsToMake.add(getDefaultSpriteDirectoryPath());
		pathsToMake.add(getCustomSpriteDirectoryPath());
		pathsToMake.add(getInventorySpriteDirectoryPath());
		for (String s : pathsToMake) {
			File file = new File(s);
			if (!file.exists()) {
				makeDirectory(s);
			}
		}
	}
}