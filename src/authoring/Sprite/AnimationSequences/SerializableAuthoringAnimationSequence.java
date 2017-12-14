package authoring.Sprite.AnimationSequences;

import java.util.ArrayList;

import engine.sprite.BoundedImage;
import javafx.scene.image.Image;

public class SerializableAuthoringAnimationSequence {

	
	private String myName;
	private ArrayList<AnimationSequenceImage> myImages;
	
	
	
	public SerializableAuthoringAnimationSequence(AuthoringAnimationSequence AAS){
		;
		myName = AAS.getName();
		myImages = AAS.getImages();
	}
	
	private Object readResolve() throws java.io.ObjectStreamException{
		;
        return new AuthoringAnimationSequence(myName, myImages); 
}
}
