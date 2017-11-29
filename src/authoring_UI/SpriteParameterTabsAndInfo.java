package authoring_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring.SpriteObject;
import authoring.SpriteParameterFactory;
import authoring.SpriteParameterI;
import authoring.StringSpriteParameter;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpriteParameterTabsAndInfo {

	private TabPane myTabPane;
	private final static double MENU_WIDTH = 400;
	private final static double MENU_HEIGHT = 500;
	private int categoryCounter = 1;
	private HashMap<String, Integer> counters;
	private SpriteObject mySO;
	private HashMap<String, String> catNames;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;
	

	SpriteParameterTabsAndInfo() {
		setupCounters();
		setupCategoryNamesMap();
		createCategoryTabPane();
		mySO = new SpriteObject();
		
		mySelectModel = myTabPane.getSelectionModel();
	}

	SpriteParameterTabsAndInfo(SpriteObject SO) {
		this();
		mySO = SO;
	}
	
	private void setupCounters(){
		counters = new HashMap<String, Integer>();
		counters.put("String", 1);
		counters.put("Double", 1);
		counters.put("Boolean", 1);
	}
	
	private void setupCategoryNamesMap(){
		catNames = new HashMap<String, String>();
	}

	public void setSpriteObject(SpriteObject SO) {
		mySO = SO;
		clearTabPane();
		create();
	}

	public ObservableList<Tab> getTabs() {
		return myTabPane.getTabs();
	}

	public TabPane getTabPane() {
		return myTabPane;
	}

	public void clearTabPane() {
		myTabPane.getTabs().clear();
	}

	public TabPane create() {
		clearTabPane();
		createFromSO(mySO);
		addCreateCategoryTab();
		return getTabPane();
	}

	public TabPane create(SpriteObject SO) {
		setSpriteObject(SO);
		return create();
	}
	
	public void createFromSO(SpriteObject SO) {
		HashMap<String, ArrayList<SpriteParameterI>> params = SO.getParameters();
		for (Map.Entry<String, ArrayList<SpriteParameterI>> entry : params.entrySet()) {

			String category = entry.getKey();
			ArrayList<SpriteParameterI> newParams = entry.getValue();
			FEParameterFactory newFactory = new FEParameterFactory(newParams);

			addCategoryTab(category, newFactory);
		}
	}

	public void createCategoryTabPane() {
		myTabPane = new TabPane();
		myTabPane.setSide(Side.RIGHT);
		myTabPane.setPrefHeight(500);
		myTabPane.setPrefWidth(400);
	}

	private void addEmptyCategoryTab() {
		String category = String.format("Category%d", categoryCounter++);
		currentTab = new Tab(category);
		currentTab.setContent(createEmptyCategoryVBox(category, currentTab));
		currentTab.setClosable(false);
		int numtabs = myTabPane.getTabs().size();
		// numtabs-1 to make sure the 'add tab' tab is the last tab
		myTabPane.getTabs().add(numtabs - 1, currentTab);
	}

	private void addCategoryTab(String category, VBox vbox) {
		Tab newCategory = new Tab(category);
		newCategory.setContent(createCategoryVBox(category, vbox, newCategory));
		newCategory.setClosable(false);
		myTabPane.getTabs().add(newCategory);
	}

	private ScrollPane createStatePane(VBox temp) {
		ScrollPane myStateSP_dummy = createEmptyScrollPane();
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}

	private VBox createCategoryVBox(String category, VBox vbox, Tab parentTab) {
		TabContentVBox emptyVBox = new TabContentVBox(category);
		emptyVBox.getChildren().addAll(createCategoryName(category, parentTab), createStatePane(vbox),
				createButtonsHBox(emptyVBox));
		return emptyVBox;
	}

	private HBox createCategoryName(String category, Tab parentTab) {
		HBox catNameHbox = new HBox();
		Text cat = new Text("Category Name");
		TextField catName = new TextField(category);
		catName.textProperty().addListener((observable, prev, next) -> {
			if (catNames.containsValue(prev)){
				for (String key: catNames.keySet()){
					if (catNames.get(key).equals(prev)){
						catNames.put(key, next);
					}
				}
			} else {
			catNames.put(prev, next);
			}
//			mySO.changeCategoryName(prev, next);
		});
		catNameHbox.getChildren().addAll(cat, catName);
		return catNameHbox;
	}

	private VBox createEmptyCategoryVBox(String category, Tab parentTab) {
		VBox vbox = new VBox();
		return createCategoryVBox(category, vbox, parentTab);
	}

	private Button createAddParameterButton(String type) {
		Button addParam = new Button(String.format("Add %s Parameter", type));
		return addParam;
	}

	private ScrollPane createEmptyScrollPane() {
		VBox vbox = new VBox();
		formatParametersVBox(vbox);
		ScrollPane myStateSP_dummy = new ScrollPane();
		myStateSP_dummy.setPrefSize(MENU_WIDTH, MENU_HEIGHT);
		myStateSP_dummy.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		myStateSP_dummy.setContent(vbox);
		return myStateSP_dummy;
	}
	
	private void setButtonAction(Button button, TabContentVBox vbox, String type, Object varValue){
		button.setOnAction(e -> {
			ScrollPane SP = null;
			for (Node node: vbox.getChildren()){
				if (node instanceof ScrollPane){
					SP = (ScrollPane) node;
				}
			}
			if (SP==null){
				throw new RuntimeException();
			}
			String category = vbox.getMyCategory();
			VBox SP_Content = (VBox) SP.getContent();
			
			Integer varCount = counters.get(type);
			varCount++;
			counters.put(type, varCount);
			
			String varName = String.format("%sParameter%d", type, varCount);

			SpriteParameterI SPI = SpriteParameterFactory.makeParameter(varName, varValue);
			mySO.addParameter(category, SPI);

			SP_Content.getChildren().add(FEParameterFactory.makeFEParameter(SPI));
		});
		
	}

	private Button createAddStringParameterButton(TabContentVBox vbox) {
		String type  = "String";
		String val = "";
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}
	
	private Button createAddDoubleParameterButton(TabContentVBox vbox) {
		String type  = "Double";
		Double val = 0.0;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}
	
	private Button createAddBooleanParameterButton(TabContentVBox vbox) {
		String type  = "Boolean";
		Boolean val = false;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private HBox createButtonsHBox(TabContentVBox vbox) {
		HBox buttonsHBox = new HBox();
		buttonsHBox.getChildren().addAll(createAddStringParameterButton(vbox), createAddDoubleParameterButton(vbox),createAddBooleanParameterButton(vbox));
		return buttonsHBox;
	}

	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(500);
		in.setPrefHeight(500);
	}

	private void addCreateCategoryTab() {
		Tab newCategory = new Tab();
		newCategory.setText("Add Category");
		newCategory.setClosable(false);
		newCategory.setOnSelectionChanged(e -> {
			addEmptyCategoryTab();
			mySelectModel.select(currentTab);
		});
		
		myTabPane.getTabs().add(newCategory);
	}

	public void apply() {

		ObservableList<Tab> tabs = myTabPane.getTabs();
		for (int i=0;i<tabs.size()-1;i++) {
			System.out.println(i);
			Tab t = tabs.get(i);
			System.out.println(t.getText());
			TabContentVBox TCV = (TabContentVBox) t.getContent();
			String catName = TCV.getMyCategory();
			if (catNames.containsKey(catName)){
				String newCatName = catNames.get(catName);
				t.setText(newCatName);
				mySO.changeCategoryName(catName, newCatName);
				
				
			}
			
			
			ScrollPane SP = null;
			for (Node node: TCV.getChildren()){
				if (node instanceof ScrollPane){
					SP = (ScrollPane) node;
				}
				
			}
			VBox paramsVbox = (VBox) SP.getContent();
			int num = 0;
			for (Node node : paramsVbox.getChildren()) {
				System.out.println("Num: "+num++);
				FEParameter FEP = (FEParameter) node;
				FEP.updateParameter();
			}

		}

	}

}
