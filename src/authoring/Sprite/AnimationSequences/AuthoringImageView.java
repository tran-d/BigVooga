package authoring.Sprite.AnimationSequences;

import authoring_data.SerializableAuthoringImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuthoringImageView extends ImageView{
	
	private String imagePath;
	
	public AuthoringImageView(String path, Image im){
		super(im);
		imagePath = path;
	}
	
	public AuthoringImageView(String path){
		Image im = new Image(path);
		imagePath = path;
		setImage(im);
	}
	
	public AuthoringImageView(AuthoringImageView image) {
		imagePath = new String(image.getImagePath());
		setImage(image.getImage());
	}

	public AuthoringImageView(Image image) {
		// TODO Auto-generated constructor stub
	}

	public String getImagePath(){
		return imagePath;
	}
	
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new SerializableAuthoringImageView(this);
	}

}
