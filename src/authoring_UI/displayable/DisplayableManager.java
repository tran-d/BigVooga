package authoring_UI.displayable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

public abstract class DisplayableManager {
	
	public DisplayableManager() {
		
	}
	
	protected Separator createSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		return separator;
	}
	
	protected Separator createShortSeparator() {
		Separator separator = new Separator();
		separator.setOrientation(Orientation.VERTICAL);
		separator.setPrefHeight(300);
		separator.setMaxHeight(300);
		separator.setMinHeight(300);
		return separator;
	}
	
	protected void updateListView() {
		// do nothing
	}
	
	protected void save() {
		// do nothing
	}
	
	protected void newEditor() {
		// do nothing
	}
	
	protected void loadEditor() {
		// do nothing
	}
	
	protected VBox createButtonPanel() {
		return null;
	}
	
	protected void prev() {
		// do nothing
	}
	
	protected void next() {
		// do nothing
	}
	
	protected Button createButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}
	
	protected void delete() {
		// do nothing
	}
	

}
