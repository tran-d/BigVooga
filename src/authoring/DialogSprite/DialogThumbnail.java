package authoring.DialogSprite;

import authoring.Thumbnail;
import javafx.scene.image.ImageView;

public class DialogThumbnail extends Thumbnail{
	
	private DialogSequence myASO;
	private DialogSequence myASOCopy;
	
	public DialogThumbnail(ImageView im, String name){
		super(im, name);
	}
	
	public DialogThumbnail(DialogSequence ASO, Boolean showButton){
		this(ASO.getImage(), ASO.getName());
		myASO = ASO;
	}
	
	public DialogThumbnail(DialogSequence ASO){
		this(ASO, false);
	}
	
	public DialogSequence getSprite(){
//		if (myASOCopy==null){
//			myASOCopy = myASO.newCopy();
//		}
		return myASO;
	}

}
