package fr.afcepf.ai.ire.modele;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.annuaire.controleur.GestionStagiaire;
import fr.afcepf.ai.ire.annuaire.controleur.IGestionStagiaire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCellBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PanelRechercheAdmin extends BorderPane {

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
	private HBox panelBottom = new HBox();

	private Button btnSupprimer = new Button("Supprimer");
	private Button btnMettreAJour = new Button("Mettre à jour");

	private TableView<Stagiaire> tableVue;

	private IGestionStagiaire gestionStagiaire = new GestionStagiaire();
	private ObservableList<Stagiaire> listPourTableau;
	
	private String nouveauNom;
	private String nouveauPrenom;
	private String nouveauDepartement;
	private String nouvellePromo;
	private String nouvelleAnne;
	private Stagiaire leStagiaire = new Stagiaire();

	@SuppressWarnings("unchecked")
	public PanelRechercheAdmin(final CreationAjoutArbreBinaire arbreBin,
			final String cheminAnnuaireALire) throws IOException {

		tableVue = new TableView<>();
		
		//REND LE TABLEAU EDITABLE
		tableVue.setEditable(true);

		this.setTop(panelTop);
		this.setCenter(tableVue);
		this.setBottom(panelBottom);

		final RandomAccessFile raf = new RandomAccessFile(cheminAnnuaireALire,
				"rwd");

		panelTop.getChildren().addAll(labelNomStagiaire, textNomStagiaire,
				labelPrenomStagiaire, textPrenomStagiaire,
				labelDepartementStagiaire, textDepartementStagiaire,
				labelPromoStagiaire, textPromoStagiaire, labelAnneeStagiaire,
				textAnneeStagiaire, btnRechercher);
		panelBottom.getChildren().addAll(btnSupprimer, btnMettreAJour);
		panelBottom.setAlignment(Pos.CENTER);

		TableColumn<Stagiaire, String> colNom = new TableColumn<>();
		colNom.setText("Nom");
		colNom.setMinWidth(200);
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"nom"));
		colNom.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());
		colNom.setOnEditCommit(new EventHandler<CellEditEvent<Stagiaire, String>>() {

			@Override
			public void handle(CellEditEvent cee) {
				nouveauNom = cee.getNewValue().toString();
				((Stagiaire)cee.getTableView().getItems().get(cee.getTablePosition().getRow())).setNom(nouveauNom);
				
			}
		});

		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>();
		colPrenom.setText("Prenom");
		colPrenom.setMinWidth(200);
		colPrenom
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"prenom"));
		colPrenom.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());
		colPrenom.setOnEditCommit(new EventHandler<CellEditEvent<Stagiaire, String>>() {

			@Override
			public void handle(CellEditEvent cee) {
				nouveauPrenom = cee.getNewValue().toString();
				((Stagiaire)cee.getTableView().getItems().get(cee.getTablePosition().getRow())).setPrenom(nouveauPrenom);
				
			}
		});

		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>();
		colDepartement.setText("Departement");
		colDepartement.setMinWidth(100);
		colDepartement
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"departement"));
		colDepartement.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());
		colDepartement.setOnEditCommit(new EventHandler<CellEditEvent<Stagiaire, String>>() {

			@Override
			public void handle(CellEditEvent cee) {
				nouveauDepartement = cee.getNewValue().toString();
				((Stagiaire)cee.getTableView().getItems().get(cee.getTablePosition().getRow())).setDepartement(nouveauDepartement);
				
			}
		});

		TableColumn<Stagiaire, String> colPromo = new TableColumn<>();
		colPromo.setText("Promo");
		colPromo.setMinWidth(75);
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"promo"));
		colPromo.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());
		colPromo.setOnEditCommit(new EventHandler<CellEditEvent<Stagiaire, String>>() {

			@Override
			public void handle(CellEditEvent cee) {
				nouvellePromo = cee.getNewValue().toString();
				((Stagiaire)cee.getTableView().getItems().get(cee.getTablePosition().getRow())).setPromo(nouvellePromo);
				
			}
		});

		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>();
		colAnnee.setText("Annee");
		colAnnee.setMinWidth(75);
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"annee"));
		colAnnee.setCellFactory(TextFieldTableCell.<Stagiaire>forTableColumn());
		colAnnee.setOnEditCommit(new EventHandler<CellEditEvent<Stagiaire, String>>() {

			@Override
			public void handle(CellEditEvent cee) {
				nouvelleAnne = cee.getNewValue().toString();
				((Stagiaire)cee.getTableView().getItems().get(cee.getTablePosition().getRow())).setAnnee(nouvelleAnne);
				
			}
		});

		tableVue.getColumns().addAll(colNom, colPrenom, colDepartement,
				colPromo, colAnnee);

		try {
			listPourTableau = FXCollections.observableArrayList(arbreBin
					.lireAnnuaire(0, cheminAnnuaireALire));
		} catch (Exception e) {
			System.out.println("erreur de Lecture de la liste");
			e.getMessage();
		}

		raf.seek(0);
		final int indexPere = raf.readInt();

		tableVue.setItems(listPourTableau);

		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				List<Stagiaire> listeStagiaire = new ArrayList<>();
				String champNom = textNomStagiaire.getText();
				String champPrenom = textPrenomStagiaire.getText();
				String champDep = textDepartementStagiaire.getText();
				String champPromo = textPromoStagiaire.getText();
				String champAnnee = textAnneeStagiaire.getText();

				listPourTableau.clear();
				try {
					listPourTableau.addAll(gestionStagiaire
							.rechercherEnMulticritere(champNom, champPrenom,
									champDep, champPromo, champAnnee,
									listeStagiaire, cheminAnnuaireALire));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stagiaire stagiaire = tableVue.getSelectionModel()
						.getSelectedItem();
				gestionStagiaire.supprimerDansArbre(stagiaire, indexPere, raf,
						0, 0, 0);
				listPourTableau.remove(stagiaire);
			}
		});
		
		leStagiaire = tableVue.getSelectionModel().getSelectedItem();

		btnMettreAJour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				Stagiaire nouveauStagiaire = new Stagiaire(nouveauNom, nouveauPrenom, nouveauDepartement, nouvellePromo, nouvelleAnne);
				System.out.println(nouveauStagiaire);
				
				if (nouveauNom.equals("")) {
					gestionStagiaire.miseAJour(raf, leStagiaire, nouveauStagiaire, 0,0,0,0);
				}
				else {
					nouveauStagiaire = GestionStagiaire.remplaceChampsStagiaire(leStagiaire, nouveauStagiaire);
					nouveauStagiaire.setChampsPere(-1);
					nouveauStagiaire.setChampsFilsGauche(-1);
					nouveauStagiaire.setChampsFilsDroit(-1);
					nouveauStagiaire.setChampsFilsCache(-1);
					gestionStagiaire.supprimerDansArbre(leStagiaire, 0, raf, 0, 0, 0);
					gestionStagiaire.ajouter(nouveauStagiaire, cheminAnnuaireALire, arbreBin);
				}
				
				
				listPourTableau.remove(leStagiaire);
				listPourTableau.add(nouveauStagiaire);
			}
		});
	}
}
