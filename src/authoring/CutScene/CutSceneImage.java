package authoring.CutScene;

import java.util.function.Supplier;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CutSceneImage extends ImageView{

	private DialogSequence myDS;
	
	CutSceneImage(DialogSprite DS, Supplier<DialogSequence> myDialogSeq){
		super(DS.getImageFileURL());
		myDS = myDialogSeq.get();
	}
	
	public DialogSequence getDialogSequence(){
		return myDS;
	}
	
	
}
