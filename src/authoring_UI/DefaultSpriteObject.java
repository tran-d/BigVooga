package authoring_UI;

import authoring.AbstractSpriteObject;

public class DefaultSpriteObject extends AbstractSpriteObject{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1111111L;

	public DefaultSpriteObject (){
		 setUniqueID();
	 }

	@Override
	public AbstractSpriteObject newCopy() {
		return new DefaultSpriteObject();
	}

}
