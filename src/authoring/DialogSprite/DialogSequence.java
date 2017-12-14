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
	
	public String getName(){
		return myName;
	}
	
	public List<DialogSprite> getDialogSprites(){
		return myDialogSprites;
	}

}
