package authoring_UI;


import java.util.ArrayList;
import java.util.List;

import authoring.GridManagers.SpriteObjectGridManager;

public class MapDataConverter {
//	private final XStream SERIALIZER = setupXStream();
	private String myName;
//	private String layerPath;
	private List<LayerDataConverter> gridManagers;
	
	public String getName(){
		return myName;
	}
	
	public MapDataConverter getToSerialize(){
		return this;
	}
	
	public MapDataConverter(DraggableGrid grids) {
		convertToMDC(grids);
		
	}
	
//	public String getLayerPath() {
//		return layerPath;
//	}
	
//	public void setLayerPath(String path) {
//		layerPath = path;
//	}
	
	private void convertToMDC(DraggableGrid grids){
		this.myName = grids.getName();
//		List<SpriteObjectGridManager> SOGMs = 
		gridManagers= new ArrayList<LayerDataConverter>();
				grids.getGrids().forEach(grid->{
					gridManagers.add(new LayerDataConverter(grid));
				});		
	}
	
	public DraggableGrid createDraggableGrid() {
		DraggableGrid newMap = new DraggableGrid();
		newMap.setName(this.myName);
		List<SpriteObjectGridManager> SOGMs = new ArrayList<SpriteObjectGridManager>();
		this.gridManagers.forEach(LDC->{
			SOGMs.add(LDC.createLayer());
		});
		newMap.loadLayers(SOGMs);
		return newMap;
	}
}