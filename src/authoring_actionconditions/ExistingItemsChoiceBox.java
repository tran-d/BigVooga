package authoring_actionconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import authoring.AuthoringEnvironmentManager;
import authoring.Sprite.AbstractSpriteObject;
import engine.operations.VoogaType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import sun.security.tools.keytool.Resources;

public class ExistingItemsChoiceBox {

	private static final String PACKAGE_NAME = ExistingItemsChoiceBox.class.getPackage().getName();
	private static final String KEY_BUNDLE_LOCATION = PACKAGE_NAME + ".codes";
	private ChoiceBox<String> cb;
	private ResourceBundle keys;

	// need current sprites

	public ExistingItemsChoiceBox(VoogaType type) {
		keys = Resources.getBundle(KEY_BUNDLE_LOCATION);
		List<String> list = this.makeObservableList(type);
		if (list.size() >= 0)
			cb = this.makeChoiceBox(list);
	}

	private List<String> makeObservableList(VoogaType type) {
		List<String> list = new ArrayList<>();
		Map<String, List<AbstractSpriteObject>> map = AuthoringEnvironmentManager.getEveryTypeOfSprite();
		List<AbstractSpriteObject> sprites = map.get("DefaultSprites");
		sprites.addAll(map.get("CustomSprites"));

		if (type == VoogaType.ANIMATIONNAME) {

			// TODO
			list.add("need access to animation names for current sprite");
			list.add("need access to animation names for current sprite");

		} else if (type == VoogaType.BOOLEANNAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("Boolean"));
			}

		} else if (type == VoogaType.DOUBLENAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("Double"));
			}

		} else if (type == VoogaType.DIALOGNAME) {

			// TODO
			list.add("need access to dialogue names");
			list.add("need access to dialogue names");

		} else if (type == VoogaType.KEY) {
			list = makeKeyList();

		} else if (type == VoogaType.OBJECTNAME) {
			list = AuthoringEnvironmentManager.getNameOfEverySprite();

		} else if (type == VoogaType.STRINGNAME) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getParameterNamesMatching("String"));
			}

		} else if (type == VoogaType.TAG) {
			for (AbstractSpriteObject sprite : sprites) {
				list.addAll(sprite.getTags());
			}

		} else if (type == VoogaType.WORLDNAME) {

			// TODO
			list.add("need access to world names");
			list.add("need access to world names");

		} else {
			list.add("if this is here, then it is broken");
			list.add("pls tell david if you see this");
		}
		;
		;
		return list;
	}

	private ChoiceBox<String> makeChoiceBox(List<String> list) {
		ObservableList<String> obList = FXCollections.observableArrayList(list);
		ChoiceBox<String> cb = new ChoiceBox<>(obList);
		return cb;
	}

	private List<String> makeKeyList() {
		List<String> list = new ArrayList<>();
		list.addAll(keys.keySet());
		return list;
	}

	/****************** PUBLIC METHODS ********************/

	public ChoiceBox<String> getChoiceBox() {
		return cb;
	}

	public String getSelected() {
		return "";
	}
}
