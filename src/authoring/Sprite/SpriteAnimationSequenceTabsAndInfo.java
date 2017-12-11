package authoring.Sprite;

import java.util.ArrayList;

import authoring.Sprite.AnimationSequences.AuthoringAnimationSequence;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
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
	private VBox containerScrollPane;
	private ArrayList<AuthoringAnimationSequence> animationsSequences;
	private VBox containerVbox;
	private TabPane containerTabPane;
	private HBox addAnimationSequenceHbox;
	private Button addAnimationSequenceButton;
	private Button createAnimationSequenceButton;
	private TextField promptNewName;
	private Label promptNameLabel;
	private Button addImage;
	private VBox animationVBox;
	
	
	public SpriteAnimationSequenceTabsAndInfo(){
		initialize();
	}
	
	public void setSpriteObject(AbstractSpriteObject SO){
		mySO = SO;
		clearAnimationSequencesList();
		this.clearExisting();
		SO.getAnimationSequences().forEach(AS->{
			System.out.println("AnimationSequence: " + AS);
			this.addAnimationSequence(AS);
		}); 
		
	}
	
	private void initialize(){
		
		createAnimationSequenceButtons();		
		
		initializeAnimationSequencesList();
		
		createScrollPane();
		createContainerVBox();
		createAnimationTabPane();
		
		putVBoxIntoScrollPane();
	}
	
	private void createAnimationSequenceButtons() {
		createAddAnimationSequenceHbox();
		createAddAnimationSequenceButton();
		createCreateAnimationSequenceButton();
		createPromptNameText();
		createPromptNameLabel();
		putAddAnimationSequenceButtonIntoHbox();
	}

	private void initializeAnimationSequencesList(){
		 animationsSequences = new ArrayList<AuthoringAnimationSequence>();
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
	
	public void clearExisting(){
		containerTabPane.getTabs().clear();
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
	
	public HBox createHBox(){
		createAnimationSequenceButtons();
		return addAnimationSequenceHbox;
	}
	
	private void createContainerVBox(){
		containerVbox = new VBox(10);
		containerVbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
	}
	
	private void createScrollPane(){
		System.out.println("Container Scroll Pane");
		containerScrollPane = new VBox();
	}

	public VBox getAnimationBox(){
		System.out.println("getting scroll pane");
		//System.out.println("Content: "+((VBox)containerScrollPane.getContent()).getChildren());
		animationVBox = new VBox(10);
		return animationVBox;
	}
	
	public void putVBoxIntoScrollPane(){
		containerScrollPane.getChildren().add(getContainerVBox());
	}
	
	private void createAddAnimationSequenceHbox(){
		addAnimationSequenceHbox = new HBox(10);
		addAnimationSequenceHbox.setAlignment(Pos.CENTER);
	}
	
	
	private void createAddAnimationSequenceButton(){
		addAnimationSequenceButton = new Button("Create Animation Sequence");
		addAnimationSequenceButton.setOnAction(event->{
			this.removeAddAnimationSequenceButtonFromHbox();
			this.addPromptNewNameAndCreateButtonToHbox();
		});
		
	}
	
	private void createCreateAnimationSequenceButton(){
		createAnimationSequenceButton = new Button("Create");
		createAnimationSequenceButton.setOnAction(event->{
			
			String animationSeqName = promptNewName.getText();
			if (!nameIsValid(animationSeqName)){
				promptNewName.setText("This Name Already Used");
			} else {
				AuthoringAnimationSequence newSequence = new AuthoringAnimationSequence(animationSeqName);
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
	
	private Tab addAnimationSequence(AuthoringAnimationSequence AS){
		animationVBox.getChildren().clear();
		this.removePromptNewNameAndCreateButtonToHbox();
		this.putAddAnimationSequenceButtonIntoHbox();
		this.animationsSequences.add(AS);
		Tab tab = new Tab();
		tab.setText(AS.getName());
		tab.setContent(AS.getUIContent());
		addImage = AS.getAddImageButton();
		animationVBox.getChildren().add(containerTabPane);
		animationVBox.setAlignment(Pos.CENTER);
		animationVBox.getChildren().addAll(addImage, createHBox());
		containerTabPane.getTabs().add(tab);
		return tab;
	}
	
	private void createPromptNameLabel(){
		promptNameLabel = new Label("Animation Sequence Name:");
		
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
		//mySO.setAnimationSequences(this.animationsSequences);
	}
	
	
	
	
	
}
