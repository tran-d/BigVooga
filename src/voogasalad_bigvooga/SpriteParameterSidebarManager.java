package voogasalad_bigvooga;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpriteParameterSidebarManager {

	ScrollPane SP;
	HashMap<String, ArrayList<SpriteParameterI>> everyStateParameter = new HashMap<String, ArrayList<SpriteParameterI>>();
	HashMap<String, String> newNameOldName = new HashMap<String, String>();
	boolean firstTimeThrough = true;
	SpriteObjectI firstSprite;
	
//	public ScrollPane getParameters(SpriteObjectI sprite, SpriteObjectGridManager SOGM) throws Exception{
//		ArrayList<SpriteObjectI> spriteList = new ArrayList<SpriteObjectI>();
//		spriteList.add(sprite);
//		return getParameters(spriteList, SOGM);
//	}

	public ScrollPane getParameters(SpriteObjectGridManagerI SOGM) throws Exception {
		ArrayList<SpriteObjectI> sprites = SOGM.getActiveSpriteObjects();
		checkActiveCellsMatch(sprites);
		SP = new ScrollPane();
		VBox container = new VBox();
		
		for (String category: everyStateParameter.keySet()){
			makeCategoryAndParameters(container, category);
		}
		
		Button applyChanges = new Button();
		applyChanges.setText("Apply Changes");
		applyChanges.setOnAction((event) -> {
			firstSprite.applyUpdates();
			int i = 0;
			for (SpriteObjectI SO: sprites){
				System.out.println("Loop: "+i);
				i++;
				System.out.println(SO.getParameters().get("General").get(0).getName());
//				System.out.println(firstSprite.getParameters().get("General").get(0).getName());
				SO.applyParameterUpdate(firstSprite.getParameters());
//				SO = firstSprite.newCopy();
				System.out.println(SO.getParameters().get("General").get(0).getName());
			}
		});
		
		container.getChildren().add(applyChanges);
		
		SP.setContent(container);
		return SP;

	}

	private void makeCategoryAndParameters(VBox container, String category) {
		ArrayList<Pane> PaneList = new ArrayList<Pane>();
		
		Text categoryText = new Text();
		categoryText.setText(category);
		
		Button addNewParameterToCategory = new Button();
		addNewParameterToCategory.setText(String.format("Add Parameter To Category %s", category));
		
		HBox categoryHBox = new HBox();
		categoryHBox.getChildren().add(categoryText);
		categoryHBox.getChildren().add(addNewParameterToCategory);
		
		
		
		PaneList.add(categoryHBox);
		
		for (SpriteParameterI SP: everyStateParameter.get(category)){
			PaneList.add(SP.getJavaFXPane());
		}
		container.getChildren().addAll(PaneList);
	}
	
	

	private void checkActiveCellsMatch(ArrayList<SpriteObjectI> SO_List) throws Exception {
		for (SpriteObjectI SO: SO_List){
		if (firstTimeThrough) {
			initializeMaps(SO);
			firstTimeThrough = false;
		} else {
			boolean matches = SO.isSame(firstSprite);
			if (!matches){
				throw new Exception("Sprites are not identical");
			}
		}
//			for (SpriteParameterI SP : SO.getParameters()) {
//				try {
//					if (!everyStateParameter.containsKey(SP.getCategory())) {
//						throw new Exception("State categories don't all match.");
//					}
//					ArrayList<SpriteParameterI> sParamList = everyStateParameter.get(SP.getCategory());
//					if (!sParamList.contains(SP)) {
//						throw new Exception(String.format("State parameters %s differ.", SP.getName()));
//					}
//
//				} catch (Exception e) {
//					throw e;
//				}
//			}
//		}
		}
	
	}

	private void initializeMaps(SpriteObjectI SO) {
		firstSprite = SO;
		everyStateParameter = SO.getParameters();
		newNameOldName = new HashMap<String, String>();
	}

}
