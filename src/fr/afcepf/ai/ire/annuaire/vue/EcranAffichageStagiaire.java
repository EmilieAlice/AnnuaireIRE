package fr.afcepf.ai.ire.annuaire.vue;

import java.io.IOException;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.PanelRechercheAdmin;
import fr.afcepf.ai.ire.modele.PanelRechercheUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EcranAffichageStagiaire extends BorderPane {

	private BorderPane panelPrincipal = this;
	private BorderPane panelTop = new BorderPane();
	private Label message = new Label(
			"Veuillez selectionner un annuaire à afficher");

	private HBox menus = new HBox();
	private Button btnGererAnnuaire = new Button("Choisir annuaire");
	private Button btnAfficheSuppr = new Button("Annuaire");
	private Button btnDeconnexion = new Button("Deconnexion");

	private CreationAjoutArbreBinaire arbreBin = new CreationAjoutArbreBinaire();
	private GestionAnnuaire choixAnnuaire;
	private BorderPane panelSuppr;
	private DropShadow dropShadow = new DropShadow();

	
	private Scene scene = new Scene(panelPrincipal, 1200, 650);

	public EcranAffichageStagiaire(final Stage stage) {
		// Permet d'ecrire un message dans le panels l'ouverture de l'appli
		message.setFont(Font.font("Verdana", 20));
		panelPrincipal.setCenter(message);
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);
		// BOUTON DU CHOIX ANNUAIRE
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

		// BOUTON DE SUPPRESSION ET DE MISE A JOUR DES STAGIAIRES
		btnAfficheSuppr.setPrefSize(400, 45);
		btnAfficheSuppr.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				try {
					if (arbreBin.getSauvegarde() == null) {
						JOptionPane.showMessageDialog(null,
								"Veuillez choisir un annuaire pour l'afficher");
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal.setCenter(message);
					} else {
						panelSuppr = new PanelRechercheAdmin(arbreBin, arbreBin
								.getSauvegarde());
						panelPrincipal.setCenter(panelSuppr);
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
		btnAfficheSuppr.setFont(Font.font(null, 15));
		btnDeconnexion.setFont(Font.font(null, 15));
		menus.getChildren().addAll(btnGererAnnuaire, btnAfficheSuppr, btnDeconnexion);


		panelTop.setCenter(menus);
		menus.setAlignment(Pos.CENTER);
		panelPrincipal.setTop(panelTop);
		panelPrincipal.setStyle("-fx-background-color: #DCDCDC");
		stage.setTitle("Annuaire des Stagiaire");
		stage.setScene(scene);
		stage.show();
	}

}
