package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.GridManagers.PanelObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManager;
import authoring.GridManagers.SpriteObjectGridManagerForSprites;
import authoring.GridManagers.TerrainObjectGridManager;
import authoring.Sprite.SpriteObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class LayerDataConverter {
	private final XStream SERIALIZER = setupXStream();
	private List<SpriteObject> allSpriteObjects;
	private Color myColor;
	private int myNumRows;
	private int myNumCols;
	private int layerNum;
	private String myName;
	
	public XStream setupXStream() {
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
		myColor = SOGM.getColor();
		myName = SOGM.getName();
		myNumRows = SOGM.getNumRows();
		myNumCols = SOGM.getNumCols();
		layerNum = SOGM.getLayerNum();
	}
	
	public SpriteObjectGridManager createLayer() {
		SpriteObjectGridManager newLayer = null;
		if (layerNum == 0) {
			newLayer = new TerrainObjectGridManager(myNumRows, myNumCols, layerNum, myColor);
		}
		if (layerNum == 1) {
			newLayer = new SpriteObjectGridManagerForSprites(myNumRows, myNumCols, layerNum, myColor);
		}
		else {
			newLayer = new PanelObjectGridManager(myNumRows, myNumCols, layerNum, myColor);
		}
		return newLayer;
	}
}