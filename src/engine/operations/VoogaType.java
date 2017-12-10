package engine.operations;

public enum VoogaType {

	STRING("String"), DOUBLE("Double"), VECTOR("Vector"), GAMEOBJECT("Sprite"), BOOLEAN("Boolean"), WORLDNAME(
			"World Name", "String"), OBJECTNAME("Sprite Name", "String"), ANIMATIONNAME("Animation Name",
					"String"), DIALOGNAME("Dialog Name", "String"), BOOLEANNAME("Boolean Name", "String"), DOUBLENAME(
							"Double Name",
							"String"), STRINGNAME("String Name",
									"String"), TAG("Tag", "String"), KEY("Key", "String"), ACTION("Action"), HOLDABLE("Holdable");


	private String presentable;
	private String strictType;

	private VoogaType(String both) {
		this(both, both);
	}

	private VoogaType(String presentable, String strictType) {
		this.presentable = presentable;
		this.strictType = strictType;
	}

	public String toString() {
		return getAuthoringType();
	}

	public String getEngineType() {
		return strictType;
	}

	public String getAuthoringType() {
		return presentable;
	}

}
