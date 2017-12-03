package authoring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Consumer;

import authoring.AbstractSpriteObject.IsLockedUtility;
import authoring.AbstractSpriteObject.IsUnlockedUtility;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class SpriteUtilityToUIController {

	private HBox containerHbox;
	private Consumer<ArrayList<Object>> myMethodOnValueChange;

	public SpriteUtilityToUIController() {
		this.setMethodOnValueChange(list -> {
			// Nothing
		});
	}

	SpriteUtilityToUIController(Consumer<ArrayList<Object>> methodOnValueChange) {
		this.setMethodOnValueChange(methodOnValueChange);
	}

	public SpriteUtilityUI getUIComponent(Annotation[] a, Field f) {
		Object o = null;
		try {
			o = (Object) f.get(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < a.length; i++) {
			Annotation b = a[i];
			if (b instanceof IsUnlockedUtility) {
				return getUnlockedUIComponent((IsUnlockedUtility) b, o);
			} else if (b instanceof IsLockedUtility) {
				return getLockedUIComponent((IsLockedUtility) b, o);
			}
		}
		return null;
	}

	private SpriteUtilityUI getUnlockedUIComponent(IsUnlockedUtility a, Object o) {
		String getMethod = a.getMethod();
		String setMethod = a.setMethod();
		String readableName = a.readableName();

		Pane p = new HBox();

		Label nameLabel = new Label();
		nameLabel.setText(readableName);

		Node content = getRequiredValueComponent(o, getMethodOnValueChange());

		p.getChildren().addAll(nameLabel, content);

		SpriteUtilityUI SUUI = new SpriteUtilityUI(p);

		return SUUI;

	}

	private SpriteUtilityUI getLockedUIComponent(IsLockedUtility a, Object o) {
		String getMethod = a.getMethod();
		String readableName = a.readableName();

		Pane p = new HBox();

		Label nameLabel = new Label();
		nameLabel.setText(readableName);

		Node content = getRequiredValueComponent(o, getMethodOnValueChange());

		p.getChildren().addAll(nameLabel, content);

		SpriteUtilityUI SUUI = new SpriteUtilityUI(p);

		return SUUI;

	}

	private Node getRequiredValueComponent(Object o, Consumer<ArrayList<Object>> consumer) {
		if (o instanceof String) {
			TextField ret = new TextField();
			ret.setText((String) o);
			ret.textProperty().addListener((observable, previous, next) -> {
				ArrayList<Object> forConsumer = new ArrayList<Object>();
				forConsumer.add(previous);
				forConsumer.add(next);
				consumer.accept(forConsumer);
			});
			return ret;
		} else if (o instanceof Integer || o instanceof Double) {
			NumberSpinner ret = new NumberSpinner(BigDecimal.valueOf((Double) o), BigDecimal.ONE);
			ret.numberProperty().addListener((observable, previous, next) -> {
				ArrayList<Object> forConsumer = new ArrayList<Object>();
				forConsumer.add(previous);
				forConsumer.add(next);
				consumer.accept(forConsumer);
			});
			return ret;
		} else if (o instanceof Boolean) {

		}
		return null;
	}

	private Consumer<ArrayList<Object>> getMethodOnValueChange() {
		return myMethodOnValueChange;
	}

	private void setMethodOnValueChange(Consumer<ArrayList<Object>> consumer) {
		myMethodOnValueChange = consumer;
	}

}
