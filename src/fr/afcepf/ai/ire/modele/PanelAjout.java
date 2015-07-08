package fr.afcepf.ai.ire.modele;

import java.io.RandomAccessFile;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.annuaire.controleur.GestionStagiaire;
import fr.afcepf.ai.ire.annuaire.controleur.IGestionStagiaire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PanelAjout extends GridPane {

	private Label labelNomStagiaire = new Label("Nom : ");
	private Label labelPrenomStagiaire = new Label("Prenom : ");
	private Label labelDepartementStagiaire = new Label("Departement : ");
	private Label labelPromoStagiaire = new Label("Promo : ");
	private Label labelAnneeStagiaire = new Label("Annee : ");
	

	private TextField textNomStagiaire = new TextField();
	private TextField textPrenomStagiaire = new TextField();
	private TextField textDepartementStagiaire = new TextField();
	private TextField textPromoStagiaire = new TextField();
	private TextField textAnneeStagiaire = new TextField();

	private IGestionStagiaire gestionStagiaire = new GestionStagiaire();

	private Button btnAjouter = new Button("Ajouter");

	public PanelAjout(final CreationAjoutArbreBinaire arbreBin, final String chemainDuRaf) {
		
		this.add(labelNomStagiaire, 0, 0);
		this.add(labelPrenomStagiaire, 0, 1);
		this.add(labelDepartementStagiaire, 0, 2);
		this.add(labelPromoStagiaire, 0, 3);
		this.add(labelAnneeStagiaire, 0, 4);
		this.add(textNomStagiaire, 1, 0);
		this.add(textPrenomStagiaire, 1, 1);
		this.add(textDepartementStagiaire, 1, 2);
		this.add(textPromoStagiaire, 1, 3);
		this.add(textAnneeStagiaire, 1, 4);
		this.add(btnAjouter, 1, 5);
		
		this.setHgap(50);
		this.setVgap(50);
		
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				Stagiaire sta = new Stagiaire(textNomStagiaire.getText().toUpperCase(), 
						textPrenomStagiaire.getText(), 
						textDepartementStagiaire.getText(), textPromoStagiaire.getText(), textAnneeStagiaire.getText());
				sta.setChampsPere(-1);
				sta.setChampsFilsGauche(-1);
				sta.setChampsFilsDroit(-1);
				sta.setChampsFilsCache(-1);
				System.out.println(arbreBin.getNbLigne());
				gestionStagiaire.ajouter(sta, chemainDuRaf, arbreBin, arbreBin.getNbLigne());
			}
		});
	}
}
