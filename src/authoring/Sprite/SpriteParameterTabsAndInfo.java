package authoring.Sprite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.Sprite.Parameters.FEParameter;
import authoring.Sprite.Parameters.FEParameterFactory;
import authoring.Sprite.Parameters.SpriteParameterFactory;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring_UI.TabContentVBox;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private Map<String, Integer> counters;
	private AbstractSpriteObject mySO;
	private Map<String, String> catNames;
	private SingleSelectionModel<Tab> mySelectModel;
	private Tab currentTab;

	public SpriteParameterTabsAndInfo() {
		this(new SpriteObject());
	}

	SpriteParameterTabsAndInfo(AbstractSpriteObject SO) {
		setupCounters();
		setupCategoryNamesMap();
		createCategoryTabPane();
		createAddCategoryTab();
		setSpriteObject(SO);
	}

	private void setupCounters() {
		counters = new HashMap<String, Integer>();
		counters.put("String", 1);
		counters.put("Double", 1);
		counters.put("Boolean", 1);
	}

	private void setupCategoryNamesMap() {
		catNames = new HashMap<String, String>();
	}

	public void setSpriteObject(AbstractSpriteObject SO) {
		mySO = SO;
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
		int numTabs = myTabPane.getTabs().size();
		myTabPane.getTabs().remove(0, numTabs);
		this.catNames.clear();
//		System.out.println("Clearing tab pane: " + myTabPane.getTabs().size());
		// .clear();
	}

	public TabPane create() {
		clearTabPane();
		// createAddCategoryTab();
		createFromSO(mySO);
//		System.out.println("catNames after create: "+ catNames);
		this.createAddCategoryTab();
		return getTabPane();
	}

	public void create(AbstractSpriteObject SO) {
		setSpriteObject(SO);
		// return create();
	}

	public void createFromSO(AbstractSpriteObject SO) {
		categoryCounter = 1;
		this.catNames.clear();
		Map<String, List<SpriteParameterI>> params = SO.getParameters();
//		System.out.println(params);
		boolean loopedOnce = false;
		for (Map.Entry<String, List<SpriteParameterI>> entry : params.entrySet()) {
//			System.out.println("Loading entry");

			String category = entry.getKey();
//			System.out.println("Loading entry category: " + category);
			// this.catNames.put(category, category);
			List<SpriteParameterI> newParams = entry.getValue();
//			System.out.println("Loading entry val: " + newParams);
			FEParameterFactory newFactory = new FEParameterFactory(newParams);
//			System.out.println("Loading entry");

			addCategoryTab(category, newFactory);
			loopedOnce = true;
		}
		if (!loopedOnce) {
			this.addEmptyCategoryTab();
		}
	}

	public void createCategoryTabPane() {
		myTabPane = new TabPane();
		myTabPane.setSide(Side.RIGHT);
		myTabPane.setPrefHeight(500);
		myTabPane.setPrefWidth(400);
		mySelectModel = myTabPane.getSelectionModel();
	}

	private void addEmptyCategoryTab() {
		// String category = String.format("Category%d", categoryCounter++);
		String category = "Category1";
		currentTab = new Tab();
		currentTab.setContent(createEmptyCategoryVBox(category, currentTab));
//		System.out.println("currentTab text in addEmpty: "+currentTab.getText());
		currentTab.setClosable(false);
		int numtabs = myTabPane.getTabs().size()-1;
		// numtabs-1 to make sure the 'add tab' tab is the last tab
//		System.out.println("Addign to num-1");
		numtabs = numtabs<0 ? 0 : numtabs;
		myTabPane.getTabs().add(numtabs, currentTab);
		mySelectModel.select(currentTab);
//		System.out.println("select model changed");
	}

	private void addCategoryTab(String category, VBox vbox) {
		Tab newCategory = new Tab(category);
		newCategory.setContent(createCategoryVBox(category, vbox, newCategory));
		newCategory.setClosable(false);
		int index = myTabPane.getTabs().size()-1;
		index = (index<0) ? 0 : index;
		myTabPane.getTabs().add(index, newCategory);
	}

	private ScrollPane createStatePane(VBox temp) {
//		System.out.println("Creating statepane its null");
		ScrollPane myStateSP_dummy = createEmptyScrollPane();
		formatParametersVBox(temp);
		myStateSP_dummy.setContent(temp);
		return myStateSP_dummy;
	}

	private VBox createCategoryVBox(String category, VBox vbox, Tab parentTab) {
		TabContentVBox emptyVBox = new TabContentVBox(parentTab);
		emptyVBox.getChildren().addAll(createCategoryName(category, parentTab), createStatePane(vbox),
				createButtonsHBox(emptyVBox));
		return emptyVBox;
	}

	private HBox createCategoryName(String category, Tab parentTab) {
		HBox catNameHbox = new HBox();
		Text cat = new Text("Category Name");
		TextField catName = new TextField("");
		int num = 1;
//		System.out.println("CATNAMES: " + catNames);
//		System.out.println("before while, current catNames: "+catNames);
		while (catNames.containsValue(category)) {
//			System.out.println("In while, category: " + category);
			category = String.format("Category%d", num++);

		}
//		System.out.println("Out of while, category: " + category);
		catName.setText(category);
		catNames.put(category, category);
		parentTab.setText(category);

		catName.textProperty().addListener((observable, prev, next) -> {
			String newText = next;
//			System.out.println("next cat name: " + next);
			if (catNames.containsValue(next)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("HEADER");
				alert.setContentText("That category name already used: " + next);
				alert.showAndWait();

				// newText
				// catName.setText("");
			} else {
				// if (catNames.containsValue(prev)){
				// catNames.containsValue(prev)
				for (String key : catNames.keySet()) {
					if (catNames.get(key).equals(prev)) {
						catNames.put(key, next);
					}
				}
				// } else {
				// catNames.put(prev, next);
				// }

				// mySO.changeCategoryName(prev, next);
			}
			parentTab.setText(newText);
			catName.setText(newText);
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

	private void setButtonAction(Button button, TabContentVBox vbox, String type, Object varValue) {
		button.setOnAction(e -> {
			ScrollPane SP = null;
			for (Node node : vbox.getChildren()) {
				if (node instanceof ScrollPane) {
					SP = (ScrollPane) node;
				}
			}
			if (SP == null) {
				throw new RuntimeException();
			}
			String category = vbox.getCategory();
//			String category =
			VBox SP_Content = (VBox) SP.getContent();

			Integer varCount = counters.get(type);
			varCount++;
			counters.put(type, varCount);

			String varName = String.format("%sParameter%d", type, varCount);

			SpriteParameterI SPI = SpriteParameterFactory.makeParameter(varName, varValue);
			// mySO.addParameter(category, SPI);

			mySO.addPossibleParameter(category, SPI);
			SP_Content.getChildren().add(FEParameterFactory.makeFEParameter(SPI));
		});

	}

	private Button createAddStringParameterButton(TabContentVBox vbox) {
		String type = "String";
		String val = "";
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private Button createAddDoubleParameterButton(TabContentVBox vbox) {
		String type = "Double";
		Double val = 0.0;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private Button createAddBooleanParameterButton(TabContentVBox vbox) {
		String type = "Boolean";
		Boolean val = false;
		Button addParam = createAddParameterButton(type);
		setButtonAction(addParam, vbox, type, val);
		return addParam;
	}

	private HBox createButtonsHBox(TabContentVBox vbox) {
		HBox buttonsHBox = new HBox();
		buttonsHBox.getChildren().addAll(createAddStringParameterButton(vbox), createAddDoubleParameterButton(vbox),
				createAddBooleanParameterButton(vbox));
		return buttonsHBox;
	}

	private void formatParametersVBox(VBox in) {
		in.setPrefWidth(500);
		in.setPrefHeight(500);
	}

	private void createAddCategoryTab() {
		Tab newCategory = new Tab();
		newCategory.setText("Add Category");
		newCategory.setClosable(false);
		myTabPane.getTabs().add(newCategory);
		for (Tab t : myTabPane.getTabs()) {
//			System.out.println("Tab t: " + t.getText());
		}
//		System.out.println("Created add category");
		newCategory.setOnSelectionChanged(e -> {
//			System.out.println("On change occured");
			addEmptyCategoryTab();
		});

	}

	public void apply() {
		int categoriesAdded = mySO.acceptPossibleParameters();
		categoryCounter -= categoriesAdded;
		ObservableList<Tab> tabs = myTabPane.getTabs();
//		System.out.println("catNames : " + catNames);
		for (int i = 0; i < tabs.size() - 1; i++) {
//			System.out.println(i);
			Tab t = tabs.get(i);
//			System.out.println(t.getText());
			TabContentVBox TCV = (TabContentVBox) t.getContent();
			// String catName = TCV.getMyCategory();
			String catName = t.getText();
//			System.out.println("catName: " + catName);

			if (catNames.containsValue(catName)) {
				for (String key : catNames.keySet()) {
					if (catNames.get(key).equals(catName)) {
						mySO.updateCategoryName(key, catName);
					}
				}
			}

			// if (catNames.containsKey(catName)){
			//// categoryCounter--;
			// String newCatName = catNames.get(catName);
			//// System.out.println("apply, newCatName: "+);
			//// t.setText(newCatName);
			// mySO.updateCategoryName(catName, newCatName);
			// }
			//

			ScrollPane SP = null;
			for (Node node : TCV.getChildren()) {
				if (node instanceof ScrollPane) {
//					System.out.println("It's a scroll pane");
					SP = (ScrollPane) node;
				}

			}
			VBox paramsVbox = (VBox) SP.getContent();
			int num = 0;
			for (Node node : paramsVbox.getChildren()) {
//				System.out.println("Num: " + num++);
				FEParameter FEP = (FEParameter) node;
//				System.out.println(FEP);
				FEP.updateParameter();
			}

		}

	}

}
