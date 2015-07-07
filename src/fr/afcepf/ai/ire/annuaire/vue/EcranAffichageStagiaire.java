package fr.afcepf.ai.ire.annuaire.vue;

import fr.afcepf.ai.ire.modele.PanelRechercheUtil;
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

public class EcranAffichageStagiaire extends Application{
	
	private BorderPane panelPrincipal = new BorderPane();
	private HBox panelTitre = new HBox();
	private Label titre = new Label("Stagiaire");
	
	private BorderPane panelUtil = new PanelRechercheUtil();
	
	@Override
	public void start(Stage stage) throws Exception {
		
		titre.setPrefHeight(60);
		titre.setFont(Font.font("Verdana", 50));
		panelTitre.getChildren().add(titre);
		panelTitre.setAlignment(Pos.CENTER);
		
		panelPrincipal.setTop(panelTitre);
		panelPrincipal.setCenter(panelUtil);
		
		Scene scene = new Scene(panelPrincipal, 1000, 600);
		stage.setTitle("Gestion des Stagiaire");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
