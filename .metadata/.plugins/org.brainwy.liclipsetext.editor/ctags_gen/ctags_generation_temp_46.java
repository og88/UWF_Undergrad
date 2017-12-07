package model;

import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
public class JavaLeagueTactics
{
	void main()
	{
		Menu();
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public JavaLeagueTactics(){
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void Menu() {
		Menu.launch();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	public void Game(Stage stage) {
		// TODO implement me
	}
	
	public static void newGame(Stage stage)
	{
	stage.setTitle("");	
	
	Button btn = new Button();
		Button btn0 = new Button();
		Button btn1 = new Button();
		
	btn.setText("Easy");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});

		btn0.setText("Medium");
		btn0.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			}
		});

		btn1.setText("Hard");
		btn1.setOnAction(new EventHandler<ActionEvent>() {
@Override
			public void handle(ActionEvent event) {
			}
		});
		Group root = new Group();
		btn.setLayoutX(115);
		btn.setLayoutY(50);

		btn0.setLayoutX(115);
		btn0.setLayoutY(100);

		btn1.setLayoutX(140);
		btn1.setLayoutY(150);

		root.getChildren().add(btn);
		root.getChildren().add(btn0);
		root.getChildren().add(btn1);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}

}
