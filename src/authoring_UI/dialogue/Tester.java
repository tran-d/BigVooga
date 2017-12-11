package authoring_UI.dialogue;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tester extends Application {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @Override
    public void start(Stage primaryStage) throws Exception {
    		Pane overall = new Pane();
        TextArea textarea = new TextArea();
        Pane pane = new Pane(textarea);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, 
	            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        overall.getChildren().add(pane);

        Scene scene = new Scene(overall, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();

        Node textAreaContent = textarea.lookup(".content");
        System.out.println(textAreaContent);
        textAreaContent.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {

            System.out.println("is clicked");

            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = textarea.getTranslateX();
            orgTranslateY = textarea.getTranslateY();

            textarea.toFront();
        });

        textAreaContent.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {

            System.out.println("is dragged");

            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            textarea.setTranslateX(newTranslateX);
            textarea.setTranslateY(newTranslateY);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
