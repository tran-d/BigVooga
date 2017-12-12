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
import authoring.Sprite.SpriteObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class LayerDataConverter {
	private static final XStream SERIALIZER = setupXStream();

	private int myNumRows;
	private int myNumCols;
	private int layerNum;
	private String myName;
	
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
		myName = SOGM.getName();
		myNumRows = SOGM.getNumRows();
		myNumCols = SOGM.getNumCols();
		layerNum = SOGM.getLayerNum();
	}
	
	public SpriteObjectGridManager createLayer() {
		SpriteObjectGridManager newLayer = null;
		System.out.println("Layer num!!: "+layerNum);
		
		if (myName.equals("Background")) {
			System.out.println("layerNUm: "+layerNum+" , background");
			newLayer = new BackgroundGridManager(myNumRows, myNumCols);
			System.out.println("NUM ROWS IN LDC: "+  myNumRows);
		}
	
		else if (myName.equals("Terrain")) {
			System.out.println("layerNUm: "+layerNum+" , terrain");
			newLayer = new TerrainObjectGridManager(myNumRows, myNumCols);
			System.out.println("NUM ROWS IN LDC: "+  myNumRows);
		}
		else if (myName.equals("Main View")) {
			System.out.println("layerNUm: "+layerNum+" , sprites");
			newLayer = new SpriteObjectGridManagerForSprites(myNumRows, myNumCols);
			System.out.println("NUM ROWS IN LDC: "+  myNumRows);
		}
		else if (myName.equals("Panels")){
			System.out.println("layerNUm: "+layerNum+" , panels");
			newLayer = new PanelObjectGridManager(myNumRows, myNumCols);
			System.out.println("NUM ROWS IN LDC: "+  myNumRows);
		}
		return newLayer;
	}
}