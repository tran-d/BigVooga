package authoring_UI;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TabContentVBox extends VBox{
	
	String myCategory;
	
	TabContentVBox(String category){
		super();
		myCategory = category;
		setSpacing(10);
	}
	
	public String getMyCategory() {
		return myCategory;
	}

	public void setMyCategory(String myCategory) {
		this.myCategory = myCategory;
	}
}
