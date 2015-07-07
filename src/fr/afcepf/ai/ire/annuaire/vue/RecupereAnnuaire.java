package fr.afcepf.ai.ire.annuaire.vue;

import java.io.File;

import fr.afcepf.ai.ire.modele.PanelDeGestion;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RecupereAnnuaire extends Application{

	private BorderPane panelPrincipal = new BorderPane();
	HBox boxH = new HBox();
	GridPane panelG = new PanelDeGestion();
	Button btnGestion = new Button("Gestion");
	Button btnConsulter = new Button("Consulter l'annuaire");

	String chemin;

	@Override
	public void start(Stage stage) throws Exception {

		FileChooser fc = new FileChooser();
		fc.setTitle("Selectionnez un fichier source");
		fc.setInitialDirectory(new File(System.getProperty("user.home"))); // config
																			// pour
																			// ouverture
																			// en
																			// local
																			// -->
																			// l�
																			// o�
																			// on
																			// travaille

		// fc.getExtensionFilters().addAll(new
		// FileChooser.ExtensionFilter("DON", ".don")); //ouvrir que les
		// fichiers en .don

		File fichierSelectionne = fc.showOpenDialog(stage); // fenetre de
															// dialogue pour
															// choisir un
															// fichier

		if (fichierSelectionne != null) {
			chemin = fichierSelectionne.toString();
			chemin = chemin.replace("\\", "/");

			System.out.println(chemin);
		}

		boxH.getChildren().addAll(btnGestion, btnConsulter);

		btnGestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				panelPrincipal.setCenter(panelG);
				panelG.setAlignment(Pos.CENTER);

			}
		});

		panelPrincipal.setLeft(boxH);

		Scene scene = new Scene(panelPrincipal, 800, 600);
		stage.setScene(scene);
		stage.setTitle("AnnuaireIRE");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
