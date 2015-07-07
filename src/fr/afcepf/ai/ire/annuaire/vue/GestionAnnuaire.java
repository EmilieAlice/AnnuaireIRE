package fr.afcepf.ai.ire.annuaire.vue;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GestionAnnuaire {

	private String cheminDuFichierAChercher;
	private String cheminDuFichierASauvegarder;

	public GestionAnnuaire(String cheminDuFichierAChercher,
			String cheminDuFichierASauvegarder, Stage stage) {
		FileChooser fichierAChercher = new FileChooser();
		fichierAChercher.setTitle("Selectionnez un fichier source");
		fichierAChercher.setInitialDirectory(new File(System
				.getProperty("user.home")));

		File fichierChercherSelectionne = fichierAChercher
				.showOpenDialog(stage);
		if (fichierChercherSelectionne != null) {
			cheminDuFichierAChercher = fichierChercherSelectionne.toString();
			cheminDuFichierAChercher = cheminDuFichierAChercher.replace("\\",
					"/");
		}

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
		}

		this.cheminDuFichierAChercher = cheminDuFichierAChercher;
		this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder;
	}

	public GestionAnnuaire() {
		super();
	}

	public String getCheminDuFichierAChercher() {
		return cheminDuFichierAChercher;
	}

	public void setCheminDuFichierAChercher(String cheminDuFichierAChercher) {
		this.cheminDuFichierAChercher = cheminDuFichierAChercher;
	}

	public String getCheminDuFichierASauvegarder() {
		return cheminDuFichierASauvegarder;
	}

	public void setCheminDuFichierASauvegarder(
			String cheminDuFichierASauvegarder) {
		this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder;
	}

}
