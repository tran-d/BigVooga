package authoring;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tester extends Application {
	private int SCENE_HEIGHT = 500;
	private int SCENE_WIDTH = 500;
	Stage MAIN_STAGE;
	private Scene MAIN_SCENE;
	private int FRAMESPEED = 1000 / 60;
	Timeline TIMELINE;
	SpriteParameterFactory SPF = new SpriteParameterFactory();
	SpriteParameterSidebarManager MAN = new SpriteParameterSidebarManager();

	ArrayList<SpriteParameterI> myParams;
	ArrayList<SpriteObjectI> mySObjects = new ArrayList<SpriteObjectI>();
	AuthoringEnvironmentManager AEM = new AuthoringEnvironmentManager();
	SpriteObjectGridManagerI SOGM = AEM.getGridManager();
	

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Tester2 t = new Tester2();
//		t.main(new String[]{"hello"});
		getParams();
//		setup();
//		ArrayList<HBox> hboxList = new ArrayList<HBox>();
//		for (SpriteParameterI SP : myParams) {
//			HBox newHBox = new HBox();
//
//			newHBox.getChildren().addAll(SP.getJavaFXNameNode(), SP.getJavaFXValueNode());
//			hboxList.add(newHBox);
//		}
//		VBox vbox = new VBox(0);
//		vbox.setStyle("-fx-border-style: solid;" + "-fx-border-width: 0;" + "-fx-border-color: black");
//		vbox.getChildren().addAll(hboxList);
		
//		GridPane GP = new GridPane();
//		GP.set
		
		VBox ScrollPaneBox = new VBox();

//		ScrollPane SP = MAN.getParameters(mySObjects);

		MAIN_SCENE = new Scene(ScrollPaneBox, SCENE_WIDTH, SCENE_HEIGHT);
		MAIN_STAGE = primaryStage;
		primaryStage.setTitle("Hello World!");
		primaryStage.setHeight(SCENE_HEIGHT);
		primaryStage.setScene(MAIN_SCENE);
		primaryStage.show();

		TIMELINE = new Timeline();
		TIMELINE.setCycleCount(TIMELINE.INDEFINITE);
		Duration time = Duration.millis(FRAMESPEED);

		
		KeyFrame kf = new KeyFrame(time, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
//				double i = Math.random();
//				if (i>.95){
//					System.out.println("i = "+i);
//					for (SpriteObjectI SBI: mySObjects) {
//						System.out.println("\nNew object");
//						for (String cat: SBI.getParameters().keySet()){
//							System.out.println("Category: "+cat);
//							for(SpriteParameterI SPI: SBI.getParameters().get(cat)){
//								System.out.println("ParamName: "+SPI.getName()+" ParamValue: "+SPI.getValue());
//							}
//						}
//					}
//				}
				
				// HBox hb = (HBox) vbox.getChildren().get(1);
				// TextArea TA = (TextArea) hb.getChildren().get(1);
				// int newHealth = Integer.parseInt(TA.getText());
				// System.out.println(newHealth);
			}
		});

		TIMELINE.getKeyFrames().add(kf);
		TIMELINE.play();

	}
	
	private void setup() {
		
	}

	public void getParams() {
		int i = 10;
		ArrayList<String> s = new ArrayList<String>();
		s.add("hello");
		s.add("world");
		s.add("bye");
		for (int h = 0; h < 3; h++) {
			SpriteObject SO = new SpriteObject();
			myParams = new ArrayList<SpriteParameterI>();
			myParams.add(SPF.makeParameter("canFight", true));
			myParams.add(SPF.makeParameter("health", i));
			myParams.add(SPF.makeParameter("name", s.get(0)));
//			for (SpriteParameterI SP : myParams) {
//				System.out.println(SP.getName());
//				System.out.println(SP.getClass());
//			}
			for (SpriteParameterI SP: myParams){
				SO.addParameter(SP);
			}
//			mySObjects.add(SO);
			Integer [] loc1 = new Integer[]{h^2,3*h};
			Integer [] loc2 = new Integer[]{h^2,5*h};
			Integer [] loc3 = new Integer[]{h,4*h};
			ArrayList<Integer[]> locs = new ArrayList<Integer[]>();
			locs.add(loc1);
			locs.add(loc2);
			locs.add(loc3);
			SOGM.populateCell(SO, locs);
//			i*=2;
		}
	}

}
