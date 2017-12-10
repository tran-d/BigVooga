package authoring_UI.dialogue;

import java.util.List;
import java.util.function.Consumer;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import tools.DisplayLanguage;

/**
 * Class that allows users to edit/save dialogues
 * 
 * @author DavidTran
 */
public class DialogueEditor {

	private static final String NAME_PROMPT = "Name";
	private static final String FONT_TYPE_PROMPT = "FontType";
	private static final String FONT_SIZE_PROMPT = "FontSize";
	private static final String FONT_COLOR_PROMPT = "FontColor";
	private static final String BACKGROUND_COLOR_PROMPT = "BackgroundColor";
	private static final String NUM_PANELS_PROMPT = "NumberOfPanels";
	private static final String INVALID_INPUT_MESSAGE = "InvalidInput";
	private static final String INTEGER_INPUT_PROMPT = "InputInteger";

	private static final double NAME_PROMPT_WIDTH = 150;
	private static final double FONT_PROMPT_WIDTH = 50; // change to choicebox
	private static final double FONT_SIZE_PROMPT_WIDTH = 50;
	private static final double NUM_PANELS_PROMPT_WIDTH = 50;
	private static final double PROMPT_HEIGHT = 10;
	private static final double INPUT_HBOX_HEIGHT = 100;

	private VBox view;
	private TextField nameTF;
	private TextField sizeTF;
	private ChoiceBox<String> fontTypeCB;
	private ColorPicker fontColorCP;
	private TextField numPanelsTF;
	private DialogueTextAreaView dsp;
	private Consumer<String> saveConsumer;
	private ColorPicker backgroundColorCP;

	public DialogueEditor(Consumer<String> saveCons) {
		this.saveConsumer = saveCons;
		view = new VBox(10);
		view.setPrefSize((WelcomeScreen.WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH) / 2, INPUT_HBOX_HEIGHT);
		view.getStylesheets().add(DialogueManager.class.getResource("dialogue.css").toExternalForm());

		this.makeTemplate();
	}

	/*************************** PUBLIC METHODS *********************************/

	public VBox getParent() {
		return view;
	}

	public String getName() {
		return nameTF.getText();
	}

	public String getFontType() {
		return fontTypeCB.getSelectionModel().getSelectedItem();
	}

	public int getFontSize() {
		if (sizeTF.getText().equals(""))
			return 0;
		else
			return Integer.parseInt(sizeTF.getText());
	}

	public Color getFontColor() {
		return fontColorCP.getValue();
	}

	public List<TextArea> getDialogueList() {
		return dsp.getDialogueList();
	}
	
	public Color getBackgroundColor() {
		return backgroundColorCP.getValue();
	}

	/*************************** PRIVATE METHODS *********************************/

	private void save(String name) {
		if (!name.trim().equals(""))
			saveConsumer.accept(name);
	}

	private void makeTemplate() {

		this.makeInputFields();

		view.getChildren().addAll(new HBox(makeEntry(NAME_PROMPT, nameTF)),
				new HBox(makeEntry(FONT_TYPE_PROMPT, fontTypeCB)), new HBox(makeEntry(FONT_SIZE_PROMPT, sizeTF)),
				new HBox(makeEntry(FONT_COLOR_PROMPT, fontColorCP)), new HBox(makeEntry(BACKGROUND_COLOR_PROMPT, backgroundColorCP)), dsp);

	}

	private void makeInputFields() {

		nameTF = makeTextField(NAME_PROMPT_WIDTH, PROMPT_HEIGHT);
		sizeTF = makeTextField(FONT_SIZE_PROMPT_WIDTH, PROMPT_HEIGHT);
		fontTypeCB = makeChoiceBox(FXCollections.observableList(Font.getFamilies()));
		fontColorCP = makeColorPallette();
		backgroundColorCP = makeColorPallette();

		sizeTF.setOnKeyReleased(e -> {
			if (!sizeTF.getText().equals("")) {

				try {
					int size = Integer.parseInt(sizeTF.getText());
					saveConsumer.accept(getName());
					System.out.println("size changed! saving!");
					dsp.setFontSize(size);
				} catch (NumberFormatException ex) {
					sizeTF.clear();
					Alert alert = new Alert(AlertType.ERROR);
					alert.contentTextProperty().bind(DisplayLanguage.createStringBinding(INTEGER_INPUT_PROMPT));
					alert.headerTextProperty().bind(DisplayLanguage.createStringBinding(INVALID_INPUT_MESSAGE));
					alert.show();
				}
			}
		});

		// fontCB.setOnKeyReleased(e -> {
		//
		// saveConsumer.accept(getName());
		// System.out.println("font changed! saving!");
		// });

		numPanelsTF = makeTextField(NUM_PANELS_PROMPT_WIDTH, PROMPT_HEIGHT);

		dsp = new DialogueTextAreaView(() -> saveConsumer.accept(getName()));
		// numPanelsTF.setOnInputMethodTextChanged(e -> checkInput());
	}

	private ColorPicker makeColorPallette() {
		ColorPicker cp = new ColorPicker();

		cp.setOnAction(e -> {
			saveConsumer.accept(getName());
			System.out.println("font changed! saving!");
		});

		return cp;
	}

	private ChoiceBox<String> makeChoiceBox(ObservableList<String> observableList) {
		ChoiceBox<String> cb = new ChoiceBox<String>(observableList);

		System.out.println("fonts: " + observableList);

		cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				saveConsumer.accept(getName());
				System.out.println("font changed! saving!");
				dsp.setFontType(observableList.get(cb.getSelectionModel().getSelectedIndex()));
			}
		});
		return cb;
	}

	private TextField makeTextField(double width, double height) {
		TextField tf = new TextField();
		tf.setPrefWidth(width);
		return tf;
	}

	private HBox makeEntry(String prompt, Node tf) {
		HBox hb = new HBox();
		Label lb = new Label();
		lb.textProperty().bind(DisplayLanguage.createStringBinding(prompt));
		hb.getChildren().addAll(lb, tf);
		return hb;
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

	public static void main(String[] args) {
		DialogueEditor ed = new DialogueEditor(null);

		System.out.println(Font.getFamilies());
	}

}
