package fr.afcepf.ai.ire.annuaire.vue;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GestionAnnuaire extends BorderPane {

	private String cheminDuFichierAChercher;
	private String cheminDuFichierASauvegarder;

	public GestionAnnuaire(final Stage stage) {

		FileChooser fichierAChercher = new FileChooser();
		fichierAChercher.setTitle("Selectionnez un fichier source");
		fichierAChercher.setInitialDirectory(new File(System
				.getProperty("user.home")));

		File fichierChercherSelectionne = fichierAChercher
				.showOpenDialog(stage);
		if (fichierChercherSelectionne != null) {
			cheminDuFichierAChercher = fichierChercherSelectionne.toString();
			this.cheminDuFichierAChercher = cheminDuFichierAChercher.replace(
					"\\", "/");
		}

		FileChooser fichierASauvegarder = new FileChooser();
		fichierASauvegarder
				.setTitle("Selectionnez un emplacement pour sauvegarder le fichier");
		fichierASauvegarder.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fichierASauvegarder.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text bin(*.bin)", "*.bin"));

		File fichierSaveSelectionne = fichierASauvegarder.showSaveDialog(stage);

		if (!fichierSaveSelectionne.getName().contains(".")) {
			cheminDuFichierASauvegarder = fichierSaveSelectionne
					.getAbsolutePath() + ".bin";
			this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder
					.replace("\\", "/");
		}

	}

	public GestionAnnuaire() {
		super();
	}

	public String getCheminDuFichierAChercher() {
		return cheminDuFichierAChercher;
	}

	public void setCheminDuFichierAChercher(Stage stage) {
		FileChooser fichierAChercher = new FileChooser();
		fichierAChercher.setTitle("Selectionnez un fichier source");
		fichierAChercher.setInitialDirectory(new File(System
				.getProperty("user.home")));

		File fichierChercherSelectionne = fichierAChercher
				.showOpenDialog(stage);
		if (fichierChercherSelectionne != null) {
			cheminDuFichierAChercher = fichierChercherSelectionne.toString();
			this.cheminDuFichierAChercher = cheminDuFichierAChercher.replace(
					"\\", "/");
		}
	}

	public String getCheminDuFichierASauvegarder() {
		return cheminDuFichierASauvegarder;
	}

	public void setCheminDuFichierASauvegarder(Stage stage) {
		FileChooser fichierASauvegarder = new FileChooser();
		fichierASauvegarder
				.setTitle("Selectionnez un emplacement pour sauvegarder le fichier");
		fichierASauvegarder.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fichierASauvegarder.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text bin(*.bin)", "*.bin"));

		File fichierSaveSelectionne = fichierASauvegarder
				.showSaveDialog(new Stage());

		if (!fichierSaveSelectionne.getName().contains(".")) {
			cheminDuFichierASauvegarder = fichierSaveSelectionne
					.getAbsolutePath().toString() + ".bin";
			System.out.println(cheminDuFichierASauvegarder);
			this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder
					.replace("\\", "/");
		}
	}

}
