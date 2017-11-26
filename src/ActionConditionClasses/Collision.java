package ActionConditionClasses;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class Collision extends ChoiceBox<Integer> implements ChoiceBoxI<Integer> {
	
	private ObservableList<Integer> collisionSpriteObjects;
	
	public Collision() {
		super();
		collisionSpriteObjects = FXCollections.observableArrayList();
		collisionSpriteObjects.addListener((ListChangeListener<Integer>) c -> setItems(collisionSpriteObjects));
	}

	@Override
	public void addListChangeListener(ListChangeListener<Integer> c) {
		collisionSpriteObjects.addListener(c);
	}

	@Override
	public void setOptions(ObservableList<Integer> list) {
		collisionSpriteObjects = list;
	}

	@Override
	public Object getCurrentChoice() {
		return getValue();
	}
	
}
