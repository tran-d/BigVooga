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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.DialogSprite.DialogSequence;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.DefaultSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.DraggableGrid;
import authoring_UI.LayerDataConverter;
import authoring_UI.MapDataConverter;
import authoring_UI.SpriteDataConverter;
import authoring_UI.SpriteGridHandler;
import engine.EngineController;
import engine.VoogaException;
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
	private static final String CONTINUE_FILE = "Engine_Controller_Load_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
	private static final String KNOWN_PROJECTS_PATH = "resources/" + KNOWN_PROJECTS + ".properties";
	private static final String PROJECT_USER_SPRITE_PATH = "Sprites/";
	private static final String PROJECT_WORLD_PATH = "Worlds/";
	private static final String PROJECT_LAYER_PATH = "Layer";
	private static final String PROJECT_LAYER_SPRITE_PATH = "Sprites/";
	private static final String DEFAULT_SPRITE_FOLDER = "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = "CustomSprites/";
	private final String INVENTORY_SPRITE_FOLDER = "InventorySprites/";
	private final String IMPORTED_SPRITE_FOLDER = "ImportedSprites/";
	private static final String DEFAULT_CATEGORY = "General/";
	private static final String RESOURCES = "resources/";
	private static final String CONTROLLER_DIRECTORY = "SAVES/";
	private static final String DELIMITER = ", ";
	public static final String RES_PATH = "resources/";
	private Map<String, Image> cache = new HashMap<>();
	private String projectPath;
	private String projectName;
	private int worldCount = 0;

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
		this.projectName = projectName;
		this.projectPath = PATH + projectName + "/";
		makeDirectory(projectPath + CONTROLLER_DIRECTORY);
		makeSpriteDirectories();
		// makeWorldAndLayerAndSpriteDirectories();
	}

	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param controller
	 *            to serialize
	 * @throws IOException
	 */
	public void saveGame(EngineController controller) {
		saveGame(controller, CONTROLLER_FILE);
		clearKnown();
	}

	public void saveForContinue(EngineController controller) {
		saveGame(controller, CONTINUE_FILE);
	}

	private void saveGame(EngineController controller, String gameName) {
		String toSave = SERIALIZER.toXML(controller);
		FileWriter writer;
		try {
			writer = new FileWriter(projectPath + CONTROLLER_DIRECTORY + gameName);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			throw new VoogaException("SaveFail");
		}
		addToKnownProjects(gameName);
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
	private void addToKnownProjects(String saveName) {
		Properties prop = getProperties();

		if (knownProjects().containsKey(projectName)) {
			if (knownProjects().get(projectName).contains(saveName))
				return;
			try {
				FileInputStream in = new FileInputStream(KNOWN_PROJECTS_PATH);
				prop.load(in);
				in.close();
			} catch (IOException e) {
				// Intentionally Blank
			}
			prop.put(projectName, prop.get(projectName) + DELIMITER + saveName);
		} else
			prop.put(projectName, saveName);

		try {
			FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS_PATH);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("KNOWN PROJECTS NOT FOUND");
		}
	}

	private Properties getProperties() {
		Properties prop = new Properties();

		try {
			FileInputStream in = new FileInputStream(KNOWN_PROJECTS_PATH);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		return prop;
	}

	private void clearKnown() {
		Properties prop = getProperties();

		prop.put(projectName, ",");

		try {
			FileOutputStream out = new FileOutputStream(KNOWN_PROJECTS_PATH);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("KNOWN PROJECTS NOT FOUND");
		}
	}

	public static Map<String, List<String>> knownProjects() {
		Map<String, List<String>> result = new HashMap<>();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(KNOWN_PROJECTS);
			Enumeration<String> projects = bundle.getKeys();
			while (projects.hasMoreElements()) {
				String p = projects.nextElement();
				List<String> saves = new ArrayList<>(Arrays.asList(bundle.getString(p).split(DELIMITER)));
				for (int i = saves.size() - 1; i >= 0; i--) {
					if (saves.get(i).matches("\\s*"))
						saves.remove(i);
				}
				result.put(p, saves);
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
		return loadGame(CONTROLLER_FILE);
	}

	public EngineController loadContinueGame() throws FileNotFoundException {
		try {
			return loadGame(CONTINUE_FILE);
		} catch (FileNotFoundException e) {
			return loadGame();
		}
	}

	private EngineController loadGame(String saveGameName) throws FileNotFoundException {
		File controllerFile = new File(projectPath + CONTROLLER_DIRECTORY + saveGameName);
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
		if (cache.containsKey(fileName)) {
			return cache.get(fileName);
		}
		String path = new File(projectPath + fileName).toURI().toString();
		Image i = new Image(path);
		cache.put(fileName, i);
		return i;
	}

	// public Image imageExists(String fileName) throws URISyntaxException {
	// File image = new File(projectPath + fileName);
	// if (!image.exists()){
	//
	// }
	// Image i = new Image(path);
	// cache.put(fileName, i);
	// return i;
	// }

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
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files (.png)", "*.png"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files (.gif)", "*.gif"));
		return fileChooser.showOpenDialog(stage);
	}

	public static Image chooseImage(Window window) {
		File f = chooseFileForImageSave(window);
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			return new Image(fis);
		} catch (FileNotFoundException e) {
			return new Image("pikachu.png");
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
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files (.gif)", "*.gif"));
		File newFile = fileChooser.showOpenDialog(window);

		return newFile;

	}

	public static String getImageURIAndCopyToResources(File file) {
		try {
			Files.copy(file.toPath(), Paths.get(RES_PATH + file.getName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String URI = file.toURI().toString();

		return URI;
	}

	private static void makeDirectory(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private boolean directoryExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	private void removeDirectory(String path) {

		File f = new File(path);
		if (f.exists() && f.listFiles().length > 0) {
			for (File child : f.listFiles()) {
				removeDirectory(child.toURI().toString());
			}
		}
		f.delete();
	}
	
	public void saveSprite(AbstractSpriteObject SO, String categoryPath, String name) throws Exception{
		if (!directoryExists(categoryPath)){
			makeDirectory(categoryPath);
		}
		saveSprite(SO, categoryPath+name);
	}

	public void saveSprite(AbstractSpriteObject SO, String path) throws Exception {
		if (SO.getSavePath() == null) { // || !path.equals(SO.getSavePath())) {
			// path = this.makeValidFileName(path); // PATH WILL NEVER BE null.
			SO.setSavePath(path);
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////// check
		///////////////////////////////////////////////////////////////////////////////////////////////////// this
		///////////////////////////////////////////////////////////////////////////////////////////////////// out
		//
		// if (SO instanceof SpriteObject){
		// SpriteObject sprite = (SpriteObject) SO;
		// System.out.println("class of sprite: "+sprite.getClass());
		// ObjectOutputStream oos = new ObjectOutputStream(new
		// FileOutputStream(path));
		// oos.writeObject(sprite);
		// oos.close();
		// } else if(SO instanceof InventoryObject){
		// InventoryObject inventory = (InventoryObject) SO;
		// ObjectOutputStream oos = new ObjectOutputStream(new
		// FileOutputStream(path));
		// oos.writeObject(inventory);
		// oos.close();
		// } else {
		// throw new Exception("Not a valid Sprite class to serialize");
		// }

		// String toSave = SERIALIZER.toXML(SO);
		// FileWriter writer = new FileWriter(path);
		// writer.write(toSave);
		// writer.close();

		// If proxy fails uncomment next two lines
		SpriteDataConverter SDC = new SpriteDataConverter(SO);
		saveSprite(SDC, path);
	}
	


	private void saveSprite(SpriteDataConverter SO, String path) throws IOException {
		// TODO: make category folder
		System.out.println("SAVE SPRITE to: " + path);
		long heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("heapSize: " + heapSize);
		String toSave = SERIALIZER.toXML(SO);
		heapSize = Runtime.getRuntime().totalMemory();
		System.out.println("heapSize: " + heapSize);
		FileWriter writer = new FileWriter(path);
		writer.write(toSave);
		/////////////////////////////////////////////////////////////////////////////////////////////////// may
		/////////////////////////////////////////////////////////////////////////////////////////////////// need
		/////////////////////////////////////////////////////////////////////////////////////////////////// to
		/////////////////////////////////////////////////////////////////////////////////////////////////// write
		/////////////////////////////////////////////////////////////////////////////////////////////////// to
		/////////////////////////////////////////////////////////////////////////////////////////////////// a
		/////////////////////////////////////////////////////////////////////////////////////////////////// new
		/////////////////////////////////////////////////////////////////////////////////////////////////// file
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
	}

	public String getProjectPath() {
		return this.projectPath;
	}

	public void saveUserCreatedSprite(SpriteObject SO) throws Exception {
		String newSpritePath = makeValidFileName(
				getCustomSpriteDirectoryPath() + this.getDefaultCategory() + SO.getName());
		saveSprite(SO, newSpritePath);
	}

	public AbstractSpriteObject loadSprite(File spriteFile) throws FileNotFoundException {
		if (!isValidFile(spriteFile)) {
			throw new VoogaException("Invalid file to load");
		}
		Scanner scanner = new Scanner(spriteFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		SpriteDataConverter SDC = (SpriteDataConverter) SERIALIZER.fromXML(fileContents);
		AbstractSpriteObject ret = SDC.createSprite();
		return ret;
	}

	public DialogSequence loadDialogue(File dFile) throws FileNotFoundException {
		if (!isValidFile(dFile)) {
			throw new VoogaException("Invalid file to load");
		}
		Scanner scanner = new Scanner(dFile);
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		DialogSequence ret = (DialogSequence) SERIALIZER.fromXML(fileContents);
		return ret;
	}

	private List<SpriteObjectGridManager> loadLayersFromDirectoryName(File worldFile) {
		List<SpriteObjectGridManager> loadedSOGMs = new ArrayList<SpriteObjectGridManager>();
		try {
			for (File f : worldFile.listFiles()) {
				SpriteObjectGridManager SOGM = this.loadLayer(f);
				if (SOGM != null) {
					loadedSOGMs.add(SOGM);
				}
			}
		} catch (Exception e) {

		}
		return loadedSOGMs;
	}

	// private SpriteObjectGridManager loadLayerFromDirectory(File
	// initializingLayerDirectoryFile) {
	// SpriteObjectGridManager temp = null;
	// if (!isValidDirectory(initializingLayerDirectoryFile)) {
	// try {
	// throw new Exception("Not a directory");
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// System.out.println("NAME NAME " +
	// initializingLayerDirectoryFile.getName());
	// File[] files = initializingLayerDirectoryFile.listFiles();
	// for (File f : files) {
	// if (!f.isDirectory()){
	// System.out.println("here is the layer info: " + f.getPath());
	// try {
	// temp = loadLayer(f); //THROWS AN ERROR
	// System.out.println("ADDED A LAYER AFTER DE-SERIALIZAING");
	// } catch (Exception e) {
	// e.printStackTrace();
	// System.out.println("HMM WEIRD");
	// }
	// }
	// }
	// return temp;
	// }

	public SpriteObjectGridManager loadLayer(File layerFile) throws Exception {

		if (!isValidFile(layerFile)) {
			System.out.println(layerFile + " BAD BAD BAD ");
			throw new Exception("Invalid file to load");
		}
		List<AbstractSpriteObject> spritesToAdd = new ArrayList<AbstractSpriteObject>();
		LayerDataConverter SDC = null;
		SpriteObjectGridManager ret = null;
		for (File f : layerFile.listFiles()) {
			if (!f.isDirectory()) {
				Scanner scanner = new Scanner(f);
				String fileContents = scanner.useDelimiter("\\Z").next();
				scanner.close();
				SDC = (LayerDataConverter) SERIALIZER.fromXML(fileContents);
				ret = SDC.createLayer();
			} else {
				spritesToAdd.addAll(loadSpritesFromDirectory(f));
			}
		}

		System.out.println("ADDED " + spritesToAdd.size() + " NUMBER OF SPRITES TO THE GRID.. MAYBE");
		ret.storeSpriteObjectsToAdd(spritesToAdd);
		// ret.setSpriteGridHandler(new SpriteGridHandler(1, new
		// DraggableGrid())); // random draggable grid
		// ret.createMapLayer(spritesToAdd);
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

	private boolean isValidFile(File in) {
		return in.exists() && !in.getName().startsWith(".");
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

	public String getInitializingWorldDirectoryPath(String worldName) {
		String ret = projectPath + PROJECT_WORLD_PATH + worldName + "/";
		return ret;
	}

	public String getInitializingLayerDirectoryPath(String worldName, int num) {
		String ret = this.getInitializingWorldDirectoryPath(worldName) + PROJECT_LAYER_PATH + num + "/";
		return ret;
	}

	public String getWorldDirectoryPath(int worldCount) {
		String ret = projectPath + PROJECT_WORLD_PATH + worldCount + "/"; // TODO
		return ret;
	}

	public String getLayerDirectoryPath(String worldName, int num) {
		String ret = this.getInitializingWorldDirectoryPath(worldName) + PROJECT_LAYER_PATH + num + "/";
		return ret;
	}

	public String getLayerSpritesDirectoryPath(String worldName, int layerNum) {
		String ret = this.getInitializingLayerDirectoryPath(worldName, layerNum) + PROJECT_LAYER_SPRITE_PATH;
		return ret;
	}

	public void saveWorld(MapDataConverter MDC, String path) throws Exception {

		String toSave = SERIALIZER.toXML(MDC);
		FileWriter writer = new FileWriter(path);
		writer.write(toSave);
		writer.close();
	}

	// public void saveWorld(DraggableGrid DG, String path) throws Exception {
	// // didn't check for null path
	// if (DG.getSavePath() == null || ! path.equals(DG.getSavePath())) {
	// path = this.makeValidFileName(path);
	// DG.setSavePath(path);
	// }
	//
	// MapDataConverter MDC = new MapDataConverter(DG);
	// saveWorld(MDC, path);
	// }

	private void removeExistingSave() {
		this.removeDirectory(projectPath + PROJECT_WORLD_PATH);
	}

	/**
	 * Entry point to save a world to data. Requires an authoring DraggableGrid
	 * (i.e., single map)
	 * 
	 * @param worldDraggableGrid
	 *            - the DraggableGrid (Map) from the authoring view to save
	 * @throws Exception
	 * @author Archana, Samuel
	 */
	public void saveWorlds(List<DraggableGrid> worldDraggableGrids) { // called
																		// by
																		// MainAuthoringGUI
		// worldCount++;
		String worldPath = projectPath + PROJECT_WORLD_PATH;
		removeExistingSave();
		makeDirectory(worldPath);
		worldDraggableGrids.forEach(world -> {
			String savePath = worldPath + world.getName();
			MapDataConverter MDC = new MapDataConverter(world);
			try {
				saveWorld(MDC, savePath);
			} catch (Exception e) {

			}
		});

		// makeWorldAndLayerAndSpriteDirectories(worldDraggableGrid);
		// List<SpriteObjectGridManager> SOGMList =
		// worldDraggableGrid.getGrids();
		// int count = 0;
		// int layerCount = 0;
		// for (SpriteObjectGridManager SOGM : SOGMList) {
		// layerCount++;
		// List<AbstractSpriteObject> spriteObjects =
		// SOGM.getEntireListOfSpriteObjects().stream()
		// .filter(e -> !(e instanceof
		// DefaultSpriteObject)).collect(Collectors.toList());
		// System.out.println("SIZE OF SOGM " + spriteObjects.size());
		// for (AbstractSpriteObject SO : spriteObjects) {
		// String path =
		// this.getLayerSpritesDirectoryPath(worldDraggableGrid.getName(),
		// layerCount);
		// path = this.makeValidFileName(path);
		// saveSprite(SO, path);
		// count++;
		// }
		// saveLayer(SOGM, worldDraggableGrid.getName(), layerCount);
		// System.out.println(SOGM.getName() + " count: " + count);
		// }
		// System.out.println("ULTIMATE COUNT of sprite objects: " + count);
	}

	/**
	 * Saves an individual layer of a world from Authoring by converting it to a
	 * serilizable object then saving it in the right place
	 * 
	 * @param SOGM
	 *            - A grid/layer to save
	 * @param worldName
	 *            - the worldname from which this layer comes
	 * @param num
	 *            - the number of the layer in the world
	 * @throws Exception
	 * @author Archana, Samuel
	 */
	private void saveLayer(SpriteObjectGridManager SOGM, String worldName, int num) throws Exception {
		LayerDataConverter LDC = new LayerDataConverter(SOGM);
		String path = getInitializingLayerDirectoryPath(worldName, num);
		saveLayer(LDC, path);
	}

	/**
	 * Saves a serializable version of a layer to data
	 * 
	 * @param LDC
	 *            - serializable layer to save
	 * @param path
	 *            - location to save
	 * @throws Exception
	 */
	private void saveLayer(LayerDataConverter LDC, String path) throws Exception {
		String toSave = SERIALIZER.toXML(LDC);
		System.out.println("LAYER PATH: " + path);
		FileWriter writer = new FileWriter(path);
		writer.write(toSave);
		writer.close();
	}

	private DraggableGrid loadWorld(File worldFile) throws Exception {
		System.out.println("just checking worldFile here: " + worldFile.getName());
		if (!isValidFile(worldFile)) {
			throw new Exception("Invalid file to load");
		}

		DraggableGrid ret = new DraggableGrid();
		ret.setName(worldFile.getName());
		// for (File f: worldFile.listFiles()){
		// if (!f.isDirectory()){
		// Scanner scanner = new Scanner(f);
		// String fileContents = scanner.useDelimiter("\\Z").next();
		// scanner.close();
		//
		// }
		// }

		// MapDataConverter MDC = (MapDataConverter)
		// SERIALIZER.fromXML(fileContents);
		// DraggableGrid ret = MDC.createMap();

		List<SpriteObjectGridManager> addToWorld = this.loadLayersFromDirectoryName(worldFile);
		System.out.println("SIZE OF SOGMS FOR EACH DG SHOULD BE 4 : " + addToWorld.size());

		ret.loadLayers(addToWorld);
		System.out.println("FINISHED CREATING WORLD");
		return ret;
	}

	private List<DraggableGrid> loadWorldsFromDirectory(File directory) throws Exception {
		List<DraggableGrid> worlds = new ArrayList<>();

		System.out.println("WORLD DIRECTORY SHOULD BE: " + directory.getName());
		if (!isValidDirectory(directory)) {
			System.out.println("not a directory");
			throw new Exception("Not a directory");
		}
		File[] files = directory.listFiles(); // goes through all world
												// directories
		for (File f : files) {
			// if (!f.isDirectory()){
			try {
				DraggableGrid temp = loadWorld(f);
				worlds.add(temp);
				System.out.println("ADDED A WORLD AFTER DE-SERIALIZAING");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }
		}
		System.out.println("number of draggable grids returned: " + worlds.size());
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
				System.out.println(dummy);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * Entry point to load worlds from data
	 * 
	 * @return List<DraggableGrids>, i.e., the Authoring worlds
	 * @author Archana, Samuel
	 */
	public List<DraggableGrid> loadWorldsFromWorldDirectory() {
		List<DraggableGrid> DG_LIST = new ArrayList<DraggableGrid>();
		String worldDirectory = projectPath + PROJECT_WORLD_PATH;

		File worldDirFile = new File(worldDirectory);

		if (worldDirFile.exists()) {
			System.out.println("World directory: " + worldDirFile.toString());
			for (File f : worldDirFile.listFiles()) {
				System.out.println("File : " + f.toString());
				try {
					Scanner scanner = new Scanner(f);
					String fileContents = scanner.useDelimiter("\\Z").next();
					scanner.close();
					MapDataConverter MDC = (MapDataConverter) SERIALIZER.fromXML(fileContents);
					DraggableGrid DG = MDC.createDraggableGrid();
					DG_LIST.add(DG);
				} catch (Exception e) {
					e.printStackTrace();
					// do nothing
				}
			}
		}

		System.out.println("all the draggable grids we return " + DG_LIST.size());
		return DG_LIST;
	}

	// public List<DraggableGrid> loadWorldsFromDirectoryName(String filePath)
	// throws Exception {
	// System.out.println("LOAD WORLDS FROM DIRECTORY NAME " + filePath);
	//
	// File directory = new File(filePath);
	//// System.out.println("loading from world number " + worldName);
	// return loadWorldsFromDirectory(directory);
	// }

	// private List<AbstractSpriteObject> loadSpritesFromDirectoryName(File
	// spriteDirectory) throws Exception {
	//// File directory = new File(filePath);
	// return loadSpritesFromDirectory(spriteDirectory);
	// }

	public Map<String, List<AbstractSpriteObject>> loadSpritesFromNestedDirectories(String rootDirectory) {
		File file = new File(rootDirectory);
		System.out.println("GET CUSTOM DIRECTORY SPRITE PATH: " + rootDirectory);
		if (!isValidDirectory(file)) {
			return null;
		}
		System.out.println("Still going, root directory: " + rootDirectory);
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

	private void makeWorldAndLayerAndSpriteDirectories(DraggableGrid DG) {
		// File file1 = new File(getInitializingWorldDirectoryPath(worldName));
		// File file2 = new File(getInitializingLayerDirectoryPath(worldName,
		// i));
		// File file3 = new File(getLayerSpritesDirectoryPath(worldName, i));
		// if (! file1.exists()) {
		String worldName = DG.getName();
		System.out.println("WORLDNAME : " + worldName);
		makeDirectory(getInitializingWorldDirectoryPath(worldName));
		// }
		// if (! file2.exists() && ! file3.exists()) {
		for (int i = 1; i <= DG.getGrids().size(); i++) {
			makeDirectory(getInitializingLayerDirectoryPath(worldName, i));
			makeDirectory(getLayerSpritesDirectoryPath(worldName, i));

		}
		// }
	}

	private void makeSpriteDirectories() {
		List<String> pathsToMake = new ArrayList<String>();
		pathsToMake.add(getDefaultSpriteDirectoryPath());
		pathsToMake.add(getCustomSpriteDirectoryPath());
		pathsToMake.add(getInventorySpriteDirectoryPath());
//		pathsToMake.add(getImportedSpriteDirectoryPath());
		for (String s : pathsToMake) {
			File file = new File(s);
			if (!file.exists()) {
				makeDirectory(s);
			}
		}
	}

	public List<DialogSequence> loadDialogsFromDirectory(String folderToLoad) {
		// TODO FILL THIS IN!
		List<DialogSequence> ret = new ArrayList<DialogSequence>();
		File file = new File(folderToLoad);
		if (file.listFiles() !=null) {
			File[] files = file.listFiles();
			 ret = new ArrayList<DialogSequence>();
			for (File f : files) {
				try {
					ret.add(loadDialogue(f));
				} catch (FileNotFoundException e) {
					throw (new VoogaException(e));
				}
			}
		}
		return ret;
	}

	public void saveDialogSequence(DialogSequence dS, String folderToSaveTo) {
		// TODO FILL THIS IN!
		String toSave = SERIALIZER.toXML(dS);
		FileWriter writer;
		try {
			writer = new FileWriter(folderToSaveTo);
			writer.write(toSave);
			/////////////////////////////////////////////////////////////////////////////////////////////////// may
			/////////////////////////////////////////////////////////////////////////////////////////////////// need
			/////////////////////////////////////////////////////////////////////////////////////////////////// to
			/////////////////////////////////////////////////////////////////////////////////////////////////// write
			/////////////////////////////////////////////////////////////////////////////////////////////////// to
			/////////////////////////////////////////////////////////////////////////////////////////////////// a
			/////////////////////////////////////////////////////////////////////////////////////////////////// new
			/////////////////////////////////////////////////////////////////////////////////////////////////// file
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new VoogaException(e);
		}

	}

}
