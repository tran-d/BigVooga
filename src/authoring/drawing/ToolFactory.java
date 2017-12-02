package authoring.drawing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;

public class ToolFactory {

	public static List<DrawingTool> getTools(ImageCanvas imageCanvas, ResourceBundle bundle) {
		Enumeration<String> toolNames = bundle.getKeys();
		List<DrawingTool> instances = new ArrayList<>();
		while (toolNames.hasMoreElements()) {
			String tool = toolNames.nextElement();
			try {
				DrawingTool dTool = (DrawingTool) Class.forName(tool).getDeclaredConstructor(String.class, ImageCanvas.class)
						.newInstance(bundle.getString(tool), imageCanvas);
				dTool.setIcon(getString(dTool.toString()));
				instances.add(dTool);
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				e.printStackTrace();
				throw new PaintException("drawingTools.properties File is Misformatted");
			}
		}
		return instances;
	}

	private static Image getString(String name) {
		try {
			return new Image(ToolFactory.class.getResourceAsStream(name));
		}catch(Exception e) {
			//Intentionally not thrown -- returns null if image doesn't exist
			return null;
		}
	}

}
