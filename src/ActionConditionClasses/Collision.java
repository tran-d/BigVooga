package ActionConditionClasses;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class Collision extends ChoiceBox<Integer> implements ChoiceBoxI {
	
	private ObservableList<Integer> collisionSpriteObjects;
	
	public Collision() {
		super();
		collisionSpriteObjects = FXCollections.observableArrayList();
		collisionSpriteObjects.addListener((ListChangeListener<Integer>) c -> setOptions(collisionSpriteObjects));
	}

	@Override
	public void addListChangeListener(ListChangeListener<Object> c) {
		collisionSpriteObjects.addListener(c);
	}

	@Override
	public void setOptions(ObservableList<Object> list) {
		setOptions(list);
	}
	
}
