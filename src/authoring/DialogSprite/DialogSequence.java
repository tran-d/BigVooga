package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;

public class DialogSequence {
	
	private List<DialogSprite> myDialogSprites;
	private String myName;
	
	public DialogSequence(String sequenceName, List<Pane> panes, String paneURL){
		myDialogSprites = new ArrayList<DialogSprite>();
		panes.forEach((pane)->{
			myDialogSprites.add(new DialogSprite(pane, paneURL));
		});
		myName = sequenceName;
	}
	
	public DialogSequence(){
		
	}
	
	public String getName(){
		return myName;
	}
	
	public void setName(String name){
		myName = name;
	}
	
	public List<DialogSprite> getDialogSprites(){
		return myDialogSprites;
	}
	
	public void setDialogSprites(List<DialogSprite> dSequences){
		myDialogSprites = dSequences;
	}
	
	public DialogSequence clone(){
		DialogSequence ret = new DialogSequence();
		List<DialogSprite> newDialogSprites = new ArrayList<DialogSprite>();
		myDialogSprites.forEach(dialog->{
			newDialogSprites.add(dialog.clone());
		});
		ret.setDialogSprites(newDialogSprites);
		ret.setName(new String(myName));
		return ret;
	}

}
