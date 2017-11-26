package authoring_UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MapEditor extends Application{

    private final DataFormat objFormat = new DataFormat("MyObject");

    private ImageView draggingObject;
    

    @Override
    public void start(Stage primaryStage) {
        try {
        		VBox menu = createMenu();
        		ImageView trashCan = new ImageView(new Image("trash.png"));
        	    trashCan.setFitWidth(45);
        	    trashCan.setFitHeight(45);
        		addDropToTrash(trashCan);
            VBox root = new VBox();

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
                        addDropHandling(sp);
                }
            }
            ScrollPane scrollGrid = new ScrollPane(gp);
            scrollGrid.setPannable(true);
            
            HBox draggableObjects = new HBox();
            StackPane treeStack = new StackPane();
            for (int k = 0; k < 10; k ++) {
            	ImageView tree = new ImageView(new Image("tree.png"));
                tree.setFitWidth(45);
                tree.setFitHeight(45);
                treeStack.getChildren().add(tree);
                dragObject(tree);
            }
            StackPane brickStack = new StackPane();
            for (int n = 0; n < 10; n++) {
            	    ImageView brick = new ImageView(new Image("brick.png"));
            	    brick.setFitWidth(45);
            	    brick.setFitHeight(45);
                brickStack.getChildren().add(brick);
                dragObject(brick);
            }
            StackPane waterStack = new StackPane();
            for (int n = 0; n < 10; n++) {
            	    ImageView water = new ImageView(new Image("water.png"));
            	    water.setFitWidth(45);
            	    water.setFitHeight(45);
                waterStack.getChildren().add(water);
                dragObject(water);
            }
            StackPane pikaStack = new StackPane();
            for (int m = 0; m < 10; m++) {
            		ImageView pika = new ImageView(new Image("pikachu.png"));
                pika.setFitWidth(45);
                pika.setFitHeight(45);
                pikaStack.getChildren().add(pika);
                dragObject(pika);
            }

            draggableObjects.getChildren().addAll(treeStack, brickStack, waterStack, pikaStack);
            
            HBox gridBox = new HBox();
            gridBox.getChildren().addAll(trashCan, scrollGrid);
            TabPane spritePane = createSpriteTabs(draggableObjects);
            root.getChildren().addAll(spritePane, gridBox);
            
            HBox authoringBox = new HBox();
            authoringBox.getChildren().addAll(menu, root);
            
            Scene scene = new Scene(authoringBox,700,600);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private VBox createMenu() {
    		VBox menuBox = new VBox();
    		HBox buttons = new HBox();
    		Button load = new Button("Load");
    		Button save = new Button("Save");
    		Button map = new Button("New Map");
    		buttons.getChildren().addAll(load, save, map);
    		
    		TabPane itemPane = new TabPane();
    		Tab props = new Tab();
    		props.setText("Properties");
    		props.setContent(new TextArea("editable properties listed here"));
    		Tab actions = new Tab();
    		actions.setText("Actions");
    		actions.setContent(new TextArea("editable actions listed here"));
    		itemPane.getTabs().addAll(props, actions);
    		itemPane.setPrefWidth(200);
    		itemPane.setPrefHeight(500);
    		
    		menuBox.getChildren().addAll(buttons, itemPane);
    		
    		return menuBox;
    }
    
    private TabPane createSpriteTabs(Node defaultSprites) {
    		TabPane sprites = new TabPane();
    		
    		 Tab defaultSpriteTab = new Tab();
         defaultSpriteTab.setText("Default Sprites");
         defaultSpriteTab.setContent(defaultSprites);
         
         Tab mySpriteTab = new Tab();
         mySpriteTab.setText("My Sprites");
         
         sprites.getTabs().addAll(defaultSpriteTab, mySpriteTab);
         
         return sprites;
    		
    }


     private void dragObject(ImageView b) {
         b.setOnDragDetected(e -> {
         Dragboard db = b.startDragAndDrop(TransferMode.MOVE);

         db.setDragView(b.snapshot(null, null)); 
         ClipboardContent cc = new ClipboardContent();
         cc.put(objFormat, " "); 
         db.setContent(cc); 
         draggingObject = b;    
         });
     }

     private void addDropHandling(StackPane pane) {
         pane.setOnDragOver(e -> {
             Dragboard db = e.getDragboard();
             if (db.hasContent(objFormat) && draggingObject != null) {
                 e.acceptTransferModes(TransferMode.MOVE);

             }
         });

         pane.setOnDragDropped(e -> {
             Dragboard db = e.getDragboard();

             if (db.hasContent(objFormat)) {
                 ((Pane)draggingObject.getParent()).getChildren().remove(draggingObject);
                 pane.getChildren().add(draggingObject);
                 e.setDropCompleted(true);

                 draggingObject = null;
             }           
         });

     }
     
     private void addDropToTrash(ImageView trash) {
    	 
    	 	trash.setOnDragOver(e -> {
             Dragboard db = e.getDragboard();
             if (db.hasContent(objFormat) && draggingObject != null) {
                 e.acceptTransferModes(TransferMode.MOVE);

             }
         });
    	 	
    	 	trash.setOnDragDropped(e -> {
    	 		Dragboard db = e.getDragboard();
    	 		if (db.hasContent(objFormat)) {
    	 			((Pane)draggingObject.getParent()).getChildren().remove(draggingObject);
    	 			e.setDropCompleted(true);
    	 			
    	 			draggingObject = null;
    	 		}
    	 	});
     }

    public static void main(String[] args) {
        launch(args);
    }
}


