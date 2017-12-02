package authoring_UI;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.SpriteObject;
import authoring.SpriteParameterI;
import javafx.geometry.Point2D;

public class SpriteDataConverter {
	
	private static final XStream SERIALIZER = setupXStream();
	
	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}
	
	HashMap<String, ArrayList<SpriteParameterI>> catmap;
	Integer[] gridPos;
	String name;
	Double width;
	Double height;
	String UUID;
	String imageURL;
	String mySavePath;
	
	public SpriteDataConverter(SpriteObject SO){
		convertSprite(SO);
	}
	
	public String getName(){
		return name;
	}
	
	public SpriteObject getSprite(File file){
		
		return null;
	}
	
	public SpriteDataConverter getToSerialize(){
		return this;
	}

	public void setPath(String path){
		mySavePath = path;
	}
	
	public void convertSprite(SpriteObject SO){
		catmap = SO.getParameters();
		gridPos = SO.getPositionOnGrid();
		name = SO.getName();
		width = SO.getNumCellsWidth();
		height = SO.getNumCellsHeight();
		UUID = SO.getUniqueID();
		imageURL = SO.getImageURL();
		mySavePath = SO.getSavePath();
		myCondAct = So>getconditionAction();
	}

	public SpriteObject createSprite() {
		SpriteObject ret = new SpriteObject(true);
		ret.setImageURL(imageURL);
		ret.setParameterMap(catmap);
		ret.setPositionOnGrid(gridPos);
		ret.setNumCellsHeight(height);
		ret.setNumCellsWidth(width);
		ret.setUniqueID(UUID);
		ret.setName(name);
		ret.setSavePath(mySavePath);
		ret.setCondAct(private var)
		return ret;
		
	}
}
