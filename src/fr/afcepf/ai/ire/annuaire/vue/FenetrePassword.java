package fr.afcepf.ai.ire.annuaire.vue;

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

	final String password = "1234";
	
	
	
	
	public FenetrePassword(Stage owner){
		super();
		initOwner(owner);
		setTitle("Login");
		Group root = new Group();
		Scene scene = new Scene(root, 250, 150, Color.WHITE);
        setScene(scene);
        
		final GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(5));
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		
		
		
		//List<Password> recherchepassExistant(String password);
		
	
		Label nomUtilisateurLbl = new Label("Utilisateur : ");
		gridpane.add(nomUtilisateurLbl, 0, 1);

		Label passwordLbl = new Label("Password : ");
		gridpane.add(passwordLbl, 0, 2);
		final TextField userNameFld = new TextField("Admin");
		gridpane.add(userNameFld, 1, 1);

		final PasswordField passwordFld = new PasswordField();
		passwordFld.setPromptText("Votre mot de passe");
		gridpane.add(passwordFld, 1, 2);

		Button login = new Button("Login");
		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if(passwordFld.equals(password))
					close();
				else{
				}
				
			}
		});
		
		gridpane.add(login, 1, 3);
		GridPane.setHalignment(login,HPos.RIGHT);
		root.getChildren().add(gridpane);
		
	}
}
