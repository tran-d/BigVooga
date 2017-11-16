package voogasalad_bigvooga;

public class SpriteParameterFactory {
	
	public static SpriteParameterI makeParameter(String name, Object value){
		if (value instanceof String) {
			return new StringSpriteParameter(name, (String) value);
		} else if (value instanceof Boolean) {
			return new BooleanSpriteParameter(name, (Boolean) value);
		} else if (value instanceof Integer){
			return new IntegerSpriteParameter(name, (Integer) value);
		}
		return null;
	}
	
	public static SpriteParameterI makeParameter(String name, Object value, Boolean isDummy){
		if (value instanceof String) {
			return new StringSpriteParameter(name, (String) value, isDummy);
		} else if (value instanceof Boolean) {
			return new BooleanSpriteParameter(name, (Boolean) value, isDummy);
		} else if (value instanceof Integer){
			return new IntegerSpriteParameter(name, (Integer) value, isDummy);
		}
		return null;
	}

}
