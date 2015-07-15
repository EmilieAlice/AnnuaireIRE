package fr.afcepf.ai.ire.annuaire.vue;

import java.io.IOException;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.PanelAjout;
import fr.afcepf.ai.ire.modele.PanelRechercheAdmin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EcranGestionStagiaire extends BorderPane {

	private BorderPane panelPrincipal = this;
	private BorderPane panelTop = new BorderPane();
	private HBox panelTitre = new HBox();
	private Label titre = new Label("Annuaire");
	private Label message = new Label("Veuillez selectionner un annuaire à afficher");

	private VBox menus = new VBox();
	private Button btnGererAnnuaire = new Button("Choisir annuaire");
	private Button btnAjout = new Button("Ajouter un stagiaire");
	private Button btnAfficheSuppr = new Button("Annuaire");

	private CreationAjoutArbreBinaire arbreBin = new CreationAjoutArbreBinaire();
	private GestionAnnuaire choixAnnuaire;
	private GridPane panelAjout;
	private BorderPane panelSuppr;

	private Button btnDeconnexion = new Button("Deconnexion");

	public EcranGestionStagiaire(final Stage stage) {
		// Permet d'ecrire un message dans le panels l'ouverture de l'appli
		message.setFont(Font.font("Verdana", 20));
		panelPrincipal.setCenter(message);

		// BOUTON DU CHOIX ANNUAIRE
		btnGererAnnuaire.setPrefSize(150, 200);
		btnGererAnnuaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					choixAnnuaire = new GestionAnnuaire(stage);
					// ON INITIALISE L'ANNUAIRE GRACE A LA CLASSE
					// GestionAnnuaire
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
								+ "\nVous travaillez sur : "
								+ choixAnnuaire.getNomDuFichierSauv());
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal.setCenter(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// BOUTON D'AJOUT D'UN STAGIAIRE
		btnAjout.setPrefSize(150, 200);
		btnAjout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (arbreBin.getSauvegarde() == null) {
					JOptionPane
							.showMessageDialog(null,
									"Veuillez choisir un annuaire pour ajouter un stagiaire");
					message.setFont(Font.font("Verdana", 20));
					panelPrincipal.setCenter(message);
				} else {
					panelAjout = new PanelAjout(arbreBin, arbreBin
							.getSauvegarde());
					panelPrincipal.setCenter(panelAjout);
					panelAjout.setAlignment(Pos.CENTER);
				}
			}
		});

		// BOUTON DE SUPPRESSION ET DE MISE A JOUR DES STAGIAIRES
		btnAfficheSuppr.setPrefSize(150, 200);
		btnAfficheSuppr.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				try {
					if (arbreBin.getSauvegarde() == null) {
						JOptionPane
								.showMessageDialog(null,
										"Veuillez choisir un annuaire pour l'afficher/le modifier");
						message.setFont(Font.font("Verdana", 20));
						panelPrincipal
								.setCenter(message);
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

		btnDeconnexion.setPrefSize(100, 35);
		btnDeconnexion.setAlignment(Pos.CENTER);
		btnDeconnexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					panelPrincipal.setCenter(new Connexion(stage));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		menus.getChildren().addAll(btnGererAnnuaire, btnAjout, btnAfficheSuppr);

		titre.setPrefHeight(80);
		titre.setFont(Font.font("Verdana", 60));
		panelTitre.getChildren().add(titre);
		panelTitre.setAlignment(Pos.CENTER);

		panelTop.setCenter(panelTitre);
		panelTop.setRight(btnDeconnexion);

		menus.setAlignment(Pos.CENTER);
		panelPrincipal.setTop(panelTop);
		panelPrincipal.setLeft(menus);

		Scene scene = new Scene(panelPrincipal, 1200, 650);
		stage.setTitle("Annuaire des stagiaires");
		stage.setScene(scene);
		stage.show();
	}
}
