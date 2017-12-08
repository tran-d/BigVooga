package authoring_UI;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import authoring.SpriteObjectGridManager;
import javafx.geometry.Point2D;

public class MapDataConverter {
	private final XStream SERIALIZER = setupXStream();
	private String myName;
	private List<SpriteObjectGridManager> gridManagers;
	
	public XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}
	
	public String getName(){
		return myName;
	}
	
	public MapDataConverter getToSerialize(){
		return this;
	}
	
	public MapDataConverter(DraggableGrid grids) {
		convertLayer(grids);
	}
	
	public void convertLayer(DraggableGrid grids){
		gridManagers = grids.getGrids();
	}
	
	public List<LayerDataConverter> getLayersToConvert() {
		List<LayerDataConverter> converters = new ArrayList<>();
		for (SpriteObjectGridManager gridManager : gridManagers) {
			converters.add(new LayerDataConverter(gridManager));
		}
		return converters;
	}
	
	// create spriteobjectgrid manager list. 
	
	public DraggableGrid createMap() {
		DraggableGrid newMap = new DraggableGrid();
		
		// TODO need more? 
		return newMap;
	}
}