package authoring;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Thumbnail extends HBox{
	
	private int HEIGHT = 40;
	private int WIDTH = 100;
	private boolean isClicked;
	
	public Thumbnail(ImageView im, String name){
		setup(im, name);
	}
	
	private void createLabel(String name){
		Label label = new Label(name);
		label.setAlignment(Pos.CENTER);
		this.getChildren().add(label);
	}
	
	private void createImageThumbnail(ImageView im){
		int thumbnail_size = (int) Math.floor(HEIGHT*.8);
		ImageView imView = new ImageView(im.snapshot(null, null));
//		imView.setPreserveRatio(true);
		imView.setFitHeight(thumbnail_size);
		imView.setFitWidth(thumbnail_size);
//		imView.wid
		
		this.getChildren().add(imView);
	}
	
	public void setClicked(boolean in){
		isClicked = in;
		if (in){
			this.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));
//			this.setOpacity(50);
		} else {
			this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
//			this.setOpacity(100);
		}
	}
	
	public boolean isClicked(){
		return isClicked;
	}
	
	private void setup(ImageView im, String name){
		this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		this.setAlignment(Pos.CENTER);
		createImageThumbnail(im);
		Separator s = new Separator();
		s.setOrientation(Orientation.VERTICAL);
		this.getChildren().add(s);
		createLabel(name);
	}

}
