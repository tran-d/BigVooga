package authoring_data;

import authoring_UI.AuthoringImageView;
import javafx.scene.image.Image;

public class SerializableAuthoringImageView {
	
	private String imagePath;
	
	
	public SerializableAuthoringImageView(AuthoringImageView AIV){
		imagePath = AIV.getImagePath();
	}
	
	private Object readResolve() throws java.io.ObjectStreamException{
        return new AuthoringImageView(imagePath, new Image(imagePath));  
}

}
