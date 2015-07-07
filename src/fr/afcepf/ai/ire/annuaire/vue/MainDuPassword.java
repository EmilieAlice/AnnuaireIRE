package fr.afcepf.ai.ire.annuaire.vue;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDuPassword extends Application {

	@Override
	public void start(final Stage stage) {
		stage.setTitle("Annuaire");
		Group root = new Group();
		Scene scene = new Scene (root, 800, 600);
		
		stage.setScene(scene);
		stage.show();
		
		Stage pass = new FenetrePassword(stage);
		pass.sizeToScene();
		pass.show();

	}
	
		public static void main(String[] args) {
		Application.launch(args);

	}
	

}
