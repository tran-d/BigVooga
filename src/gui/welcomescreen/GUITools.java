package gui.welcomescreen;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Samarth
 *
 */
public class GUITools {

	public GUITools() {
		
	}
	
	public static Image createImage(String path, int width, int height) {
		Image image = new Image(WelcomeScreen.class.getClassLoader().getResourceAsStream(path), width, height, true, true);
		return image;
	}
	
	public static ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		return imageView;
	}
	
	public static Label generateLabel(String labelText, String font, String color, String size) {
		Label label = new Label(labelText);
		label.setStyle(styleLabel(font, color, size));
		return label;
	}
	
	public static String styleLabel(String font, String color, String size) {
		return "-fx-font-family: " + font +
				"-fx-text-fill: " + color +  
				"-fx-font-size: " + size  
				;
	}
	
	/**
	 * Sets border color
	 * @param color
	 * @return
	 */
	public static String styleBox(String color) {
		return "-fx-border-style: solid inside;" + 
               "-fx-border-width: 2;" + 
               "-fx-border-radius: 5;" + 
               "-fx-border-color: " + color + ";";
	}
	
}
