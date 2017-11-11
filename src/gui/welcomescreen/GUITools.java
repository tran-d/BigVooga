package gui.welcomescreen;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
