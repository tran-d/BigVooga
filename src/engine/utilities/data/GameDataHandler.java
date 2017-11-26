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
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String SELECTOR_TITLE = "Open Resource File";
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
}
