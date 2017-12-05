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
import java.util.function.Function;

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
		mySUTUIC = new SpriteUtilityToUIController(getBiFunctionForValueOnChange());
		initialize();
	}
	
	private void initialize(){
		makeContainerVBox();
		makeScrollPane();
		putVBoxIntoScrollPane();
	}
	
	public void setSpriteObject(AbstractSpriteObject ASO){
		myASO = ASO;
	}
	
	public void setSpriteObjectAndUpdate(AbstractSpriteObject ASO){
		myASO = ASO;
		mySUTUIC.setSpriteObject(ASO);
		clearVBox();
		addUtilityParametersToContainerVBox();
	}
	
	public void addUtilityParametersToContainerVBox(){
//		System.out.println("ASO: "+myASO);
//		myASO = myASO.getClass().getSuperclass().cast(myASO);
		Field [] fields = myASO.getClass().getSuperclass().getDeclaredFields();
		Annotation[] as = myASO.getClass().getAnnotations();
//		System.out.println(as);
//		System.out.println("Fields: "+ fields);
		for (Field f:fields){
//			System.out.println("Field f: "+f);
			Annotation[] a = f.getAnnotations();
//			 IsUnlockedUtility a = f.getAnnotation(IsUnlockedUtility.class);
			
			if (a!=null){
//				System.out.println("a isnt null!");
				SpriteUtilityUI UI_Component = mySUTUIC.getUIComponent(a, f);
//				System.out.println("UI_Component is "+UI_Component);
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
	
	public Pane getContainerVBox(){
		return containerVbox;
	}
	
	private void makeContainerVBox(){
		containerVbox = new VBox(10);
	}
	
	private void makeScrollPane(){
		containerScrollPane = new ScrollPane();
	}
	public ScrollPane getScrollPane(){
		return containerScrollPane;
	}
	
	public void putVBoxIntoScrollPane(){
		containerScrollPane.setContent(getContainerVBox());
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
	

	
	private BiFunction<String, Object, Boolean> getBiFunctionForValueOnChange(){
		BiFunction<String, Object, Boolean> bifunc = new BiFunction<String, Object, Boolean>(){
			
			@Override
			public Boolean apply(String t, Object u) {
				
//				System.out.println("U equals: "+u);
//				System.out.println(u.getClass());
				Method setMethod = null;
				if (u!=null){
					try{
					if (u instanceof String){
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, String.class);
						setMethod.invoke(myASO, (String) u);
					}
						//TODO: Handle String Changes
					else if (u instanceof Double){
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Double.class);
//						System.out.println("method: "+setMethod);
						setMethod.invoke(myASO, (Double) u);
						//TODO: Handle Double Changes
					}else if (u instanceof Integer){
//						System.out.println("method: "+setMethod);
//						System.out.println("method name: "+t);
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Integer.class);
//						System.out.println("method : "+setMethod);
						setMethod.invoke(myASO, (Integer) u);
						//TODO: Handle Integer Changes
					} else if (u instanceof Boolean){
						//TODO: Handle Boolean Changes
						setMethod  = myASO.getClass().getSuperclass().getMethod(t, Boolean.class);
					}
//					System.out.println("About to return true");
					return true;
				}
				 catch (Exception e){
//					e.printStackTrace();
					return false;
				}
					
				}
				
				return false;
			}
			
		};
		
		return bifunc;
	}
	
	
	
	
	
	
	
	
	
}
