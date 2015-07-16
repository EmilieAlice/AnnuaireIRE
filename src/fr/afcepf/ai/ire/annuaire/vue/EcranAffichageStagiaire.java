package fr.afcepf.ai.ire.annuaire.vue;

import java.io.IOException;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.modele.CreationAjoutArbreBinaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EcranAffichageStagiaire extends BorderPane {

	private BorderPane panelPrincipal = this;
	private BorderPane panelTop = new BorderPane();
	private Label message = new Label(
			"Veuillez selectionner un annuaire à afficher");

	private HBox menus = new HBox();
	private Button btnGererAnnuaire = new Button("Choisir annuaire");
	private Button btnAffiche = new Button("Annuaire");
	private Button btnDeconnexion = new Button("Deconnexion");

	private CreationAjoutArbreBinaire arbreBin = new CreationAjoutArbreBinaire();
	private GestionAnnuaire choixAnnuaire;
	private BorderPane panelAffiche;

	private Scene scene = new Scene(panelPrincipal, 1200, 650);

	public EcranAffichageStagiaire(final Stage stage) {
		message.setFont(Font.font("Verdana", 20));
		panelPrincipal.setCenter(message);

		btnGererAnnuaire.setPrefSize(400, 45);
		btnGererAnnuaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					choixAnnuaire = new GestionAnnuaire(stage);
					if (choixAnnuaire.getCheminDuFichierAChercher() == null
							|| choixAnnuaire.getCheminDuFichierASauvegarder() == null) {
						JOptionPane.showMessageDialog(null,
								"Aucun annuaire selectionné");
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal.setCenter(message);
					} else {
						arbreBin.init(
								choixAnnuaire.getCheminDuFichierAChercher(),
								choixAnnuaire.getCheminDuFichierASauvegarder());
						message.setText("Vous avez selectionnez l'annuaire : "
								+ choixAnnuaire.getNomDuFichierSelectionne()
								+ "\nVous visionnez : "
								+ choixAnnuaire.getNomDuFichierSauv());
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal.setCenter(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnAffiche.setPrefSize(400, 45);
		btnAffiche.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					if (arbreBin.getSauvegarde() == null) {
						JOptionPane.showMessageDialog(null,
								"Veuillez choisir un annuaire pour l'afficher");
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal.setCenter(message);
					} else {
						panelAffiche = new PanelRechercheUtil(arbreBin,
								arbreBin.getSauvegarde());
						panelPrincipal.setCenter(panelAffiche);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btnDeconnexion.setPrefSize(400, 45);
		btnDeconnexion.setAlignment(Pos.CENTER);
		btnDeconnexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					new Connexion(stage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnGererAnnuaire.setFont(Font.font(null, 15));
		btnAffiche.setFont(Font.font(null, 15));
		btnDeconnexion.setFont(Font.font(null, 15));
		menus.getChildren()
				.addAll(btnGererAnnuaire, btnAffiche, btnDeconnexion);

		panelTop.setCenter(menus);
		menus.setAlignment(Pos.CENTER);
		panelPrincipal.setTop(panelTop);
		panelPrincipal.setStyle("-fx-background-color: #DCDCDC");
		stage.setTitle("Annuaire des Stagiaire");
		stage.setScene(scene);
		stage.show();
	}

}
