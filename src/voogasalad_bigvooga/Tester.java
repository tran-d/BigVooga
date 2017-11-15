package voogasalad_bigvooga;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Tester2 t = new Tester2();
//		t.main(new String[]{"hello"});
		getParams();
		ArrayList<HBox> hboxList = new ArrayList<HBox>();
		for (SpriteParameterI SP : myParams) {
			HBox newHBox = new HBox();

			newHBox.getChildren().addAll(SP.getJavaFXNameNode(), SP.getJavaFXValueNode());
			hboxList.add(newHBox);
		}
		VBox vbox = new VBox(0);
		vbox.setStyle("-fx-border-style: solid;" + "-fx-border-width: 0;" + "-fx-border-color: black");
		vbox.getChildren().addAll(hboxList);

		ScrollPane SP = MAN.getParameters(mySObjects.get(1));

		MAIN_SCENE = new Scene(SP, SCENE_WIDTH, SCENE_HEIGHT);
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
				// HBox hb = (HBox) vbox.getChildren().get(1);
				// TextArea TA = (TextArea) hb.getChildren().get(1);
				// int newHealth = Integer.parseInt(TA.getText());
				// System.out.println(newHealth);
			}
		});

		TIMELINE.getKeyFrames().add(kf);
		TIMELINE.play();

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
			for (SpriteParameterI SP : myParams) {
				System.out.println(SP.getName());
				System.out.println(SP.getClass());
			}
			for (SpriteParameterI SP: myParams){
				SO.addParameter(SP);
			}
			mySObjects.add(SO);
//			i*=2;
		}
	}

}
