package authoring_UI;

import java.util.ArrayList;
import java.util.stream.Collectors;

import authoring.AbstractSpriteObject;
import authoring.AnimationSequence;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SpriteAnimationSequenceTabsAndInfo {
	
	private AbstractSpriteObject mySO;
	private ScrollPane containerScrollPane;
	private ArrayList<AnimationSequence> animationsSequences;
	private VBox containerVbox;
	private TabPane containerTabPane;
	private HBox addAnimationSequenceHbox;
	private Button addAnimationSequenceButton;
	private Button createAnimationSequenceButton;
	private TextField promptNewName;
	private Label promptNameLabel;
	
	
	SpriteAnimationSequenceTabsAndInfo(){
		initialize();
	}
	
	public void setSpriteObject(AbstractSpriteObject SO){
		mySO = SO;
		clearAnimationSequencesList();
		this.clearExisting();
		SO.getAnimationSequences().forEach(AS->{
			System.out.println("AnimationSequence: "+AS);
			this.addAnimationSequence(AS);
		});
		
	}
	
	private void initialize(){
		createAddAnimationSequenceHbox();
		createAddAnimationSequenceButton();
		createCreateAnimationSequenceButton();
		createPromptNameText();
		createPromptNameLabel();
		putAddAnimationSequenceButtonIntoHbox();
		
		
		initializeAnimationSequencesList();
		
		createScrollPane();
		createContainerVBox();
		createAnimationTabPane();
		
		
		putTabPaneAndHboxIntoContainerVbox();
		
		putVBoxIntoScrollPane();
	}
	
	private void initializeAnimationSequencesList(){
		 animationsSequences = new ArrayList<AnimationSequence>();
	}
	
	private void clearAnimationSequencesList(){
		animationsSequences.clear();
	}
	
	private void createAnimationTabPane(){
		containerTabPane = new TabPane();
		containerTabPane.setSide(Side.RIGHT);
		containerTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}
//	private TabPane getAnimationTabPane(){
//		return containerTabPane;
//	}
	
	private void clearExisting(){
		containerTabPane.getTabs().clear();
	}
	
	private void putTabPaneAndHboxIntoContainerVbox(){
//		if (containerTabPane == null){
//			createAnimationTabPane();
//		} 
//		if (addAnimationSequenceHbox==null){
//			this.createAddAnimationSequenceHbox();
//		}
		containerVbox.getChildren().addAll(containerTabPane, new Separator(), addAnimationSequenceHbox);
	}
	
	private void addToVBox(Pane pane){
		containerVbox.getChildren().add(pane);
	}
	
	private void clearVBox(){
		containerVbox.getChildren().clear();
	}
	
	public Pane getContainerVBox(){
		System.out.println("getting container vbox");
		return containerVbox;
	}
	
	private void createContainerVBox(){
		containerVbox = new VBox(10);
		containerVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
	}
	
	private void createScrollPane(){
		System.out.println("Container Scroll Pane");
		containerScrollPane = new ScrollPane();
	}
	public ScrollPane getScrollPane(){
		System.out.println("getting scroll pane");
		System.out.println("Content: "+((VBox)containerScrollPane.getContent()).getChildren());
		return containerScrollPane;
	}
	
	public void putVBoxIntoScrollPane(){
		containerScrollPane.setContent(getContainerVBox());
	}
	
	private void createAddAnimationSequenceHbox(){
		addAnimationSequenceHbox = new HBox(10);
	}
	
	
	private void createAddAnimationSequenceButton(){
		addAnimationSequenceButton = new Button("Add Animation Sequence");
		addAnimationSequenceButton.setOnAction(event->{
			this.removeAddAnimationSequenceButtonFromHbox();
			this.addPromptNewNameAndCreateButtonToHbox();
		});
		
	}
	
	private void createCreateAnimationSequenceButton(){
		createAnimationSequenceButton = new Button("Create Animation Sequence");
		createAnimationSequenceButton.setOnAction(event->{
			
			String animationSeqName = promptNewName.getText();
			if (!nameIsValid(animationSeqName)){
				promptNewName.setText("This name already used");
			} else {
				AnimationSequence newSequence = new AnimationSequence(animationSeqName);
				Tab newTab = addAnimationSequence(newSequence);
				this.containerTabPane.getSelectionModel().select(newTab);
				this.removePromptNewNameAndCreateButtonToHbox();
				this.putAddAnimationSequenceButtonIntoHbox();
				
			}
			
		});
		
	}
	
	private boolean nameIsValid(String newName){
		return !mySO.getAnimationSequences().stream()
			.anyMatch(AS->AS.getName().equals(newName));
			
	}
	
	
	
	
	private Tab addAnimationSequence(AnimationSequence AS){
		this.animationsSequences.add(AS);
		Tab tab = new Tab();
		tab.setText(AS.getName());
		tab.setContent(AS.getUIContent());
		this.containerTabPane.getTabs().add(tab);
		return tab;
	}
	
	private void createPromptNameLabel(){
		promptNameLabel = new Label("AnimationSequence Name:");
		
	}
	
	private void createPromptNameText(){
		promptNewName = new TextField();

	}
	
	private void putAddAnimationSequenceButtonIntoHbox(){
		addAnimationSequenceHbox.getChildren().clear();
		this.addAnimationSequenceHbox.getChildren().add(addAnimationSequenceButton);
	}
	
	private void removeAddAnimationSequenceButtonFromHbox(){
		this.addAnimationSequenceHbox.getChildren().remove(addAnimationSequenceButton);
	}
	
	private void addPromptNewNameAndCreateButtonToHbox(){
		this.addAnimationSequenceHbox.getChildren().addAll(promptNameLabel, promptNewName, createAnimationSequenceButton);
	}
	
	private void removePromptNewNameAndCreateButtonToHbox(){
		this.addAnimationSequenceHbox.getChildren().removeAll(promptNameLabel, promptNewName, createAnimationSequenceButton);
	}
	
	public void apply(){
		mySO.setAnimationSequences(this.animationsSequences);
	}
	
	
	
	
	
}
