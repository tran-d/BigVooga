package authoring.DialogSprite;

import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class DialogText {
	
	private String myText;
	private String myFont;
	private String myColor;
	private double myTextSize;
	
	private double relativeYCoor;
	private double relativeXCoor;
	private double relativeHeight;
	private double relativeWidth;
	
	
	
	private double absoluteWidth;
	private double absoluteHeight;
	private double absoluteCenterX;
	private double absoluteCenterY;
	
	private double paneHeight;
	private double paneWidth;
	
	public DialogText(Pane parent, TextArea child){
		paneHeight = parent.getHeight();
		paneWidth=parent.getWidth();
		
		myTextSize = child.getFont().getSize();
		myFont = child.getFont().getName();
		setColorFromCSS(child.getStyle());
		this.setText(child.getText());
		
		this.setAbsoluteDimensions(this.getBoundsInParent(parent, child));
		this.setRelativeYCoor(this.findRelativeYCoor());
		this.setRelativeXCoor(this.findRelativeXCoor());
		this.setRelativeHeight(this.findRelativeHeight());
		this.setRelativeWidth(this.findRelativeWidth());
	}
	
	public DialogText(){
		
	}
	private void setColorFromCSS(String cssRepresentation){
	String[] css = cssRepresentation.split(":");
	for (int i=0; i<css.length;i++){
		String curr = css[i];
		if (curr.matches("-fx-text-fill")){
			myColor = css[i+1];
			break;
		}
	}
	}
	
	public void setFont(String newFont){
    	myFont = newFont;
    }

    
    public String getFont(){
    	return this.myFont;
    }
    
    public void setColor(String newColor){
    	myColor = newColor;
    }
    
    public String getColor(){
    	return this.myColor;
    }
    
    public void setTextSize(double newSize){
    	myTextSize = newSize;
    }
    
    public double getTextSize(){
    	return this.myTextSize;
    }
	
	private Bounds getBoundsInParent(Pane parent, TextArea child){
		return child.getBoundsInParent();
	}
	
	private void setAbsoluteDimensions(Bounds boundsInParent){
		absoluteWidth = boundsInParent.getWidth();
		absoluteHeight = boundsInParent.getHeight();
		absoluteCenterX = boundsInParent.getMinX()+absoluteWidth/2;
		absoluteCenterY = boundsInParent.getMinY()+absoluteHeight/2;
	}
	
	public String getText() {
		return myText;
	}

	public void setText(String myText) {
		this.myText = myText;
	}


	public double getRelativeYCoor() {
		return relativeYCoor;
	}

	public void setRelativeYCoor(double relativeYCoor) {
		this.relativeYCoor = relativeYCoor;
	}

	public double getRelativeXCoor() {
		return relativeXCoor;
	}

	public void setRelativeXCoor(double relativeXCoor) {
		this.relativeXCoor = relativeXCoor;
	}

	public double getRelativeHeight() {
		return relativeHeight;
	}

	public void setRelativeHeight(double relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	public double getRelativeWidth() {
		return relativeWidth;
	}

	public void setRelativeWidth(double relativeWidth) {
		this.relativeWidth = relativeWidth;
	}
	
		
	
	private double findRelativeYCoor() {
		double pos = this.absoluteCenterY/paneHeight;
		return pos-.5;
	}

	private double findRelativeXCoor() {
		double pos = this.absoluteCenterX/paneWidth;
		return pos-.5;
	}

	private double findRelativeHeight() {
		double height = this.absoluteHeight/paneHeight;
		return height;
	}

	private double findRelativeWidth() {
		double width = this.absoluteWidth/paneWidth;
		return width;
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
