package fr.afcepf.ai.ire.annuaire.vue;

import java.io.BufferedReader;
import java.io.FileReader;

import fr.afcepf.ai.ire.modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * @web http://zoranpavlovic.blogspot.com/
 */
public class TestConnexion extends BorderPane {
	private final String fichierLogin = "../ressource/log.bin";
	private Utilisateur admin = new Utilisateur();
	private Utilisateur util = new Utilisateur();
	private String checkUser, checkPw;
	
	private Label lblUserName = new Label("Identifiant");
	private Label lblPassword = new Label("Mot de passe");
	private Button btnLogin = new Button("Entrer");
	
	private final TextField txtUserName = new TextField();
	private final PasswordField pf = new PasswordField();
	private final Label lblMessage = new Label();

	public TestConnexion(final Stage stage) throws Exception {
		String absolutePathFichierLogin = this.getClass().getResource(fichierLogin).getPath();
		
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

		this.setPadding(new Insets(10, 50, 50, 50));

		// Adding HBox
		HBox hb = new HBox();
		hb.setPadding(new Insets(20, 20, 20, 30));

		// Adding GridPane
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		// Implementing Nodes for GridPane
		
		

		// Adding Nodes to GridPane layout
		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(pf, 1, 1);
		gridPane.add(btnLogin, 2, 1);
		gridPane.add(lblMessage, 1, 2);

		// Reflection for gridPane
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		gridPane.setEffect(r);

		// DropShadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);

		// Adding text and DropShadow effect to it
		Text text = new Text("IRE Gestion Annuaire");
		text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		text.setEffect(dropShadow);

		// Adding text to HBox
		hb.getChildren().add(text);

		// Add ID's to Nodes
		this.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		text.setId("text");

		// Action for btnLogin
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				checkUser = txtUserName.getText().toString();
				checkPw = pf.getText().toString();
				if (checkUser.equals(admin.getIdentifiant()) && checkPw.equals(admin.getMotDePasse())) {
					EcranGestionStagiaire ecranGestionStagiaire = new EcranGestionStagiaire(stage);
//					lblMessage.setText("Bienvenue " + admin.getIdentifiant());
//					lblMessage.setTextFill(Color.GREEN);
					
				} else if (checkUser.equals(util.getIdentifiant()) && checkPw.equals(util.getMotDePasse())) {
					EcranAffichageStagiaire ecranAffichageStagiaire = new EcranAffichageStagiaire(stage);
					lblMessage.setText("Bienvenue " + util.getIdentifiant());
					lblMessage.setTextFill(Color.GREEN);
				} 
				else {
					lblMessage.setText("Identifiant ou mot de passe erroné");
					lblMessage.setTextFill(Color.RED);
				}		
				txtUserName.setText("");
				pf.setText("");
			}
		});

		// Add HBox and GridPane layout to BorderPane Layout
		this.setTop(hb);
		this.setCenter(gridPane);

	}

}