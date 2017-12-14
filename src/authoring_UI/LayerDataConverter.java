package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;

public class LayerDataConverter {

	private int myNumRows;
	private int myNumCols;
	private int layerNum;
	private String myName;
	private List<SpriteDataConverter> mySprites;
	
	
	public String getName(){
		return myName;
	}
	
	public LayerDataConverter getToSerialize(){
		return this;
	}
	
	public LayerDataConverter(SpriteObjectGridManager SOGM) {
		convertLayer(SOGM);
	}
	
	public void convertLayer(SpriteObjectGridManager SOGM){
//		myColor = SOGM.getColor();
		mySprites = new ArrayList<SpriteDataConverter>();
		myName = SOGM.getName();
		myNumRows = SOGM.getNumRows();
		myNumCols = SOGM.getNumCols();
		layerNum = SOGM.getLayerNum();
		SOGM.getEntireListOfSpriteObjects().forEach(sprite->{
			mySprites.add(new SpriteDataConverter(sprite));
		});
	}
	
	public SpriteObjectGridManager createLayer() {
		SpriteObjectGridManager newLayer = null;
		;
		
		if (myName.equals("Background")) {
			;
			newLayer = new BackgroundGridManager(myNumRows, myNumCols);
			;
		}
	
		else if (myName.equals("Terrain")) {
			;
			newLayer = new TerrainObjectGridManager(myNumRows, myNumCols);
			;
		}
		else if (myName.equals("Main View")) {
			;
			newLayer = new SpriteObjectGridManagerForSprites(myNumRows, myNumCols);
			;
		}
		else if (myName.equals("Panels")){
			;
			newLayer = new PanelObjectGridManager(myNumRows, myNumCols);
			;
		}
		List<AbstractSpriteObject> toStore = new ArrayList<AbstractSpriteObject>();
		mySprites.forEach(SDC->{
			System.out.println("sprite url " +SDC.imageURL);
			toStore.add(SDC.createSprite());
		});
		newLayer.storeSpriteObjectsToAdd(toStore);
		;
		return newLayer;
	}
}