package authoring_UI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import authoring.AbstractSpriteObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public abstract class MapLayer extends GridPane {

	private int myLayerNumber;
	private int myRows;
	private int myColumns;
	private SpriteGridHandler mySGH;
	private Color defaultColor;
	private String myName;
	static final int CELL_SIZE = 50;
	protected ObjectProperty<Integer> numRowsProperty;
	protected ObjectProperty<Integer> numColumnsProperty;
	private Set<AuthoringMapStackPane> activeGridCells;

	protected MapLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super();
		defaultColor = c;
		activeGridCells = new HashSet<AuthoringMapStackPane>();
		numRowsProperty = new SimpleObjectProperty<Integer>();
		numColumnsProperty = new SimpleObjectProperty<Integer>();
		numRowsProperty.set(1);
		numColumnsProperty.set(1);
		
//		myRows = rows;
//		myColumns = columns;
		numRowsProperty.addListener((observable, oldNumRows, newNumRows)->{
			Integer diff = newNumRows-oldNumRows;
			if (diff<0){
				for (int i=0;i>diff;i--){
					for (int col =0;col<numColumnsProperty.get();col++){
						AuthoringMapStackPane AMSP = this.getChildAtPosition(oldNumRows-i-1, col);
						if (AMSP.isActive()){
							this.removeActive(AMSP);
						}
						if (AMSP.isCoveredByOtherSprite()){
							AbstractSpriteObject ASO = AMSP.getCoveringSprite();
							AuthoringMapStackPane parentSP = (AuthoringMapStackPane)ASO.getParent();
//							parentSP.removeChild();
							parentSP.setRowSpan(parentSP.getRowSpan()-1);
							
//							AMSP.getCoveringSprite().setFitHeight(currFitHeight-this.CELL_SIZE);
						}
					this.getChildren().remove(AMSP);
					}
				}
			} else if (diff>0){
				for (int i=0;i<diff;i++){
					for (int col =0;col<numColumnsProperty.get();col++){
					this.addAuthoringStackPaneToPosition(oldNumRows+i, col);
					}
			}
			}
		});
		
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			System.out.println("Layer num cols: "+newNumColumns);
			Integer diff = newNumColumns-oldNumColumns;
			if (diff<0){
				for (int i=0;i>diff;i--){
					for (int row =0;row<numRowsProperty.get();row++){
						System.out.println("Row: "+row);
						AuthoringMapStackPane AMSP = this.getChildAtPosition(row,oldNumColumns-i-1);
						if (AMSP.isActive()){
							this.removeActive(AMSP);
						}
						if (AMSP.isCoveredByOtherSprite()){
							AbstractSpriteObject ASO = AMSP.getCoveringSprite();
							AuthoringMapStackPane parentSP = (AuthoringMapStackPane)ASO.getParent();
//							parentSP.removeChild();
							parentSP.setColSpan(parentSP.getColSpan()-1);
							
						}
					this.getChildren().remove(AMSP);
					}
				}
			} else if (diff>0){
				for (int i=0;i<diff;i++){
					for (int row =0;row<numRowsProperty.get();row++){
					this.addAuthoringStackPaneToPosition(row, oldNumColumns+i);
					}
			}
			}
		});
		
		myLayerNumber = layerNum;
		mySGH = SGH;
		this.addAuthoringStackPaneToPosition(0,0);
		this.setNumRows(rows);
		this.setNumCols(columns);
//		setup();

	}
	
	public void setSpriteGridHandler(SpriteGridHandler SGH){
		mySGH = SGH;
	}
	
	public void addActive(AuthoringMapStackPane pane){
		this.activeGridCells.add(pane);
	}
	
	public Set<AuthoringMapStackPane> getActive(){
		return activeGridCells;
	}
	
	public void removeActive(AuthoringMapStackPane pane){
		this.activeGridCells.remove(pane);
	}
	
	public String getName(){
		return myName;
	}
	
	protected void setName(String name)
	{
		myName = name;
	}
	public int getLayerNumber() {
		return myLayerNumber;
	}

	protected void setDefaultColor(Color c) {
		defaultColor = c;
	}

	protected Color getDefaultColor() {
		return defaultColor;
	}
	
	public void removeSpritesAtPositions(ArrayList<Integer[]>locs){
		locs.forEach((pos)->{
			this.getChildAtPosition(pos[0], pos[1]).removeChild();
		});
		
	}
	
	protected AuthoringMapStackPane getChildAtPosition(int row, int col){
		AuthoringMapStackPane result = null;
		ObservableList<Node> childrens = this.getChildren();

	    for (Node node : childrens) {
	    	System.out.println("rowIndex: "+this.getRowIndex(node)+", columnIndex: "+this.getColumnIndex(node));
	        if(this.getRowIndex(node) == row && this.getColumnIndex(node) == col) {
	            result = (AuthoringMapStackPane) node;
	            break;
	        }
	    }
	    return result;
	}
	
	public int getCellSize(){
		return CELL_SIZE;
	}
	
	protected boolean hasChildAtPosition(int row, int column){
		AuthoringMapStackPane child = getChildAtPosition(row, column);
//		if (!(child instanceof AuthoringMapStackPane)){
//	    	return true;
//	    } else {
//	    	AuthoringMapStackPane childStackPane = (AuthoringMapStackPane) child;
	    	return child.hasChild();
//	    }
	}

//	protected void setup() {
//		for (int i = 0; i < myColumns; i++) {
//			for (int j = 0; j < myRows; j++) {
//				addAuthoringStackPaneToPosition(j,i);
//			}
//		}
//	}
	
	private void addAuthoringStackPaneToPosition(int row, int col){
		AuthoringMapStackPane sp = new AuthoringMapStackPane(this);
		sp.setMinWidth(CELL_SIZE);
		sp.setMaxWidth(CELL_SIZE);
		sp.setPrefWidth(CELL_SIZE);
		sp.setMinHeight(CELL_SIZE);
		sp.setPrefHeight(CELL_SIZE);
		sp.setMaxHeight(CELL_SIZE);
		
		// "-fx-background-color: rgba(0, 100, 100, 0.5);
		sp.setBackground(
				new Background(new BackgroundFill(getDefaultColor(), CornerRadii.EMPTY, Insets.EMPTY)));
		// sp.setStyle();
		BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		sp.setBorder(new Border(border));
//		GridPane.setColumnSpan(sp, 1);
//		GridPane.setRowSpan(sp, 1);
		GridPane.setHgrow(sp, Priority.NEVER);
		GridPane.setVgrow(sp, Priority.NEVER);
		this.add(sp, col, row);
		sp.setColSpan(1);
		sp.setRowSpan(1);
//		System.out.println(this.getRowSpan(sp));
		
		mySGH.addDropHandling(sp);
		mySGH.addGridMouseClick(sp);
		mySGH.addGridMouseDrag(sp);
	}
	
	public void addRow(){
		setNumCols(numRowsProperty.get()+1);
	}
	
	public void setNumRows(Integer newRows){
		this.numRowsProperty.set(newRows);
	}
	
	public void addColumn(){
		setNumCols(numColumnsProperty.get()+1);
	}
	
	public void setNumCols(Integer newCols){
		this.numColumnsProperty.set(newCols);
	}

}
