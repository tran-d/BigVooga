package authoring_UI;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DraggableGrid extends HBox {
	
	protected DraggableGrid(SpriteGridHandler spriteGridHandler) {
		createGrid(spriteGridHandler);
	}
	
	private void createGrid(SpriteGridHandler spriteGridHandler) {
		GridPane gp = new GridPane();
        
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                    StackPane sp = new StackPane();
                    sp.setPrefHeight(50);
                    sp.setPrefWidth(50);
                    sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT);
                    sp.setBorder(new Border(border));

                    gp.add(sp, i, j);
                    spriteGridHandler.addDropHandling(sp);
                    spriteGridHandler.addMouseClick(sp);
            }
        }
        ScrollPane scrollGrid = new ScrollPane(gp);
        scrollGrid.setPannable(true);
        this.getChildren().add(scrollGrid);
	}
	
}
