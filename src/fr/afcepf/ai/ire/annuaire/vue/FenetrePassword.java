package fr.afcepf.ai.ire.annuaire.vue;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

import fr.afcepf.ai.ire.modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FenetrePassword extends Stage{

	private final String fichierLogin = "../ressource/log.bin";
	private Utilisateur admin = new Utilisateur();
	private Utilisateur util = new Utilisateur();
	private Label labelId = new Label("Identifiant : ");
	private Label labelPass = new Label("Password : ");
	private TextField textId = new TextField();
	private PasswordField password = new PasswordField();
	private GridPane gridpane = new GridPane();
	private Button btnValiderConnexion = new Button("Entrer");
	
	public FenetrePassword(final Stage stage){
		super();
		String absolutePathFichierLogin = this.getClass().getResource(fichierLogin).getPath();
		
		try {
			FileReader fr = new FileReader(absolutePathFichierLogin);
			BufferedReader br = new BufferedReader(fr);
			
			String ligneAdmin = br.readLine();
			String ligneUtil = br.readLine();
			
			String[] tabAdmin = ligneAdmin.split(";");
			admin.setIdentifiant(tabAdmin[0]);
			admin.setMotDePasse(tabAdmin[1]);
			
			String[] tabUtil = ligneUtil.split(";");
			util.setIdentifiant(tabUtil[0]);
			util.setMotDePasse(tabUtil[1]);
			
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(admin);
		System.out.println(util);
		
		initOwner(stage);
		setTitle("Connexion");
		Group root = new Group();
		Scene scene = new Scene(root, 250, 150, Color.WHITE);
        setScene(scene);
        
		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		
		gridpane.add(labelId, 0, 1);
		gridpane.add(labelPass, 0, 2);
		gridpane.add(textId, 1, 1);
		gridpane.add(password, 1, 2);
		
		btnValiderConnexion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (textId.equals(admin.getIdentifiant()) || textId.equals(util.getIdentifiant())) {
					if(textId.equals(admin.getIdentifiant())){
						if (password.equals(admin.getMotDePasse())) {
							EcranGestionStagiaire ecranGestion = new EcranGestionStagiaire(stage);
						}
					}
				}				
			}
		});
		
		gridpane.add(btnValiderConnexion, 1, 3);
		GridPane.setHalignment(btnValiderConnexion,HPos.RIGHT);
		root.getChildren().add(gridpane);
		
	}
}
