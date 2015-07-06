package fr.afcepf.ai.ire.annuaire.vue;

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

public class EcranGestionStagiaire extends Application{
	
	private BorderPane panelPrincipal = new BorderPane();
	private HBox panelTitre = new HBox();
	private Label titre = new Label("Stagiaire");
	
	private VBox menus = new VBox();
	private Button btnAfficheAjout = new Button("Ajouter");
	private Button btnAfficheSuppr = new Button("Supprimer/Mettre à jour");
	
	private GridPane panelAjout = new PanelAjout();
	private BorderPane panelSuppr = new PanelRechercheAdmin();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		btnAfficheAjout.setPrefSize(150, 200);
		btnAfficheAjout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				panelPrincipal.setCenter(panelAjout);
				panelAjout.setAlignment(Pos.CENTER);
			}
		});
		btnAfficheSuppr.setPrefSize(150, 200);
		btnAfficheSuppr.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				panelPrincipal.setCenter(panelSuppr);
			}
		});
		menus.getChildren().addAll(btnAfficheAjout, btnAfficheSuppr);
		
		titre.setPrefHeight(60);
		titre.setFont(Font.font("Verdana", 50));
		panelTitre.getChildren().add(titre);
		panelTitre.setAlignment(Pos.CENTER);
		
		menus.setAlignment(Pos.CENTER);
		panelPrincipal.setTop(panelTitre);
		panelPrincipal.setLeft(menus);
		
		Scene scene = new Scene(panelPrincipal, 1000, 600);
		stage.setTitle("Gestion des Stagiaire");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
