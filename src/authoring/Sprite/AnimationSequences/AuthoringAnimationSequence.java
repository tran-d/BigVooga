package authoring.Sprite.AnimationSequences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import authoring.Thumbnail;
import authoring_data.SerializableAuthoringImageView;
import engine.utilities.data.GameDataHandler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class AuthoringAnimationSequence {

	private String myName;
	private ArrayList<AnimationSequenceImage> myImages;
	private ScrollPane myScrollPane;
	private VBox myContainerVbox;
	private VBox outMostVbox;
	private ObjectProperty<AnimationSequenceImage> myMostRecentlyAddedAnimationSequenceProperty;
	private boolean showUI;
	private Button addNewImage;

	public AuthoringAnimationSequence(String name) {
		myName = name;
		initialize();
	}

	public AuthoringAnimationSequence(AuthoringAnimationSequence AS) {
		initialize();
		myName = new String(AS.getName());
		AS.getImages().forEach(aasequence->{
			myImages.add(new AnimationSequenceImage(aasequence));
		});
//		myImages = AS.getImages();
	}

	public AuthoringAnimationSequence(String name, AuthoringImageView AEI) {
		this(name);
		addNewAuthoringImageViewToSequence(AEI);
	}
	
	public AuthoringAnimationSequence(String name, List<AnimationSequenceImage> images) {
		this(name);
		images.forEach(image -> {
			myImages.add(image);
		});
	}

	public AuthoringAnimationSequence(String name, ArrayList<AuthoringImageView> images) {
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

	public void addNewAuthoringImageViewToSequence(AuthoringImageView AEI) {
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
	
	public VBox getUIContent(){
		showUI();
		if (this.outMostVbox==null){
			outMostVbox = new VBox();
			outMostVbox.getChildren().addAll(getScrollPane());
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
		myScrollPane.setPrefHeight(200);
		myScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		
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
		myContainerVbox = new VBox();
		myContainerVbox.setPrefWidth(495);
		
	}

	private void addAllAnimationSequenceImageThumbnails() {
		for (AnimationSequenceImage ASI : this.getImages()) {
			addNewAnimationSequenceToUI(ASI);
		}
//		int counter = 1;
//		for (AnimationSequenceImage ASI : this.getImages()) {
//			String label = "Image " + Integer.toString(counter);
//			addNewAnimationSequenceToUI(ASI);
//			counter++;
//		}
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI, String label) {
		
		Thumbnail th = new Thumbnail(ASI.getImage(), label);
		th.addSideButton("Remove from animation");
		th.setSideButtonRunnable(()->{
			myContainerVbox.getChildren().remove(th);
			this.myImages.remove(ASI);
		});
		
		myContainerVbox.getChildren().add(th);
	}

	private void addNewAnimationSequenceToUI(AnimationSequenceImage ASI) {
		int vboxSize = myContainerVbox.getChildren().size();
		;
		Thumbnail th = new Thumbnail(ASI.getImage(), "Image "+Integer.toString(vboxSize));
		VBox animationBox = new VBox();
		animationBox.getChildren().addAll(th, new Separator());
		th.addSideButton("Remove from animation");
		th.setSideButtonRunnable(()->{
			if(!(this.getName().equals("Default")&&this.myContainerVbox.getChildren().size()==1)){
			myContainerVbox.getChildren().remove(animationBox);
			this.myImages.remove(ASI);
			}
		});
		
		myContainerVbox.getChildren().add(animationBox);
	}

	
	private Object writeReplace() throws java.io.ObjectStreamException {
		return new SerializableAuthoringAnimationSequence(this);
	}
}
