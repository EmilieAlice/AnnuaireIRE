package fr.afcepf.ai.ire.annuaire.vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.afcepf.ai.ire.modele.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.GestionStagiaire;
import fr.afcepf.ai.ire.modele.IGestionStagiaire;
import fr.afcepf.ai.ire.modele.Stagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PanelRechercheUtil extends BorderPane {

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

	private Button btnRechercher = new Button("Rechercher");

	private HBox panelTop = new HBox();

	private TableView<Stagiaire> tableVue;

	private IGestionStagiaire gestionStagiaire = new GestionStagiaire();
	private ObservableList<Stagiaire> listPourTableau;
	

	private List<Stagiaire> listeStagiaire = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public PanelRechercheUtil(final CreationAjoutArbreBinaire arbreBin,
			final String cheminAnnuaireALire) throws IOException {

		tableVue = new TableView<>();
		
		//REND LE TABLEAU EDITABLE
		tableVue.setEditable(true);

		this.setTop(panelTop);
		this.setCenter(tableVue);

		panelTop.getChildren().addAll(labelNomStagiaire, textNomStagiaire,
				labelPrenomStagiaire, textPrenomStagiaire,
				labelDepartementStagiaire, textDepartementStagiaire,
				labelPromoStagiaire, textPromoStagiaire, labelAnneeStagiaire,
				textAnneeStagiaire, btnRechercher);

		TableColumn<Stagiaire, String> colNom = new TableColumn<>();
		colNom.setText("Nom");
		colNom.setMinWidth(200);
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"nom"));
		colNom.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());

		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>();
		colPrenom.setText("Prenom");
		colPrenom.setMinWidth(200);
		colPrenom
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"prenom"));
		colPrenom.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());

		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>();
		colDepartement.setText("Departement");
		colDepartement.setMinWidth(100);
		colDepartement
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"departement"));
		colDepartement.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());

		TableColumn<Stagiaire, String> colPromo = new TableColumn<>();
		colPromo.setText("Promo");
		colPromo.setMinWidth(75);
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"promo"));
		colPromo.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());

		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>();
		colAnnee.setText("Annee");
		colAnnee.setMinWidth(75);
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"annee"));
		colAnnee.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());

		tableVue.getColumns().addAll(colNom, colPrenom, colDepartement,
				colPromo, colAnnee);

		try {
			listPourTableau = FXCollections.observableArrayList(arbreBin
					.lireAnnuaire(0, cheminAnnuaireALire));
		} catch (Exception e) {
			System.out.println("erreur de Lecture de la liste");
			e.getMessage();
		}

		tableVue.setItems(listPourTableau);
		
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				
				String champNom = textNomStagiaire.getText();
				String champPrenom = textPrenomStagiaire.getText();
				String champDep = textDepartementStagiaire.getText();
				String champPromo = textPromoStagiaire.getText();
				String champAnnee = textAnneeStagiaire.getText();
				
				List<Stagiaire> listeStagiaireParNom = new ArrayList<Stagiaire>();
				
				try {
					listeStagiaire = gestionStagiaire
							.rechercherEnMulticritere(champNom, champPrenom,
									champDep, champPromo, champAnnee,
									arbreBin
									.lireAnnuaire(0, cheminAnnuaireALire), cheminAnnuaireALire, listeStagiaireParNom);
					listPourTableau.clear();
					listPourTableau.addAll(listeStagiaire);
					for(Stagiaire stagiaire : listeStagiaire){
						System.out.println(stagiaire);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
