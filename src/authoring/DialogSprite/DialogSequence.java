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
