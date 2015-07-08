package fr.afcepf.ai.ire.annuaire.vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class IREGestionAnnuaire extends Application{

	private BorderPane panelPrincipal = new BorderPane();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Scene scene = new Scene(panelPrincipal, 1000, 600);
		stage.setTitle("IRE Gestion Annuaire");
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}