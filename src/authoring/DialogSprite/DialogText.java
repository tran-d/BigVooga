package authoring.DialogSprite;

import authoring.CutScene.SuperlayerComponent;
import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class DialogText extends SuperlayerComponent{
	
	private String myText;
	private String myFont;
	private double myTextSize;
	
	
	public DialogText(Pane parent, TextArea child){
		super(parent, child);
		myTextSize = child.getFont().getSize();
		myFont = child.getFont().getName();
		this.setText(child.getText());
	}
	
	public DialogText(){
		
	}
	public void setFont(String newFont){
    	myFont = newFont;
    }

    
    public String getFont(){
    	return this.myFont;
    }
    
    public void setTextSize(double newSize){
    	myTextSize = newSize;
    }
    
    public double getTextSize(){
    	return this.myTextSize;
    }
	
	public String getText() {
		return myText;
	}

	public void setText(String myText) {
		this.myText = myText;
	}
	
	public DialogText clone(){
		DialogText ret = new DialogText();
		ret.setText(new String(this.getText()));
		ret.setFont(this.getFont());
		ret.setColor(this.getColor());
		ret.setTextSize(this.getTextSize());
		
		ret.setRelativeHeight(this.getRelativeHeight());
		ret.setRelativeWidth(this.getRelativeWidth());
		ret.setRelativeXCoor(this.getRelativeXCoor());
		ret.setRelativeYCoor(this.getRelativeYCoor());
		return ret;
	}
}
