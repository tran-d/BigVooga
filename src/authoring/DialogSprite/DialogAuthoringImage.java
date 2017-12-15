package authoring.DialogSprite;

import java.util.function.Supplier;

import authoring.CutScene.SuperlayerSequence;
import javafx.scene.image.ImageView;

public class DialogAuthoringImage extends ImageView{

	private SuperlayerSequence mySLS;
	
	public DialogAuthoringImage(SuperlayerSprite DS, Supplier<SuperlayerSequence> mySuperlayerSeq){
		super(DS.getImageFileURL());
		mySLS = mySuperlayerSeq.get();
	}
	
	public SuperlayerSequence getDialogSequence(){
		return mySLS;
	}
	
	
}
