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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
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
	private static final String INITIAL_FONT_COLOR = "#47BDFF";
	private static final String INITIAL_BACKGROUND_COLOR = "#FFFFFF";

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
	private SVGPath svg;

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

//		view.getChildren().addAll(new HBox(makeEntry(NAME_PROMPT, nameTF)),
//				new HBox(makeEntry(FONT_TYPE_PROMPT, fontTypeCB)), new HBox(makeEntry(FONT_SIZE_PROMPT, sizeTF)),
//				new HBox(makeEntry(FONT_COLOR_PROMPT, fontColorCP)), new HBox(makeEntry(BACKGROUND_COLOR_PROMPT, backgroundColorCP)), dsp);
		
		view.getChildren().addAll(new HBox(makeEntry(NAME_PROMPT, nameTF)), createAddTextAreaButton(),
				 new HBox(makeEntry(BACKGROUND_COLOR_PROMPT, backgroundColorCP)), dsp);
	}
	
	private Button createAddTextAreaButton() {
		Button addText = new Button("Add Text");
		addText.setOnAction(e -> dsp.addTextArea());
		
		return addText;
	}
	

	private void makeInputFields() {

		nameTF = makeTextField(NAME_PROMPT_WIDTH, PROMPT_HEIGHT);
		sizeTF = makeTextField(FONT_SIZE_PROMPT_WIDTH, PROMPT_HEIGHT);
		fontTypeCB = makeChoiceBox(FXCollections.observableList(Font.getFamilies()));
		fontColorCP = makeColorPallette(INITIAL_FONT_COLOR);
		backgroundColorCP = makeColorPallette(INITIAL_BACKGROUND_COLOR);
//		changeFontColor();
//		changeBackgroundColor();

		sizeTF.setOnKeyReleased(e -> changeFontSize());
		fontColorCP.setOnAction(e -> changeFontColor());
		backgroundColorCP.setOnAction(e -> changeBackgroundColor());
		

		numPanelsTF = makeTextField(NUM_PANELS_PROMPT_WIDTH, PROMPT_HEIGHT);

		dsp = new DialogueTextAreaView(() -> saveConsumer.accept(getName()));
		// numPanelsTF.setOnInputMethodTextChanged(e -> checkInput());
	}
	
	private void changeFontSize() {
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
	}
	
    private void changeFontColor() {
		dsp.setFontColor(toRGBString(fontColorCP.getValue()));
    }
    
    private void changeBackgroundColor() {
    		dsp.setBackgroundColor(backgroundColorCP.getValue());
    }
    
    private String toRGBString(Color c) {
        return "rgb("
                          + to255Int(c.getRed())
                    + "," + to255Int(c.getGreen())
                    + "," + to255Int(c.getBlue())
             + ")";
    }

    private int to255Int(double d) {
        return (int) (d * 255);
    }

	private ColorPicker makeColorPallette(String color) {
		ColorPicker cp = new ColorPicker(Color.web(color));
		svg = new SVGPath();
		svg.setContent("M70,50 L90,50 L120,90 L150,50 L170,50"
				+ "L210,90 L180,120 L170,110 L170,200 L70,200 L70,110 L60,120 L30,90"
				+ "L70,50");
		svg.setStroke(Color.DARKGREY);
		svg.setStrokeWidth(2);
		svg.setEffect(new DropShadow());
		svg.setFill(cp.getValue());
		
//
//		cp.setOnAction(e -> {
//			saveConsumer.accept(getName());
//			System.out.println("font changed! saving!");
//		});

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
		lb.setStyle("-fx-text-fill: #47BDFF;");
		hb.getChildren().addAll(lb, tf);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

}
