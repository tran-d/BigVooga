package authoring.Sprite.AnimationSequences;

import engine.sprite.BoundedImage;
import javafx.scene.layout.StackPane;

public class AnimationSequenceImage {
	
	private AuthoringImageView myImage;
	private BoundedImage boundedImage;

	AnimationSequenceImage(AuthoringImageView AEI){
		myImage = AEI;
	}
	
	AnimationSequenceImage(AnimationSequenceImage ASE){
		myImage = new AuthoringImageView(ASE.getImage()); 
	}
	
	public AuthoringImageView getImage(){
		return myImage;
	}
	
//	public StackPane getThumbnail(){
//		StackPane SP = new StackPane();
//		SP.getChildren().add(myImage);
//		return SP;
//	}
}
