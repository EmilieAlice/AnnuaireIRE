package fr.afcepf.ai.ire.modele;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.annuaire.controleur.GestionStagiaire;
import fr.afcepf.ai.ire.annuaire.controleur.IGestionStagiaire;

public class PanelRechercheUtil extends BorderPane {

	private Label labelNomStagiaire = new Label("Nom : ");
	private Label labelDepartementStagiaire = new Label("Departement : ");
	private Label labelPromoStagiaire = new Label("Promo : ");

	private TextField textNomStagiaire = new TextField();
	private TextField textDepartementStagiaire = new TextField();
	private TextField textPromoStagiaire = new TextField();

	private Button btnRechercher = new Button("Rechercher");

	private HBox panelTop = new HBox();

	private TableView<Stagiaire> tableVue = new TableView<>();

	private IGestionStagiaire gestionStagiaire = new GestionStagiaire();
	private ObservableList<Stagiaire> listPourTableau;

	@SuppressWarnings("unchecked")
	public PanelRechercheUtil(CreationAjoutArbreBinaire arbreBin) {
		this.setTop(panelTop);
		this.setCenter(tableVue);

		panelTop.getChildren().addAll(labelNomStagiaire, textNomStagiaire,
				labelDepartementStagiaire, textDepartementStagiaire,
				labelPromoStagiaire, textPromoStagiaire, btnRechercher);

		TableColumn<Stagiaire, Integer> colNom = new TableColumn<>();
		colNom.setText("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, Integer>(
				"nom"));

		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>();
		colPrenom.setText("Prenom");
		colPrenom
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"prenom"));

		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>();
		colDepartement.setText("Departement");
		colDepartement
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"departement"));

		TableColumn<Stagiaire, String> colPromo = new TableColumn<>();
		colPromo.setText("Promo");
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"promo"));

		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>();
		colAnnee.setText("Annee");
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"annee"));

		tableVue.getColumns().addAll(colPrenom, colPrenom, colDepartement,
				colPromo, colAnnee);
		listPourTableau = FXCollections
				.observableArrayList(new ArrayList<Stagiaire>());
		tableVue.setItems(listPourTableau);
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				List<Stagiaire> listeStagiaire = new ArrayList<>();
				if (!textNomStagiaire.getText().equals("")
						&& textDepartementStagiaire.getText().equals("")
						&& textPromoStagiaire.getText().equals("")) {
					listeStagiaire = gestionStagiaire
							.rechercherParNom(textNomStagiaire.getText(), arbreBin);
				} else {
					if (textNomStagiaire.getText().equals("")
							&& !textDepartementStagiaire.getText().equals("")
							&& textPromoStagiaire.getText().equals("")) {
						listeStagiaire = gestionStagiaire
								.rechercherParDepartement(textDepartementStagiaire
										.getText());
					} else {
						if (textNomStagiaire.getText().equals("")
								&& textDepartementStagiaire.getText()
										.equals("")
								&& !textPromoStagiaire.getText().equals("")) {
							listeStagiaire = gestionStagiaire
									.rechercherParPromo(textPromoStagiaire
											.getText());
						} else {
							if (!textNomStagiaire.getText().equals("")
									&& !textDepartementStagiaire.getText()
											.equals("")
									&& textPromoStagiaire.getText().equals("")) {
								List<Stagiaire> listeN = gestionStagiaire
										.rechercherParNom(textNomStagiaire
												.getText(), arbreBin);
								for (Stagiaire stagiaire : listeN) {
									if (stagiaire.getDepartement() == textDepartementStagiaire
											.getText()) {
										listeStagiaire.add(stagiaire);
									}
								}
							} else {
								if (textNomStagiaire.getText().equals("")
										&& !textDepartementStagiaire.getText()
												.equals("")
										&& !textPromoStagiaire.getText()
												.equals("")) {
									List<Stagiaire> listeN = gestionStagiaire
											.rechercherParPromo(textPromoStagiaire
													.getText());
									for (Stagiaire stagiaire : listeN) {
										if (stagiaire.getDepartement() == textDepartementStagiaire
												.getText()) {
											listeStagiaire.add(stagiaire);
										}
									}
								} else {
									if (!textNomStagiaire.getText().equals("")
											&& textDepartementStagiaire
													.getText().equals("")
											&& !textPromoStagiaire.getText()
													.equals("")) {
										List<Stagiaire> listeN = gestionStagiaire
												.rechercherParNom(textNomStagiaire
														.getText(), arbreBin);
										for (Stagiaire stagiaire : listeN) {
											if (stagiaire.getPromo() == textPromoStagiaire
													.getText()) {
												listeStagiaire.add(stagiaire);
											}
										}
									} else {
										if (!textNomStagiaire.getText().equals(
												"")
												&& !textDepartementStagiaire
														.getText().equals("")
												&& !textPromoStagiaire
														.getText().equals("")) {
											List<Stagiaire> listeN = gestionStagiaire
													.rechercherParNom(textNomStagiaire
															.getText(), arbreBin);
											List<Stagiaire> listeM = new ArrayList<>();
											for (Stagiaire stagiaire : listeN) {
												if (stagiaire.getPromo() == textPromoStagiaire
														.getText()) {
													listeM.add(stagiaire);
												}
												for (Stagiaire leStagiaire : listeM) {
													if (stagiaire
															.getDepartement() == textDepartementStagiaire
															.getText()) {
														listeStagiaire
																.add(leStagiaire);
													}

												}
											}
										}
									}
								}
							}
						}

					}
				}
				listPourTableau.clear();
				listPourTableau.addAll(listeStagiaire);
			}
		});

	}
}
