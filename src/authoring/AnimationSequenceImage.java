package authoring;

import authoring_UI.AuthoringImageView;
import javafx.scene.layout.StackPane;

public class AnimationSequenceImage {
	
	private AuthoringImageView myImage;

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
