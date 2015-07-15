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

/**
 * 
 * @web http://zoranpavlovic.blogspot.com/
 */
public class Connexion extends BorderPane {
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

	private HBox panelTitre = new HBox();
	private BorderPane borderCenter = new BorderPane();
	private GridPane gridPane = new GridPane();
	private Text titre = new Text("IRE Gestion Annuaire");
	private DropShadow dropShadow = new DropShadow();
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
		
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);

		titre.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		titre.setEffect(dropShadow);
		
		String absolutePathFichierLogin = panelPrincipal.getClass()
				.getResource(fichierLogin).getPath();

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

		panelPrincipal.setPadding(new Insets(10, 50, 50, 50));

		gridPane.setPadding(new Insets(5));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		gridPane.add(lblUserName, 0, 0);
		gridPane.add(txtUserName, 1, 0);
		gridPane.add(lblPassword, 0, 1);
		gridPane.add(pf, 1, 1);
		gridPane.add(btnLogin, 1, 2);
		gridPane.add(lblMessage, 1, 3);

		

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
					lblMessage.setText("Bienvenue " + util.getIdentifiant());
					lblMessage.setTextFill(Color.GREEN);
				} else {
					lblMessage.setText("Identifiant ou mot de passe erroné");
					lblMessage.setTextFill(Color.RED);
				}
				txtUserName.setText("");
				pf.setText("");
			}
		});
		
		Scene scene = new Scene(panelPrincipal, 1200, 650);
		stage.setTitle("Annuaire des stagiaires");
		stage.setScene(scene);
		stage.show();

	}

}