package authoring_UI.dialogue;

import java.util.List;
import java.util.function.Consumer;

import authoring_UI.ViewSideBar;
import gui.welcomescreen.WelcomeScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.DisplayLanguage;

/**
 * Class that allows users to edit/save dialogues
 * 
 * @author DavidTran
 */
public class DialogueEditor {

	private static final String NAME_PROMPT = "Name";
	private static final String FONT_PROMPT = "Font";
	private static final String FONT_SIZE_PROMPT = "FontSize";
	private static final String NUM_PANELS_PROMPT = "NumberOfPanels";

	private static final double NAME_PROMPT_WIDTH = 150;
	private static final double FONT_PROMPT_WIDTH = 50; // change to choicebox
	private static final double FONT_SIZE_PROMPT_WIDTH = 50;
	private static final double NUM_PANELS_PROMPT_WIDTH = 50;
	private static final double PROMPT_HEIGHT = 10;
	private static final double INPUT_HBOX_HEIGHT = 100;

	private VBox view;
	private TextField nameTF;
	private TextField sizeTF;
	private TextField fontTF;
	private TextField numPanelsTF;
	private DialogueTextAreaView dsp;
	private Consumer<String> saveConsumer;

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

	public int getFontSize() {
		return Integer.parseInt(sizeTF.getText());
	}

	public String getFont() {
		return fontTF.getText();
	}

	public List<TextArea> getDialogueList() {
		return dsp.getDialogueList();
	}

	/*************************** PRIVATE METHODS *********************************/

	private void save(String name) {
		if (!name.trim().equals(""))
			saveConsumer.accept(name);
	}

	private void makeTemplate() {

		this.makeInputFields();

		view.getChildren().addAll(new HBox(makeEntry(NAME_PROMPT, nameTF)), new HBox(makeEntry(FONT_PROMPT, sizeTF)),
				new HBox(makeEntry(FONT_SIZE_PROMPT, fontTF)), dsp);

	}

	private void makeInputFields() {

		nameTF = makeTextField(NAME_PROMPT_WIDTH, PROMPT_HEIGHT);
		sizeTF = makeTextField(FONT_SIZE_PROMPT_WIDTH, PROMPT_HEIGHT);
		fontTF = makeTextField(FONT_PROMPT_WIDTH, PROMPT_HEIGHT);

		numPanelsTF = makeTextField(NUM_PANELS_PROMPT_WIDTH, PROMPT_HEIGHT);

		dsp = new DialogueTextAreaView();
		// numPanelsTF.setOnInputMethodTextChanged(e -> checkInput());

	}

	private TextField makeTextField(double width, double height) {
		TextField tf = new TextField();
		tf.setPrefWidth(width);
		return tf;
	}

	private HBox makeEntry(String prompt, TextField tf) {
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

}
