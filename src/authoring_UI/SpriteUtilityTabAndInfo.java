package authoring_UI;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import authoring.AbstractSpriteObject;
import authoring.SpriteUtilityToUIController;
import authoring.SpriteUtilityUI;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SpriteUtilityTabAndInfo {

	
	private ScrollPane containerScrollPane;
	private VBox containerVbox;
	private AbstractSpriteObject myASO;
	private SpriteUtilityToUIController mySUTUIC;

	SpriteUtilityTabAndInfo(){
		mySUTUIC = new SpriteUtilityToUIController();
	}
	
	public void setSpriteObject(AbstractSpriteObject ASO){
		myASO = ASO;
	}
	
	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO){
		myASO = ASO;
		clearVBox();
		addUtilityParametersToContainerVBox();
	}
	
	public void addUtilityParametersToContainerVBox(){
		Field [] fields = myASO.getClass().getDeclaredFields();
		for (Field f:fields){
			Annotation[] a = f.getAnnotations();
//			 IsUnlockedUtility a = f.getAnnotation(IsUnlockedUtility.class);
			if (a!=null){
				SpriteUtilityUI UI_Component = mySUTUIC.getUIComponent(a, f);
				if (UI_Component!=null){
					addToVBox(UI_Component);
				}
			}
		}
	}
	
	private void addToVBox(Pane pane){
		containerVbox.getChildren().add(pane);
	}
	
	private void clearVBox(){
		containerVbox.getChildren().clear();
	}
	
	private void sort(){
		containerVbox.getChildren().sort(new Comparator<Node>(){

			@Override
			public int compare(Node o1, Node o2) {
				int o1Val = 0;
				int o2Val = 0;
				if (o1 instanceof SpriteUtilityUI){
					o1Val = 1;
				} else if (o2 instanceof SpriteUtilityUI){
					o2Val = 1;
				}
				
				if (o1!=o2){
					return o2Val-o1Val;
				} else {
					return (((SpriteUtilityUI) o2).getValue() - ((SpriteUtilityUI) o1).getValue());
				}
			}
			
		});
	}
	
	private void setConsumerForValueOnChange(){
		BiFunction<String, ArrayList<Object>, Boolean> bifunc = new BiFunction<String, ArrayList<Object>, Boolean>(){
			
			@Override
			public Boolean apply(String t, ArrayList<Object> u) {
				
				Method setMethod = null;
				if (u!=null){
					try{
					if (u.get(0) instanceof String){
						setMethod  = myASO.getClass().getMethod(t, String.class);
					}
						//TODO: Handle String Changes
					else if (u.get(0) instanceof Double){
						setMethod  = myASO.getClass().getMethod(t, Double.class);
						//TODO: Handle Double Changes
					}else if (u.get(0) instanceof Integer){
						setMethod  = myASO.getClass().getMethod(t, Integer.class);
						//TODO: Handle Integer Changes
					} else if (u.get(0) instanceof Boolean){
						//TODO: Handle Boolean Changes
						setMethod  = myASO.getClass().getMethod(t, Boolean.class);
					}
					
					return true;
				}
				 catch (Exception e){
					e.printStackTrace();
					return false;
				}
				}
				
				return false;
			}
			
		};
	}
	
	
	
	
	
	
	
	
	
}
