package fr.afcepf.ai.ire.annuaire.vue;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDuPassword extends Application {

	private Group root = new Group();
	private Scene scene = new Scene (root, 800, 600);
	private Stage pass;
	
	@Override
	public void start(final Stage stage) {
		stage.setTitle("IRE Gestion Annuaire");
		pass = new FenetrePassword(stage);
		
		
		
		
		
		
		
		stage.setScene(scene);
		stage.show();
		pass.sizeToScene();
		pass.show();
	}
	
		public static void main(String[] args) {
		Application.launch(args);

	}
	

}
