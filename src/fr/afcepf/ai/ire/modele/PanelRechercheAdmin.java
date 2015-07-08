package fr.afcepf.ai.ire.modele;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PanelRechercheAdmin extends BorderPane {

	private Label labelNomStagiaire = new Label("Nom : ");
	private Label labelDepartementStagiaire = new Label("Departement : ");
	private Label labelPromoStagiaire = new Label("Promo : ");

	private TextField textNomStagiaire = new TextField();
	private TextField textDepartementStagiaire = new TextField();
	private TextField textPromoStagiaire = new TextField();

	private Button btnRechercher = new Button("Rechercher");

	private HBox panelTop = new HBox();
	private HBox panelBottom = new HBox();

	private Button btnSupprimer = new Button("Supprimer");
	private Button btnMettreAJour = new Button("Mettre à jour");

	private TableView<Stagiaire> tableVue = new TableView<>();

	private IGestionStagiaire gestionStagiaire = new GestionStagiaire();
	private ObservableList<Stagiaire> listPourTableau;

	@SuppressWarnings("unchecked")
	public PanelRechercheAdmin(final CreationAjoutArbreBinaire arbreBin,
			String cheminAnnuaireALire) {
		this.setTop(panelTop);
		this.setCenter(tableVue);
		this.setBottom(panelBottom);

		panelTop.getChildren().addAll(labelNomStagiaire, textNomStagiaire,
				labelDepartementStagiaire, textDepartementStagiaire,
				labelPromoStagiaire, textPromoStagiaire, btnRechercher);
		panelBottom.getChildren().addAll(btnSupprimer, btnMettreAJour);
		panelBottom.setAlignment(Pos.CENTER);

		TableColumn<Stagiaire, String> colNom = new TableColumn<>();
		colNom.setText("Nom");
		colNom.setMinWidth(200);
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"nom"));

		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>();
		colPrenom.setText("Prenom");
		colPrenom.setMinWidth(200);
		colPrenom
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"prenom"));

		TableColumn<Stagiaire, String> colDepartement = new TableColumn<>();
		colDepartement.setText("Departement");
		colDepartement.setMinWidth(100);
		colDepartement
				.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
						"departement"));

		TableColumn<Stagiaire, String> colPromo = new TableColumn<>();
		colPromo.setText("Promo");
		colPromo.setMinWidth(75);
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"promo"));

		TableColumn<Stagiaire, String> colAnnee = new TableColumn<>();
		colAnnee.setText("Annee");
		colAnnee.setMinWidth(75);
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>(
				"annee"));
		
		tableVue.getColumns().addAll(colNom, colPrenom, colDepartement,
				colPromo, colAnnee);
		
		try {
			listPourTableau = FXCollections.observableArrayList(arbreBin
					.lireAnnuaire(0, cheminAnnuaireALire));
		} catch (Exception e) {
			System.out.println("erreur de Lecture de la liste");
			e.getMessage();
		}
		tableVue.getItems().clear();
		tableVue.setItems(listPourTableau);
		btnRechercher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				listPourTableau.clear();
				List<Stagiaire> listeStagiaire = new ArrayList<>();
				String champNom = textNomStagiaire.getText();
				String champDep = textDepartementStagiaire.getText();
				String champPromo = textPromoStagiaire.getText();
				try {
					if (!champNom.equals("") && champDep.equals("")
							&& champPromo.equals("")) {
						listeStagiaire = gestionStagiaire
								.rechercherParNom(champNom,
										arbreBin.getSauvegarde(), 0);
					} else {
						if (champNom.equals("") && !champDep.equals("")
								&& champPromo.equals("")) {
							listeStagiaire = gestionStagiaire
									.rechercherParDepartement(champDep,
											listPourTableau);
						} else {
							if (champNom.equals("") && champDep.equals("")
									&& !champPromo.equals("")) {
								listeStagiaire = gestionStagiaire
										.rechercherParPromo(champPromo,
												listPourTableau);
							} else {
								if (!champNom.equals("")
										&& !champDep.equals("")
										&& champPromo.equals("")) {
									List<Stagiaire> listeN = gestionStagiaire
											.rechercherParNom(champNom,
													arbreBin.getSauvegarde(), 0);
									for (Stagiaire stagiaire : listeN) {
										if (stagiaire.getDepartement() == champDep) {
											listeStagiaire.add(stagiaire);
										}
									}
								} else {
									if (champNom.equals("")
											&& !champDep.equals("")
											&& !champPromo.equals("")) {
										List<Stagiaire> listeN = gestionStagiaire
												.rechercherParPromo(champPromo,
														listPourTableau);
										for (Stagiaire stagiaire : listeN) {
											if (stagiaire.getDepartement() == champDep) {
												listeStagiaire.add(stagiaire);
											}
										}
									} else {
										if (!champNom.equals("")
												&& champDep.equals("")
												&& !champPromo.equals("")) {
											List<Stagiaire> listeN = gestionStagiaire
													.rechercherParNom(
															champNom,
															arbreBin.getSauvegarde(),
															0);
											for (Stagiaire stagiaire : listeN) {
												if (stagiaire.getPromo() == champPromo) {
													listeStagiaire
															.add(stagiaire);
												}
											}
										} else {
											if (!champNom.equals("")
													&& !champDep.equals("")
													&& !champPromo.equals("")) {
												List<Stagiaire> listeN = gestionStagiaire
														.rechercherParNom(
																champNom,
																arbreBin.getSauvegarde(),
																0);
												List<Stagiaire> listeM = new ArrayList<>();
												for (Stagiaire stagiaire : listeN) {
													if (stagiaire.getPromo() == champPromo) {
														listeM.add(stagiaire);
													}
													for (Stagiaire leStagiaire : listeM) {
														if (stagiaire
																.getDepartement() == champDep) {
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
					listPourTableau.addAll(listeStagiaire);
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
				gestionStagiaire.supprimer(stagiaire.getNom(), tableVue
						.getSelectionModel().getSelectedIndex());
				listPourTableau.remove(stagiaire);
			}
		});

		btnMettreAJour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Stagiaire stagiaire = tableVue.getSelectionModel()
						.getSelectedItem();
				gestionStagiaire.miseAJour(stagiaire);
				listPourTableau.remove(stagiaire);
			}
		});
	}

}
