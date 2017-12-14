package authoring.Sprite.AnimationSequences;

import engine.sprite.BoundedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuthoringImageView extends ImageView{
	
	private String imagePath;
	private BoundedImage boundedImage;
	
	public AuthoringImageView(String path, Image im){
		super(im);
		imagePath = path;
	}
	
	public AuthoringImageView(String path, Image im, BoundedImage bImage){
		super(im);
		imagePath = path;
		boundedImage = bImage;
	}
	
	public AuthoringImageView(String path){
		System.out.println("AIV path: "+path);
		Image im = new Image(path);
		imagePath = path;
		setImage(im);
	}
	
	public AuthoringImageView(String path, BoundedImage bImage){
		this(path);
		boundedImage = bImage;
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
	
	public void setBoundedImage(BoundedImage BI){
		boundedImage = BI;
	}
	
	public BoundedImage getBoundedImage(){
		return boundedImage;
	}
	
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new SerializableAuthoringImageView(this);
	}

}
