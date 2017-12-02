package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import authoring_UI.SpriteDataConverter;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManager;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

// TODO 
public class LayerDataConverter {
	private final XStream SERIALIZER = setupXStream();
	private List<SpriteObject> allSpriteObjects;
	
	public XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}
	
	public LayerDataConverter(SpriteObjectGridManager SOGM) {
		convertLayer(SOGM);
	}
	
	public void convertLayer(SpriteObjectGridManager SOGM){
		allSpriteObjects = SOGM.getActiveSpriteObjects();
	}
	
	public List<SpriteDataConverter> getSpritesToConvert() {
		List<SpriteDataConverter> spriteConverters = new ArrayList<>();
		for (SpriteObject SO : allSpriteObjects) {
			spriteConverters.add(new SpriteDataConverter(SO));
		}
		return spriteConverters;
	}
	
	public SpriteObjectGridManager createLayer() {
		
		SpriteObjectGridManager SOGM = new SpriteObjectGridManager();
		
		SpriteObjectGridManager(int rows, int columns, int layerNum, Color c){
			
		}
		
	}
}