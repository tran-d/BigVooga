package authoring.DialogSprite;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DialogSprite {

//	private String myBackgroundColor;
	private String myFileURL;
	
	private List<DialogText> myTexts;
	private static final int cellsWidth = 4;
	private static final int cellsHeight  = 2;

	DialogSprite(Pane pane, String fileURL) {
		myFileURL = fileURL;
		myTexts = new ArrayList<DialogText>();
		
//		myBackgroundColor = toRgbString((Color) pane.getBackground().getFills().get(0).getFill());
		pane.getChildren().forEach((text) -> {
			myTexts.add(createDialogText(pane, (TextArea) text));
		});
		
	}
	
	public DialogSprite(){
		
	}

	private DialogText createDialogText(Pane parent, TextArea ta) {
		DialogText ret = new DialogText(parent, ta);
		return ret;
	}
	
	public List<DialogText> getDialogText(){
		return myTexts;
	}
	public void setDialogText(List<DialogText> dialogTexts){
		myTexts = dialogTexts;
	}
	
//	public String getBackgroundColor(){
//		return this.myBackgroundColor;
//	}
	
	public String getImageFileURL(){
		return this.myFileURL;
	}
	
	public void setImageFileURL(String newURL){
		myFileURL = newURL;
	}
	
	public int getCellsWidth(){
		return cellsWidth;
	}
	
	public int getCellsHeight(){
		return cellsHeight;
	}
	
//	public ImageView getThumbnail() {
//		return new DialogImage(this);
//	}
	
	
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
    
    public DialogSprite clone(){
    	DialogSprite ret = new DialogSprite();
    	ret.setImageFileURL(new String(this.getImageFileURL()));
    	List<DialogText> newTexts = new ArrayList<DialogText>();
    	this.getDialogText().forEach(dialog->{
    		newTexts.add(dialog.clone());
    	});
    	ret.setDialogText(newTexts);
    	return ret;
    }

	

}
