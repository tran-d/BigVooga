package authoring.Sprite.AnimationSequences;

import java.io.File;

import engine.sprite.BoundedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuthoringImageView extends ImageView{
	
	private String imagePath;
	private BoundedImage boundedImage;
	
	public AuthoringImageView(String path, Image im){
		super(im);
//		File f = new File(path);
		imagePath = path;
	}
	
	public AuthoringImageView(String path, Image im, BoundedImage bImage){
		this(path, im);
		boundedImage = bImage;
	}
	
	public AuthoringImageView(String path){
//		System.out.println("AIV path: "+path);
//		File f = new File(path);
		Image im = GDH.getImage(path);
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
