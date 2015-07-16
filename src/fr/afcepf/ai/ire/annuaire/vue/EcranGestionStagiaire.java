package fr.afcepf.ai.ire.annuaire.vue;

import java.io.IOException;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.modele.CreationAjoutArbreBinaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class EcranGestionStagiaire extends BorderPane {

	private BorderPane panelPrincipal = this;
	private Scene scene = new Scene(panelPrincipal, 1200, 650);
	
	private BorderPane panelTop = new BorderPane();
	private GridPane panelAjout;
	private BorderPane panelSuppr;
	
	private HBox menus = new HBox();
	
	private Button btnGererAnnuaire = new Button("Choisir annuaire");
	private Button btnAjout = new Button("Ajouter un stagiaire");
	private Button btnAfficheSuppr = new Button("Annuaire");
	private Button btnDeconnexion = new Button("Deconnexion");
	
	private CreationAjoutArbreBinaire arbreBin = new CreationAjoutArbreBinaire();
	private GestionAnnuaire choixAnnuaire;
	
	private Label message = new Label("Veuillez selectionner un annuaire à afficher");
	
	public EcranGestionStagiaire(final Stage stage) {
		menus.setPadding(new Insets(5));
		message.setFont(Font.font("Verdana", 20));
		panelPrincipal.setCenter(message);
		
		btnGererAnnuaire.setPrefSize(300, 40);
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

		btnAjout.setPrefSize(300, 40);
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
					panelAjout.setAlignment(Pos.CENTER);
					panelPrincipal.setCenter(panelAjout);
				}
			}
		});

		btnAfficheSuppr.setPrefSize(300, 40);
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

		btnDeconnexion.setPrefSize(300, 40);
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
		btnAjout.setFont(Font.font(null, 15));
		btnAfficheSuppr.setFont(Font.font(null, 15));
		btnDeconnexion.setFont(Font.font(null, 15));
		menus.getChildren().addAll(btnGererAnnuaire, btnAjout, btnAfficheSuppr, btnDeconnexion);
		

		menus.setAlignment(Pos.CENTER);
		panelPrincipal.setTop(menus);
		panelPrincipal.setStyle("-fx-background-color: #DCDCDC");
		stage.setTitle("Annuaire des stagiaires");
		stage.setScene(scene);
		stage.show();
	}
}
