package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.GridManagers.BackgroundGridManager;
import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class LayerDataConverter {
	private static final XStream SERIALIZER = setupXStream();

	private int myNumRows;
	private int myNumCols;
	private int layerNum;
	private String myName;
	private List<SpriteDataConverter> mySprites;
	
	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}
	
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
			toStore.add(SDC.createSprite());
		});
		newLayer.storeSpriteObjectsToAdd(toStore);
		;
		return newLayer;
	}
}