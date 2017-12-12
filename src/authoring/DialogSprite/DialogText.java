package authoring.DialogSprite;

import javafx.geometry.Bounds;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DialogText {
	
	private String myText;
	private String myFont;
	private String myColor;
	private double myTextSize;
	
	private double relativeYCoor;
	private double relativeXCoor;
	private double relativeHeight;
	private double relativeWidth;
	
	private Bounds boundsInParent;
	
	private Pane myParent;
	private TextArea myTextArea;
	
	private double absoluteWidth;
	private double absoluteHeight;
	private double absoluteCenterX;
	private double absoluteCenterY;
	
	public DialogText(Pane parent, TextArea child){
		myParent = parent;
		myTextArea = child;
		myTextSize = child.getFont().getSize();
		myFont = child.getFont().getName();
		setColor(child.getStyle());
		this.setText(child.getText());
		this.setBoundsInParent(parent, child);
		this.setAbsoluteDimensions();
		this.setRelativeYCoor(this.findRelativeYCoor());
		this.setRelativeXCoor(this.findRelativeXCoor());
		this.setRelativeHeight(this.findRelativeHeight());
		this.setRelativeWidth(this.findRelativeWidth());
	}
	
	private void setColor(String cssRepresentation){
	String[] css = cssRepresentation.split(":");
	for (int i=0; i<css.length;i++){
		String curr = css[i];
		if (curr.matches("-fx-text-fill")){
			myColor = css[i+1];
			break;
		}
	}
	}
	
	/**
	 * https://stackoverflow.com/questions/20357542/changing-text-area-string-colors-in-javafx-using-the-colorpicker
	 * @param c 
	 * @return String RGB representation
	 */
	private String toRgbString(Color c) {
        return "rgb("
                          + to255Int(c.getRed())
                    + "," + to255Int(c.getGreen())
                    + "," + to255Int(c.getBlue())
             + ")";
    }

    /**
     * https://stackoverflow.com/questions/20357542/changing-text-area-string-colors-in-javafx-using-the-colorpicker
     * @param d
     * @return int representation of color channel
     */
    private int to255Int(double d) {
        return (int) (d * 255);
    }
    
    public String getFont(){
    	return this.myFont;
    }
    
    public String getColor(){
    	return this.myColor;
    }
    
    public double getTextSize(){
    	return this.myTextSize;
    }
	
	private void setBoundsInParent(Pane parent, TextArea child){
		boundsInParent = child.getBoundsInParent();
	}
	
	private void setAbsoluteDimensions(){
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
		double pos = this.absoluteCenterY/myParent.getHeight();
		return pos-.5;
	}

	private double findRelativeXCoor() {
		double pos = this.absoluteCenterX/myParent.getWidth();
		return pos-.5;
	}

	private double findRelativeHeight() {
		double height = this.absoluteHeight/myParent.getHeight();
		return height;
	}

	private double findRelativeWidth() {
		double width = this.absoluteWidth/myParent.getWidth();
		return width;
	}
}
