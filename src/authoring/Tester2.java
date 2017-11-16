package authoring;

public class Tester2 {
	
	public static void main(String[] args){
		
	SpriteParameterFactory SPF = new SpriteParameterFactory();
	StringSpriteParameter BSP = (StringSpriteParameter) SPF.makeParameter("hello", "hi");
	
	SpriteObject S1 = new SpriteObject();
	S1.addParameter(BSP);
	
	SpriteObject S2 = new SpriteObject();
	S2.addParameter(BSP);
	
	if (S1.equals(S2)) {
		System.out.println("The same");
	}
	
//	BooleanSpriteParameter BSP = new BooleanSpriteParameter("hello", true);
//	BSP.setCheckedStatus(false);
	
//	BSP.setCheckedStatus(false);
	
	}
	
	

}
