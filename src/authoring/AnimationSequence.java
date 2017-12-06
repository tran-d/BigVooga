package authoring;

import java.io.File;
import java.util.ArrayList;

import authoring_UI.AuthoringImageView;
import engine.utilities.data.GameDataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AnimationSequence {

	private String myName;
	private ArrayList<AnimationSequenceImage> myImages;
	private ScrollPane myScrollPane;
	private VBox myContainerVbox;
	private VBox outMostVbox;
	private ObjectProperty<AnimationSequenceImage> myMostRecentlyAddedAnimationSequenceProperty;
	private boolean showUI;
	private Button addNewImage;

	public AnimationSequence(String name) {
		myName = name;
		initialize();
	}

	AnimationSequence(AnimationSequence AS) {
		myName = AS.getName();
		myImages = AS.getImages();
	}

	AnimationSequence(String name, AuthoringImageView AEI) {
		this(name);
		addNewAuthoringImageViewToSequence(AEI);
	}

	AnimationSequence(String name, ArrayList<AuthoringImageView> images) {
		this(name);
		images.forEach(image -> {
			addNewAuthoringImageViewToSequence(image);
		});
	}

	private void initialize() {

		myImages = new ArrayList<AnimationSequenceImage>();
	}

	// private void createAnimationImagesProperty(){
	// myImagesProperty = new
	// SimpleObjectProperty<ArrayList<AnimationSequenceImage>>();
	// myImagesProperty.addListener((observable, oldList, newList)->{
	//
	// if (showUI){
	//
	// }
	//
	// });
	// }

	private void addNewAuthoringImageViewToSequence(AuthoringImageView AEI) {
		if (myMostRecentlyAddedAnimationSequenceProperty == null) {
			myMostRecentlyAddedAnimationSequenceProperty = new SimpleObjectProperty<AnimationSequenceImage>();
			
		myMostRecentlyAddedAnimationSequenceProperty.addListener((observable, oldASI, newASI) -> {
				if (showUI) {
					this.addNewAnimationSequenceToUI(newASI);
				}
				this.myImages.add(newASI);
			});
		
		}
		
		myMostRecentlyAddedAnimationSequenceProperty.set(new AnimationSequenceImage(AEI));
		
		

	}

//	private void addAuthoringImageView(AuthoringImageView AEI) {
//		myImages.add(new AnimationSequenceImage(AEI));
//	}

	public String getName() {
		return myName;
	}

	public ArrayList<AnimationSequenceImage> getImages() {
		return myImages;
	}
	
	public Pane getUIContent(){
		showUI();
		if (this.outMostVbox==null){
			outMostVbox  = new VBox(5);
			outMostVbox.getChildren().add(getScrollPane());
			outMostVbox.getChildren().add(new Separator());
			outMostVbox.getChildren().add(this.getAddImageButton());
		}
		return outMostVbox;
	}

	public ScrollPane getScrollPane() {
		
		if (myScrollPane == null) {
			createScrollPane();
			putContainerVBoxIntoScrollPane();
		}
		return myScrollPane;
	}
	
	public void showUI(){
		showUI = true;
	}

	private void createScrollPane() {
		myScrollPane = new ScrollPane();
		
	}
	
	private void putContainerVBoxIntoScrollPane(){
		myScrollPane.setContent(getContainerVbox());
	}

	private VBox getContainerVbox() {
		if (myContainerVbox == null) {
			createContainerVbox();
			addAllAnimationSequenceImageThumbnails();
		}
		return myContainerVbox;
	}

	private void createContainerVbox() {
		myContainerVbox = new VBox(10);
	}

	private void addAllAnimationSequenceImageThumbnails() {
		int counter = 1;
		for (AnimationSequenceImage ASI : this.getImages()) {
			String label = "Image " + Integer.toString(counter);
			myContainerVbox.getChildren().add(new Thumbnail(ASI.getImage(), label));
			counter++;
		}
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI, String label) {
		myContainerVbox.getChildren().add(new Thumbnail(ASI.getImage(), label));
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI) {
		int vboxSize = myContainerVbox.getChildren().size();
		System.out.println("Adding to AnSeq");
		myContainerVbox.getChildren().add(new Thumbnail(ASI.getImage(), Integer.toString(vboxSize)));
	}
	
	private void createAddImageButton(){
		addNewImage = new Button("Add Image");
		addNewImage.setOnAction(event->{
			Node parent = myScrollPane.getParent();
			Scene s = parent.getScene();
			while (s == null) {
				parent = parent.getParent();
				s = parent.getScene();
			}
			
			File file = GameDataHandler.chooseFileForImageSave(s.getWindow());
//			System.out.println(file.getName());
//			Image im = new Image("/"+file.getName());
//			Image im = new Image("/"+"brick.png");
//			System.out.println("Image loaded: "+im);
			
			String testFile = File.separator+file.getName();
			System.out.println(testFile);
			AuthoringImageView AIV = new AuthoringImageView(testFile);
			addNewAuthoringImageViewToSequence(AIV);
		});
	}
	
	private Button getAddImageButton(){
		if (addNewImage==null){
			createAddImageButton();
		}
		return addNewImage;
	}
}
