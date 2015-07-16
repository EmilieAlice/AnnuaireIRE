package fr.afcepf.ai.ire.annuaire.vue;

import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GestionAnnuaire extends BorderPane {

	private String cheminDuFichierAChercher, cheminDuFichierASauvegarder,
			nomDuFichierSelectionne, nomDuFichierSauv;

	public GestionAnnuaire(final Stage stage) throws FileNotFoundException {

		FileChooser fichierAChercher = new FileChooser();
		fichierAChercher.setTitle("Selectionnez un fichier source");
		fichierAChercher.setInitialDirectory(new File(System
				.getProperty("user.home")));

		File fichierChercherSelectionne = fichierAChercher
				.showOpenDialog(stage);
		if (fichierChercherSelectionne != null) {
			this.nomDuFichierSelectionne = fichierChercherSelectionne.getName();
			cheminDuFichierAChercher = fichierChercherSelectionne.toString();
			this.cheminDuFichierAChercher = cheminDuFichierAChercher.replace(
					"\\", "/");
		} else {
			JOptionPane.showMessageDialog(null,
					"Pas de fichier source selectionné");
		}

		FileChooser fichierASauvegarder = new FileChooser();
		fichierASauvegarder
				.setTitle("Selectionnez un emplacement pour sauvegarder le fichier");
		fichierASauvegarder.setInitialDirectory(new File(System
				.getProperty("user.home")));
		fichierASauvegarder.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("Text bin(*.bin)", "*.bin"));

		File fichierSaveSelectionne = fichierASauvegarder.showSaveDialog(stage);
		if (fichierSaveSelectionne != null) {
			cheminDuFichierASauvegarder = fichierSaveSelectionne
					.getAbsolutePath();
			if (!fichierSaveSelectionne.getName().contains(".")) {
				cheminDuFichierASauvegarder = cheminDuFichierASauvegarder
						+ ".bin";
			}
			this.nomDuFichierSauv = fichierSaveSelectionne.getName() + ".bin";
			this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder
					.replace("\\", "/");
		} else {
			JOptionPane.showMessageDialog(null, "Pas de fichier annuaire créé");
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
			this.cheminDuFichierASauvegarder = cheminDuFichierASauvegarder
					.replace("\\", "/");
		}
	}

	public String getNomDuFichierSelectionne() {
		return nomDuFichierSelectionne;
	}

	public void setNomDuFichierSelectionne(String nomDuFichierSelectionne) {
		this.nomDuFichierSelectionne = nomDuFichierSelectionne;
	}

	public String getNomDuFichierSauv() {
		return nomDuFichierSauv;
	}

	public void setNomDuFichierSauv(String nomDuFichierSauv) {
		this.nomDuFichierSauv = nomDuFichierSauv;
	}

}
