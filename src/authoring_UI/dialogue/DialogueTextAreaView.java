package authoring_UI.dialogue;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

	private List<TextArea> taList;
	private List<Pane> paneList;
	private Button nextButton;
	private Button prevButton;
	private Button addPanelButton;
	private Button removePanelButton;
	private int currentPanelIndex = -1;
	private Label currentPanel;
	private Label totalPanels;
	private HBox dialoguePreview;

	private SimpleIntegerProperty curr;
	private SimpleIntegerProperty total;

	private Runnable save;
	private double orgSceneX, orgSceneY;
	private double orgTranslateX, orgTranslateY;

	public DialogueTextAreaView(Runnable save) {
		taList = new ArrayList<>();
		paneList = new ArrayList<>();
		dialoguePreview = new HBox();
		addPanel();
		this.save = save;
		this.setSpacing(15);

		curr = new SimpleIntegerProperty(currentPanelIndex + 1);
		total = new SimpleIntegerProperty(taList.size());

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

	public void removePanel() {

		if (!taList.isEmpty()) {

			if (currentPanelIndex == 0) {
				next();
				if (currentPanelIndex == 0) {
					dialoguePreview.getChildren().clear();
					taList.remove(currentPanelIndex);
				} else
					taList.remove(--currentPanelIndex);
			} else if (currentPanelIndex == taList.size() - 1) {
				prev();
				taList.remove(currentPanelIndex + 1);
			} else {
				next();
				taList.remove(--currentPanelIndex);
			}
		}
	}

	public void addPanel() {
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
		setCurrentPanel(paneList.size() - 1);
		this.getChildren().add(dialoguePane);
	}
	
	protected void addTextArea() {
		TextArea ta = new TextArea();
		ta.setPrefSize(50, 50);
//		ta.setStyle("-fx-background-color: transparent;");
		ta.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		ta.setWrapText(true);
		taList.add(ta);
		Pane k = (Pane) this.getChildren().get(0);
		k.getChildren().add(ta);
		
		DragResizer draggableTA = new DragResizer(ta);
		draggableTA.makeResizable();
		draggableTA.makeDraggable();
		
//        ta.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
//
//            System.out.println("is clicked");
//
//            orgSceneX = e.getSceneX();
//            orgSceneY = e.getSceneY();
//            orgTranslateX = ta.getTranslateX();
//            orgTranslateY = ta.getTranslateY();
//
//            ta.toFront();
//        });
//
//        ta.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
//
//            System.out.println("is dragged");
//
//            double offsetX = e.getSceneX() - orgSceneX;
//            double offsetY = e.getSceneY() - orgSceneY;
//            double newTranslateX = orgTranslateX + offsetX;
//            double newTranslateY = orgTranslateY + offsetY;
//
//            ta.setTranslateX(newTranslateX);
//            ta.setTranslateY(newTranslateY);
//        });
        
	}

	/************************ PRIVATE METHODS ***************************/

	private void setCurrentPanel(int index) {
		dialoguePreview.getChildren().clear();
		dialoguePreview.getChildren().add(paneList.get(index));
		currentPanelIndex = index;
	}

	private void prev() {
		if (currentPanelIndex > 0) {
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(taList.get(--currentPanelIndex));
		}
	}

	private void next() {
		if (currentPanelIndex < taList.size() - 1) {
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(taList.get(++currentPanelIndex));
		}
	}

	private HBox makeToolPanel() {
		HBox hb = new HBox();
		hb.setPrefWidth(DIALOG_PROMPT_WIDTH);
		currentPanel = new Label();
		currentPanel.textProperty().bind(curr.asString());
		Label slash = new Label("/");
		totalPanels = new Label();
		totalPanels.textProperty().bind(total.asString());
		hb.getChildren().addAll(makeButtonPanel(), currentPanel, slash, totalPanels);
		return hb;
	}

	private HBox makeButtonPanel() {
		HBox hb = new HBox(15);
		hb.setPrefWidth(DIALOG_PROMPT_WIDTH * 0.90);
		nextButton = makeButton(NEXT_BUTTON_PROMPT, e -> next());
		prevButton = makeButton(PREV_BUTTON_PROMPT, e -> prev());
		addPanelButton = makeButton(ADD_PANEL_BUTTON_PROMPT, e -> this.addPanel());
		removePanelButton = makeButton(REMOVE_PANEL_BUTTON_PROMPT, e -> this.removePanel());
		// change number
		// saveButton = makeButton(SAVE_BUTTON_PROMPT, e -> save(nameTF.getText()));
		hb.getChildren().addAll(prevButton, nextButton, addPanelButton, removePanelButton);
		return hb;
	}

	private Button makeButton(String name, EventHandler<ActionEvent> handler) {
		Button btn = new Button();
		btn.textProperty().bind(DisplayLanguage.createStringBinding(name));
		btn.setOnAction(handler);
		return btn;
	}

}
