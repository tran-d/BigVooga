package authoring.Sprite;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.Sprite.Parameters.*;
import authoring.Sprite.AnimationSequences.*;
import authoring.Sprite.UtilityTab.*;
import authoring.Sprite.InventoryTab.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

public class SpriteThumbnail extends Thumbnail{
	
	private AbstractSpriteObject myASO;
	private AbstractSpriteObject myASOCopy;
	
	public SpriteThumbnail(ImageView im, String name){
		super(im, name);
	}
	
	public SpriteThumbnail(AbstractSpriteObject ASO, Boolean showButton){
		this(ASO, ASO.getName());
		myASO = ASO;
	}
	
	public SpriteThumbnail(AbstractSpriteObject ASO){
		this(ASO, false);
	}
	
	public AbstractSpriteObject getSprite(){
//		if (myASOCopy==null){
//			myASOCopy = myASO.newCopy();
//		}
		return myASO;
	}

}
