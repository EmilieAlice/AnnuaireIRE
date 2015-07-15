package fr.afcepf.ai.ire.annuaire.vue;

import java.io.BufferedReader;
import java.io.FileReader;

import fr.afcepf.ai.ire.modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
public class Connexion extends BorderPane {
	private final String fichierLogin = "C:/Users/Stagiaire/Desktop/Projet1/IRE/log.bin";
	private Utilisateur admin = new Utilisateur();
	private Utilisateur util = new Utilisateur();
	private String checkUser, checkPw;

	private Label lblUserName = new Label("Identifiant : ");
	private Label lblPassword = new Label("Mot de passe : ");
	private Button btnLogin = new Button("Connexion");

	private final TextField txtUserName = new TextField();
	private final PasswordField pf = new PasswordField();
	private final Label lblMessage = new Label();

	private HBox panelTitre = new HBox();
	private BorderPane borderCenter = new BorderPane();
	private GridPane gridPane = new GridPane();
	private Text titre = new Text("IRE Gestion Annuaire");
	private BorderPane panelPrincipal = this;
		        

	public Connexion(final Stage stage) throws Exception {
        
		panelTitre.getChildren().add(titre);
		panelTitre.setAlignment(Pos.CENTER);
		panelTitre.setMinHeight(50);
		
		gridPane.setAlignment(Pos.CENTER);
		borderCenter.setCenter(gridPane);
		borderCenter.setMinHeight(100);

		panelPrincipal.setTop(panelTitre);
		panelPrincipal.setCenter(borderCenter);
		

		titre.setFont(Font.font("Verdana", 40));
		
		FileReader fr = new FileReader(fichierLogin);
		BufferedReader br = new BufferedReader(fr);

		String ligneAdmin = br.readLine();
		String ligneUtil = br.readLine();

		String[] tabAdmin = ligneAdmin.split(";");
		admin.setIdentifiant(tabAdmin[0]);
		admin.setMotDePasse(tabAdmin[1]);

		String[] tabUtil = ligneUtil.split(";");
		util.setIdentifiant(tabUtil[0]);
		util.setMotDePasse(tabUtil[1]);

		panelPrincipal.setPadding(new Insets(10, 50, 50, 50));

		gridPane.setPadding(new Insets(7));
		gridPane.setHgap(7);
		gridPane.setVgap(7);

		lblUserName.setFont(Font.font("Arial", 15));
		lblPassword.setFont(Font.font("Arial", 15));
		
		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(pf, 1, 1);
		gridPane.add(btnLogin, 1, 3);
		gridPane.add(lblMessage, 1, 5);
		btnLogin.setFont(Font.font(null, 15));
		gridPane.setMinWidth(100);

		
btnLogin.setPrefSize(100, 15);
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				checkUser = txtUserName.getText().toString();
				checkPw = pf.getText().toString();
				if (checkUser.equals(admin.getIdentifiant())
						&& checkPw.equals(admin.getMotDePasse())) {
					new EcranGestionStagiaire(
							stage);
				} else if (checkUser.equals(util.getIdentifiant())
						&& checkPw.equals(util.getMotDePasse())) {
					new EcranAffichageStagiaire(
							stage);
				} else {
					lblMessage.setText("Identifiant ou mot de passe erroné");
					lblMessage.setFont(Font.font("Arial", 12));
					lblMessage.setTextFill(Color.RED);
				}
				txtUserName.setText("");
				pf.setText("");
			}
		});
		panelPrincipal.setStyle("-fx-background-color: #DCDCDC");
		Scene scene = new Scene(panelPrincipal, 800, 550);
		stage.setTitle("Annuaire des stagiaires");
		stage.setScene(scene);
		stage.show();

		
	}

	public Utilisateur getAdmin() {
		return admin;
	}

	public void setAdmin(Utilisateur admin) {
		this.admin = admin;
	}

	public Utilisateur getUtil() {
		return util;
	}

	public void setUtil(Utilisateur util) {
		this.util = util;
	}

}