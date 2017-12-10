package ActionConditionClasses;

import java.util.ResourceBundle;

public class ResourceBundleUtil {
	
	private static final String conditionActionTabTitlePath = "TextResources/ConditionActionTitles";
	private static final ResourceBundle conditionActionTabResource = ResourceBundle.getBundle(conditionActionTabTitlePath);
	
	public static String getTabTitle(String key) {
		return conditionActionTabResource.getString(key);
	}
	
	public static ResourceBundle getResourceBundle(String tabTitle) {
		String tabResourcePath = "TextResources/" + tabTitle.substring(0,tabTitle.length() - 1) + "TabResources";
		return ResourceBundle.getBundle(tabResourcePath);
	}

}
