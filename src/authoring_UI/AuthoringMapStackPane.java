package authoring_UI;

import java.util.function.Consumer;

import authoring.AbstractSpriteObject;
import javafx.scene.layout.StackPane;

public class AuthoringMapStackPane extends StackPane{

	MapLayer myML;
//	private int myRow = 
	
	AuthoringMapStackPane(MapLayer ML){
		super();
		myML = ML;
	}
	
//	AuthoringMapStackPane(MapLayer M, int row, int col){
//		this(ML);
//		
//		
//	}
	
	public MapLayer getMapLayer(){
		return myML;
	}
	
	public void addChild(AbstractSpriteObject ASO){
//		ASO
		this.getChildren().add(ASO);
		ASO.setWidthConsumer(widthConsumer());
		ASO.setHeightConsumer(heightConsumer());
//		this.widthProperty()
	}
	
	private void setRowSpan(int span){
		getMapLayer().setRowSpan(this, span);
	}
	
	private void setColSpan(int span){
		getMapLayer().setColumnSpan(this, span);
	}
	
	public Consumer<Integer> heightConsumer(){
		Consumer<Integer> consumer = new Consumer<Integer>(){
			@Override
			public void accept(Integer t) {
				setRowSpan(t);
				
			}
			
		};
		return consumer;
	}
	
	public Consumer<Integer> widthConsumer(){
		Consumer<Integer> consumer = new Consumer<Integer>(){
			@Override
			public void accept(Integer t) {
				setColSpan(t);
				
			}
			
		};
		return consumer;
	}
}
