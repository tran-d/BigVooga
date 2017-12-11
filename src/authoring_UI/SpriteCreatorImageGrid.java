package authoring_UI;

import authoring.AbstractSpriteObject;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpriteCreatorImageGrid extends GridPane {

	private AbstractSpriteObject currentSprite;
	private StackPane imageStack;

	public SpriteCreatorImageGrid() {

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				if (i == 1 && j == 1) {
					imageStack = sp;
					this.add(imageStack, 1, 1);
				} else {
					this.add(sp, j, i);
				}
			}
		}
	}
	public StackPane getImageStack() {
		return imageStack;
	}
	
	public void setCurrentSprite(AbstractSpriteObject s) {
		currentSprite = s;
	}

	public void setSprite(AbstractSpriteObject s) {
		if (this.getSprite() == null) {
			currentSprite = s;
			imageStack.getChildren().add(s);
		} else {
			this.getImageStack().getChildren().remove(0);
			currentSprite = s;
			imageStack.getChildren().add(s);
		}

	}

	public AbstractSpriteObject getSprite() {
		return currentSprite;
	}
	
	public void removeSprite() {
		
	}
}
