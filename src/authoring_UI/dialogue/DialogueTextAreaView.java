package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import tools.DisplayLanguage;

/**
 * Class that displays the text areas and utilities for editing dialogues.
 * 
 * @author DavidTran
 *
 */
public class DialogueTextAreaView extends VBox {

	private static final double VBOX_SPACING = 25;
	private static final double DIALOG_PROMPT_WIDTH = 600;
	private static final double DIALOG_PROMPT_HEIGHT = 300;
	private static final String NEXT_BUTTON_PROMPT = "Next";
	private static final String PREV_BUTTON_PROMPT = "Previous";
	private static final String ADD_PANEL_BUTTON_PROMPT = "AddPanel";
	private static final String REMOVE_PANEL_BUTTON_PROMPT = "RemovePanel";
	private static final String SAVE_BUTTON_PROMPT = "Save";

	private String currentFontType;
	private int currentFontSize;

	private List<Pane> paneList;
	private Button nextButton;
	private Button prevButton;
	private Button addPaneButton;
	private Button removePanelButton;
	private Label currentPane;
	private Label totalPanes;
	private HBox dialoguePreview;
	private int currentPaneIndex;

	private SimpleIntegerProperty current;
	private SimpleIntegerProperty totalPaneCount;

	private Runnable save;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;
	private ArrayList<TextArea> taList;
	
	private double oldHeight = 0;
	private Supplier<Color> currentBgColor;

	public DialogueTextAreaView(Runnable save, Supplier <Color> bgColor) {
		currentBgColor = bgColor;
		taList = new ArrayList<TextArea>();
		paneList = new ArrayList<Pane>();
		dialoguePreview = new HBox();
		this.save = save;
		this.setSpacing(15);

		currentPaneIndex = -1;
		current = new SimpleIntegerProperty(0);
		totalPaneCount = new SimpleIntegerProperty(0);
		
		addPane();

		this.getChildren().addAll(dialoguePreview, makeToolPanel());

	}

	/************************ PUBLIC METHODS ***************************/

	public List<TextArea> getDialogueList() {
		return taList;
	}

	public void setFontType(String family) {
		for (TextArea ta : taList) {
			ta.setFont(Font.font(family));
		}
	}

	public void setFontSize(int size) {
		for (TextArea ta : taList) {
			ta.setFont(Font.font(size));
		}
	}
	
	protected void setFontColor(String color) {
		for (TextArea ta: taList) {
			ta.setStyle("-fx-text-fill: " + color + ";")	;
		}
	}
	
	protected void setBackgroundColor(Color color) {
		for (Pane pane : paneList) {
			pane.setBackground(new Background(new BackgroundFill(
                                                                 color,
                                                                 null, null)));
		}
	}
	
	protected void setTextAreaBackgroundColor(Color color) {
		for (TextArea ta : taList) {
			ta.setBackground(new Background(new BackgroundFill(
                                                                 color,
                                                                 null, null)));
		}
	}

	public void removePanel() {

		if (paneList.size()>1) {
			System.out.println(paneList.size());
			System.out.println(currentPaneIndex);

			if (currentPaneIndex == paneList.size() - 1) {
				prev();
				paneList.remove(currentPaneIndex+1);
				
			} else {
				next();
				paneList.remove(currentPaneIndex-1);
				currentPaneIndex -= 1;
				current.set(current.get()-1);
			}
			totalPaneCount.set(totalPaneCount.get()-1);
		}
	}

	public void addPane() {
//		TextArea ta = new TextArea();
//		ta.setPrefSize(DIALOG_PROMPT_WIDTH, DIALOG_PROMPT_HEIGHT);
//		ta.setWrapText(true);
//		taList.add(ta);
//
//		ta.setOnKeyTyped(e -> save.run());
//
//		setCurrentPanel(taList.size() - 1);
		Pane dialoguePane = new Pane();
		dialoguePane.setPrefSize(DIALOG_PROMPT_WIDTH, DIALOG_PROMPT_HEIGHT);
		paneList.add(dialoguePane);
		setBackgroundColor(currentBgColor.get());
		setCurrentPane();
		totalPaneCount.set(totalPaneCount.get()+1);
	}
	
	protected void addTextArea() {
		TextArea ta = new TextArea();
		ta.setPrefSize(25, 25);

		ta.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		ta.setBackground(paneList.get(0).getBackground());
		ta.setWrapText(true);
		String css = this.getClass().getResource("dialogue.css").toExternalForm();
		ta.getStylesheets().add(css);

		taList.add(ta);
		Pane k = (Pane) this.getChildren().get(0);
		k.getChildren().add(ta);
		
		DragResizer draggableTA = new DragResizer(ta);
		draggableTA.makeResizable();
		draggableTA.makeDraggable();
        
	}

	/************************ PRIVATE METHODS ***************************/

	private void setCurrentPane() {
		currentPaneIndex += 1;
		current.set(current.get()+1);
		if (!paneList.isEmpty()) {
			dialoguePreview.getChildren().clear();
		}
		dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
	}

	private void prev() {
		if (currentPaneIndex > 0) {
			currentPaneIndex -= 1;
			System.out.println(currentPaneIndex);
			current.set(current.get()-1);
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
		}
	}

	private void next() {
		if (currentPaneIndex < paneList.size() - 1) {
			currentPaneIndex += 1;
			System.out.println(currentPaneIndex);
			current.set(current.get()+1);
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
		}
	}

	private HBox makeToolPanel() {
		HBox hb = new HBox(150);
		hb.setPrefWidth(DIALOG_PROMPT_WIDTH);
		currentPane = new Label();
		currentPane.textProperty().bind(current.asString());
		Label slash = new Label("/");
		totalPanes = new Label();
		totalPanes.textProperty().bind(totalPaneCount.asString());
		HBox panelCountBox = new HBox();
		panelCountBox.getChildren().addAll(currentPane, slash, totalPanes);
		panelCountBox.setAlignment(Pos.CENTER_RIGHT);
		panelCountBox.setPrefWidth(30);
		hb.getChildren().addAll(makeButtonPanel(), panelCountBox);
		hb.setAlignment(Pos.CENTER);
		return hb;
	}

	private HBox makeButtonPanel() {
		HBox hb = new HBox(15);
		hb.setAlignment(Pos.CENTER_LEFT);
		nextButton = makeButton(NEXT_BUTTON_PROMPT, e -> next());
		prevButton = makeButton(PREV_BUTTON_PROMPT, e -> prev());
		addPaneButton = makeButton(ADD_PANEL_BUTTON_PROMPT, e -> this.addPane());
		removePanelButton = makeButton(REMOVE_PANEL_BUTTON_PROMPT, e -> this.removePanel());
		// change number
		// saveButton = makeButton(SAVE_BUTTON_PROMPT, e -> save(nameTF.getText()));
		hb.getChildren().addAll(prevButton, nextButton, addPaneButton, removePanelButton);
		return hb;
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

}
