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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import engine.EngineController;
import engine.VoogaException;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

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

	private void makeDirectories() {
		String[] pathsToMake = new String[] { ENGINE_PATH, RESOURCES, PROJECT_WORLD_PATH, PROJECT_WORLD_PATH,
				PROJECT_LAYER_PATH, PROJECT_LAYER_SPRITE_PATH, DEFAULT_SPRITE_FOLDER, CUSTOM_SPRITE_FOLDER,
				INVENTORY_SPRITE_FOLDER };
		for (String s : pathsToMake) {
			File file = new File(s);
			if (!file.exists()) {
				makeDirectory(s);
			}
		}
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
			root = pathSupplier.get();
			setDirectoryPath(root);
			return root;
		}
	}

	public void setDirectoryPath(String path) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(LOCAL);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		prop.put(DIRECTORY_PATH, path);
		try {
			FileOutputStream out = new FileOutputStream(LOCAL);
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("KNOWN PROJECTS NOT FOUND");
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
		known.add(projectName);
		FileWriter fileWriter = new FileWriter(projectPath + KNOWN_PROJECTS);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (String s : known) {
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

	private List<String> knownProjects() {
		List<String> known = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(projectPath + KNOWN_PROJECTS);
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
		String toSave = SERIALIZER.toXML(controller);
		FileWriter writer;
		try {
			writer = new FileWriter(projectPath + ENGINE_PATH + gameName);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			throw new VoogaException("SaveFail");
		}
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
		File controllerFile = new File(projectPath + ENGINE_PATH + saveGameName);
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

	/**
	 * @param file
	 *            Adds the file to the project
	 * @throws IOException
	 */
	public void addFileToProject(File file) throws IOException {
		if (file != null)
			Files.copy(file.toPath(), Paths.get(projectPath + RESOURCES + file.getName()),
					StandardCopyOption.REPLACE_EXISTING);
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
		fileChooser.setInitialDirectory(new File(root+RESOURCES));
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
}
