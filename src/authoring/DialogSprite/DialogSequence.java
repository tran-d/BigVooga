package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.Pane;

public class DialogSequence {
	
	private List<DialogSprite> myDialogSprites;
	private String myName;
	
	public DialogSequence(String sequenceName, Map<Pane, String> panes_urls){
		myDialogSprites = new ArrayList<DialogSprite>();
		panes_urls.forEach((pane, fileURL)->{
			myDialogSprites.add(new DialogSprite(pane, fileURL));
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
