package engine.utilities.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.GridManagers.SpriteObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.DefaultSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.DraggableGrid;
import authoring_UI.LayerDataConverter;
import authoring_UI.MapDataConverter;
import authoring_UI.SpriteDataConverter;
import engine.EngineController;
import engine.VoogaException;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class LocalGameDataHandler {
	private static final String DIRECTORY_PATH = "Directory Path";

	private static final XStream SERIALIZER = setupXStream();

	private static final String RESOURCES = "resources/";

	private static final String KNOWN_PROJECTS = RESOURCES + "KnownProjectNames.txt";
	private static final String ENGINE_PATH = "engine/";
	private static final String KNOWN_PROJECTS_PATH = RESOURCES + KNOWN_PROJECTS;
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String CONTROLLER_FILE_PATH = ENGINE_PATH + CONTROLLER_FILE;
	private static final String CONTINUE_FILE = "Engine_Controller_Load_File";
	private static final String CONTINUE_FILE_PATH = ENGINE_PATH + CONTINUE_FILE;

	private static final String AUTHORING_PATH = "authoring/";
	private static final String PROJECT_USER_SPRITE_PATH = AUTHORING_PATH + "Sprites/";
	private static final String PROJECT_WORLD_PATH = AUTHORING_PATH + "WorldsTEST";
	private static final String PROJECT_LAYER_PATH = AUTHORING_PATH + "LayersTEST";
	private static final String PROJECT_LAYER_SPRITE_PATH = AUTHORING_PATH + "SpritesTEST";
	private static final String DEFAULT_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "CustomSprites/";
	private static final String INVENTORY_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "InventorySprites/";
	private static final String DEFAULT_CATEGORY = "General/";

	private static final String LOCAL = "local";

	private static final String SELECTOR_TITLE = "Open Resource File";

	private Map<String, Image> cache = new HashMap<>();
	private String projectPath;
	private String projectName;

	private String root;

	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}

	public LocalGameDataHandler(Supplier<String> pathSupplier) {
		this(pathSupplier, "Test Project");
	}

	public LocalGameDataHandler(Supplier<String> pathSupplier, String projectName) {
		this.projectName = projectName;
		this.projectPath = getPath(pathSupplier) + projectName + "/";
		makeDirectories();
	}

	private Object getObjectFromFile(String filePath) {
		File controllerFile = new File(filePath);
		Scanner scanner;
		try {
			scanner = new Scanner(controllerFile);
		} catch (FileNotFoundException e) {
			throw new VoogaException(e);
		}
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return SERIALIZER.fromXML(fileContents);
	}

	private void saveToFile(Object object, String filePath) {
		String toSave = SERIALIZER.toXML(object);
		FileWriter writer;
		try {
			writer = new FileWriter(filePath);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			throw new VoogaException("SaveFail");
		}
	}

	private void makeDirectories() {
		String[] pathsToMake = new String[] { ENGINE_PATH, RESOURCES, PROJECT_WORLD_PATH, PROJECT_WORLD_PATH,
				PROJECT_LAYER_PATH, PROJECT_LAYER_SPRITE_PATH, DEFAULT_SPRITE_FOLDER, CUSTOM_SPRITE_FOLDER,
				INVENTORY_SPRITE_FOLDER };
		for (String s : pathsToMake)
			makeDirectory(projectPath + s);
		makeDirectory(root + RESOURCES);
	}

	private static void makeDirectory(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	private String getPath(Supplier<String> pathSupplier) {
		try {
			ResourceBundle local = ResourceBundle.getBundle(LOCAL);
			String s = local.getString(DIRECTORY_PATH);
			if (!new File(s).exists())
				throw new FileNotFoundException();
			return s;
		} catch (Exception e) {
			root = pathSupplier.get() + "\\";
			setDirectoryPath(root);
			makeDirectory(root + RESOURCES);
			return root;
		}
	}

	public void setDirectoryPath(String path) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(RESOURCES + LOCAL + ".properties");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		prop.put(DIRECTORY_PATH, path);
		try {
			FileOutputStream out = new FileOutputStream(RESOURCES + LOCAL + ".properties");
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
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
		try {
			addToKnown();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void addToKnown() throws IOException {
		List<String> known = knownProjects();
		if (!known.contains(projectName))
			known.add(projectName);
		FileWriter fileWriter = new FileWriter(root + KNOWN_PROJECTS);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (String s : known) {
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

	public List<String> knownProjects() {
		List<String> known = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(root + KNOWN_PROJECTS);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				known.add(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		return known;
	}

	public void saveForContinue(EngineController controller) {
		saveGame(controller, CONTINUE_FILE);
	}

	private void saveGame(EngineController controller, String gameName) {
		saveToFile(controller, projectPath + ENGINE_PATH + gameName);
	}

	/**
	 * @return The loaded EngineController from the project
	 * @throws FileNotFoundException
	 */
	public EngineController loadGame() {
		return loadGame(CONTROLLER_FILE);
	}

	public EngineController loadContinueGame() {
		try {
			return loadGame(CONTINUE_FILE);
		} catch (VoogaException e) {
			return loadGame();
		}
	}

	private EngineController loadGame(String saveGameName) {
		return (EngineController) getObjectFromFile(projectPath + ENGINE_PATH + saveGameName);
	}

	/**
	 * @param fileName
	 *            simple file name of file in the project directory
	 * @return The Image
	 * @throws URISyntaxException
	 */
	public Image getImage(String fileName) {
		if (cache.containsKey(fileName)) {
			return cache.get(fileName);
		}
		String path = new File(projectPath + RESOURCES + fileName).toURI().toString();
		Image i = new Image(path);
		cache.put(fileName, i);
		return i;
	}

	/**
	 * @param file
	 *            Adds the file to the project
	 * @throws IOException
	 */
	public void addFileToProject(File file) {
		if (file != null)
			try {
				Files.copy(file.toPath(), Paths.get(projectPath + RESOURCES + file.getName()),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new VoogaException(e);
			}
	}

	public static File chooseFile(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		return fileChooser.showOpenDialog(stage);
	}

	public static Image toImage(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			return new Image(fis);
		} catch (FileNotFoundException e) {
			return new Image("");
		}
	}

	/**
	 * @param stage
	 *            To present dialog
	 * @return File Chosen
	 */
	public File chooseFileForImageSave(Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		fileChooser.setInitialDirectory(new File(root + RESOURCES));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files (.png)", "*.png"),
				new ExtensionFilter("Image Files (.gif)", "*.gif"), new ExtensionFilter("Image Files (.jpg)", "*.jpg"));
		File newFile = fileChooser.showOpenDialog(window);
		return newFile;
	}

	public static File selectDirectory(Stage stage) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select Directory");
		return chooser.showDialog(stage);
	}

	public void saveSprite(AbstractSpriteObject SO, String path) {
		if (SO.getSavePath() == null) {
			SO.setSavePath(path);
		}
		saveToFile(new SpriteDataConverter(SO), path);
	}

	public void saveDefaultSprite(AbstractSpriteObject SO) throws IOException {
		saveSprite(SO, projectPath + DEFAULT_SPRITE_FOLDER + DEFAULT_CATEGORY + SO.getName());
	}

	public String makeValidFileName(String path) {
		path = path + "/";
		makeDirectory(path);
		int counter = 1;
		String pathWithInt = path + counter;
		File temp = new File(path);
		while (temp.exists()) {
			counter++;
			pathWithInt = path + counter;
			temp = new File(pathWithInt);
		}
		return path;
	}

	public void saveUserCreatedSprite(SpriteObject SO) {
		String newSpritePath = makeValidFileName(projectPath + CUSTOM_SPRITE_FOLDER + DEFAULT_CATEGORY + SO.getName());
		saveSprite(SO, newSpritePath);
	}

	public AbstractSpriteObject loadSprite(File spriteFile) {
		SpriteDataConverter SDC = (SpriteDataConverter) getObjectFromFile(spriteFile.getAbsolutePath());
		AbstractSpriteObject ret = SDC.createSprite();
		return ret;
	}

	private List<SpriteObjectGridManager> loadLayersFromDirectoryName(int worldNum) {
		List<SpriteObjectGridManager> loadedSOGMs = new ArrayList<SpriteObjectGridManager>();
		for (int i = 1; i < 5; i++) {
			SpriteObjectGridManager SOGM = loadLayerFromDirectory(getInitializingLayerDirectoryPath(worldNum, i), i,
					worldNum);
			if (SOGM != null) {
				loadedSOGMs.add(SOGM);
			}
		}
		return loadedSOGMs;
	}

	private SpriteObjectGridManager loadLayerFromDirectory(String initializingLayerDirectoryPath, int layerNum, int worldNum) {
		SpriteObjectGridManager temp = null;
		File directory = new File(initializingLayerDirectoryPath);
		File[] files = directory.listFiles();
		for (File f : files) {
			temp = loadLayer(f, layerNum, worldNum); //TODO
		}
		return temp;
	}

	public SpriteObjectGridManager loadLayer(File layerFile, int num, int worldNum) {
		LayerDataConverter SDC = (LayerDataConverter) getObjectFromFile(layerFile.getAbsolutePath());
		SpriteObjectGridManager ret = SDC.createLayer();

		List<AbstractSpriteObject> spritesToAdd = this
				.loadSpritesFromDirectoryName(this.getLayerSpritesDirectoryPath(worldNum, num));
		ret.storeSpriteObjectsToAdd(spritesToAdd);
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
	
	public void saveWorld(MapDataConverter MDC, String path) {
		saveToFile(MDC, path);
	}
	
	public void saveWorld(DraggableGrid DG, String path) {
		if (DG.getSavePath() == null || !path.equals(DG.getSavePath())) {
			path = this.makeValidFileName(path);
			DG.setSavePath(path);
		}
		MapDataConverter MDC = new MapDataConverter(DG);
		saveWorld(MDC, path);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	public void saveWorld(DraggableGrid DG) {
		worldCount++;
		makeWorldAndLayerAndSpriteDirectories(worldCount);
		List<SpriteObjectGridManager> SOGMList = DG.getGrids();
		int count = 0;
		int layerCount = 0;
		for (SpriteObjectGridManager SOGM : SOGMList) {
			layerCount++;
			for (AbstractSpriteObject SO : SOGM.getEntireListOfSpriteObjects()) {
				if(SO instanceof DefaultSpriteObject)
					continue;
				String path = getLayerSpritesDirectoryPath(worldCount, layerCount);
				path = this.makeValidFileName(path);
				saveSprite(SO, path);
				count++;
			}
			saveLayer(SOGM, worldCount, layerCount);
		}
		String worldPath = getWorldDirectoryPath(worldCount);
		saveWorld(DG, worldPath);
	}
	
	private void saveLayer(SpriteObjectGridManager SOGM, int worldCount, int num) throws Exception {
		LayerDataConverter LDC = new LayerDataConverter(SOGM);
		String path = makeValidFileName(getInitializingLayerDirectoryPath(worldCount, num));
		saveToFile(LDC, path);
	}
	
	private DraggableGrid loadWorld(int worldNum, File worldFile) {
		DraggableGrid ret = new DraggableGrid();
		List<SpriteObjectGridManager> addToWorld = loadLayersFromDirectoryName(worldNum);
		ret.loadLayers(addToWorld);
		return ret;
	}
	
	private List<DraggableGrid> loadWorldsFromDirectory(int worldNum, File directory) {
		List<DraggableGrid> worlds = new ArrayList<>();
		File[] files = directory.listFiles();
		for (File f : files) {
			if (!f.isDirectory()){
				DraggableGrid temp = loadWorld(worldNum, f);
				worlds.add(temp);
			}
		}
		return worlds;
	}
	
	private List<AbstractSpriteObject> loadSpritesFromDirectory(File directory) {
		File[] files = directory.listFiles();
		List<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		for (File f : files) {
				AbstractSpriteObject dummy = loadSprite(f);
				ret.add(dummy);
		}
		return ret;
	}
	
	//TODO WHY TEN??!!?! Why is this written like this?!
	public List<DraggableGrid> loadWorldsFromWorldDirectory(){
		List<DraggableGrid> DG_LIST = new ArrayList<DraggableGrid>();
		try{
			for (int worldNum = 1; worldNum < 10; worldNum++) {
				DG_LIST.add(loadWorldsFromDirectoryName(worldNum, this.getInitializingWorldDirectoryPath(worldNum)).get(0));
			}
		} catch (Exception e){
			// do nothing
		}
		return DG_LIST;
	}

	private List<AbstractSpriteObject> loadSpritesFromDirectoryName(String filePath) throws Exception {
		File directory = new File(filePath);
		return loadSpritesFromDirectory(directory);
	}

	public Map<String, List<AbstractSpriteObject>> loadSpritesFromNestedDirectories(String rootDirectory) {
		File file = new File(rootDirectory);
		System.out.println("GET CUSTOM DIRECTORY SPRITE PATH: " + rootDirectory);
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
	
	private void makeWorldAndLayerAndSpriteDirectories(int worldNum) {
		File file1 = new File(getInitializingWorldDirectoryPath(worldNum));
		File file2 = new File(getInitializingLayerDirectoryPath(worldNum, 1));
		File file3 = new File(getLayerSpritesDirectoryPath(worldNum, 1));
		if (! file1.exists()) {
			System.out.println("WORLDNUM : " + worldNum);
			makeDirectory(getInitializingWorldDirectoryPath(worldNum));
		}
		if (! file2.exists() && ! file3.exists()) {
			for (int i = 1; i < 5; i++) {
				makeDirectory(getInitializingLayerDirectoryPath(worldNum, i));
				makeDirectory(getLayerSpritesDirectoryPath(worldNum, i));

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
