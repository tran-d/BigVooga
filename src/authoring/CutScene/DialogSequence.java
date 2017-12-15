package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CutSceneSequence {
	
	private List<CutSceneSprite> myDialogSprites;
	private String myName;
	
	public CutSceneSequence(String sequenceName, List<Pane> panes, String paneURL){
		myDialogSprites = new ArrayList<DialogSprite>();
		panes.forEach((pane)->{
			myDialogSprites.add(new DialogSprite(pane, paneURL));
		});
		myName = sequenceName;
	}
	
	public CutSceneSequence(){
		
	}
	
	public String getName(){
		return myName;
	}
	
	public void setName(String name){
		myName = name;
	}
	
	public List<CutSceneSprite> getDialogSprites(){
		return myDialogSprites;
	}
	
	public void setCutSceneSprites(List<CutSceneSprite> dSequences){
		myDialogSprites = dSequences;
	}
	
	public ImageView getImage(){
		return new CutSceneImage(myDialogSprites.get(0), ()->this);
	}
	
	public CutSceneSequence clone(){
		CutSceneSequence ret = new CutSceneSequence();
		List<DialogSprite> newDialogSprites = new ArrayList<DialogSprite>();
		myDialogSprites.forEach(dialog->{
			newDialogSprites.add(dialog.clone());
		});
		ret.setDialogSprites(newDialogSprites);
		ret.setName(new String(myName));
		return ret;
	}

}
