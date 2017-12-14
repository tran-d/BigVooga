package authoring.DialogSprite;

import java.util.function.Supplier;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DialogImage extends ImageView{

	private DialogSequence myDS;
	
	DialogImage(DialogSprite DS, Supplier<DialogSequence> myDialogSeq){
		super(DS.getImageFileURL());
		myDS = myDialogSeq.get();
	}
	
	public DialogSequence getDialogSequence(){
		return myDS;
	}
	
	
}
