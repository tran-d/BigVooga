package authoring;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.application.Platform;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class SpriteParameter implements SpriteParameterI {

	Control myNameJavaFXNode;
	String myName;
	Control myJavaFXNode;
	Object myValue;
	SpriteParameterI dummy; 
	boolean isDummy;
	
	SpriteParameter(){
		
	}
	
	SpriteParameter(String name, Object value){
//		boolean myValue = checkedStatus;
		setMyValue(myValue);
		isDummy = false;
		setUpVariables(name, value);
		makeClone();
		setCloneHandlers();
		setJavaFXNameNode();
	}
	
	SpriteParameter(String name, Object value, Boolean cloneMade){
		isDummy = true;
		setUpVariables(name, value);
	}
	
	protected abstract void setUpVariables(String name, Object value);
	protected abstract void setCloneHandlers();
	
	@Override
	public void updateName(String name) {
		myName = name;
		
	}

	@Override
	public String getName() {
		return myName;
	}
	
	protected void setJavaFXNameNode(){
		TextArea TA = new TextArea();
		TA.setText(myName);
		TA.textProperty().addListener((observable, oldValue, newValue) -> {
			String newText;
			if (newValue.contains("\t")|| newValue.contains(" ")){
				System.out.println("ContainsSpace");
//			String newText = newValue.trim();
//			System.out.println("newtext: "+newText);
			newText= newValue.replaceAll("\\s", "");
//			System.out.println("newtext: "+newText);
			Platform.runLater(() -> { 
				int currentCaretPosition = TA.getCaretPosition();
				TA.setText(newText);
				TA.positionCaret(currentCaretPosition-1);
	        }); 
			} else {
				newText = newValue;
			}
			dummy.updateName(newText);
		});
		myNameJavaFXNode = TA;
	}
	
	@Override
	public Control getJavaFXNameNode(){
		return myNameJavaFXNode;
	}
	
	protected void setMyValue(Object in){
		myValue = in;
	}
	
	protected void makeClone(){
		System.out.println(getValue());
		dummy = SpriteParameterFactory.makeParameter(getName(), getValue(), true);
		System.out.println("make clone");
		System.out.println(dummy.getClass());
	}

	@Override
	public abstract void updateValue(Object value);

	@Override
	public Control getJavaFXValueNode(){
		return myJavaFXNode;
	}
	
	protected void setJavaFXValueNode(Control control){
		myJavaFXNode = control;
	}
	
	@Override
	public abstract Object getValue();
	
	
	@Override 
	public boolean isSame(SpriteParameterI other) {
		return getName().equals(other.getName()) && getValue().equals(other.getValue());
	}
	
	@Override
	public Pane getJavaFXPane() {
		HBox hbox = new HBox();
		
		hbox.getChildren().add(getJavaFXNameNode());
		hbox.getChildren().add(getJavaFXValueNode());
	
		return hbox;
	}
	
	public SpriteParameterI getDummy(){
		return dummy;
	}
	
	@Override
	public void becomeDummy() {
		updateName(dummy.getName());
		updateValue(dummy.getValue());
	}

}
