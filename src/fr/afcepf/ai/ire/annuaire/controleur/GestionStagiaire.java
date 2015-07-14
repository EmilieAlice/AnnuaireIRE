package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.afcepf.ai.ire.modele.Stagiaire;

public class GestionStagiaire implements IGestionStagiaire {
	private List<Stagiaire> listeAAfficher = new ArrayList<>();

	@Override
	public void ajouter(Stagiaire stagiaire, String chemainDuRaf,
			CreationAjoutArbreBinaire caab) {
		try {
			RandomAccessFile raf = new RandomAccessFile(chemainDuRaf, "rwd");
			int nbLigne = (int) ((raf.length() / CreationAjoutArbreBinaire.LONGUEURLIGNE));
			System.out.println(nbLigne);
			caab.formaterEnRubrique(stagiaire, raf);
			caab.rechercherPereDuStagiaire(0, raf, nbLigne);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void supprimerDansArbre(Stagiaire unStagiaire, int indexPere,
			RandomAccessFile fichierAStructurer, int numeroDeLigneStagiaire,
			int positionChamps, int fils) {

		Stagiaire stagiaireAComparer = new Stagiaire();

		try {

			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(
					fichierAStructurer, indexPere);
			System.out.println(stagiaireAComparer + " fils : " + fils);
			if (unStagiaire.getNom().trim()
					.compareToIgnoreCase(stagiaireAComparer.getNom().trim()) < 0) {
				System.err.println("je suis fils gauche "
						+ stagiaireAComparer.getChampsFilsGauche() + " de "
						+ stagiaireAComparer.getNom());
				fils = stagiaireAComparer.getChampsFilsGauche();
				positionChamps = CreationAjoutArbreBinaire.POSITIONPERE;
				supprimerDansArbre(unStagiaire, fils, fichierAStructurer,
						numeroDeLigneStagiaire, positionChamps, fils);

			} else if (unStagiaire.getNom().trim()
					.compareToIgnoreCase(stagiaireAComparer.getNom().trim()) > 0) {
				fils = stagiaireAComparer.getChampsFilsDroit();
				positionChamps = CreationAjoutArbreBinaire.POSITIONFILSGAUCHE;
				supprimerDansArbre(unStagiaire, fils, fichierAStructurer,
						numeroDeLigneStagiaire, positionChamps, fils);

				// QUAND TROUVE
			} else {
				System.out.println("j'y suis " + unStagiaire + " ma ligne --> "
						+ fils);

				// SI RACINE A SUPPR
				fichierAStructurer.seek(0);
				int ligneRacine = fichierAStructurer.readInt();

				if (fils == ligneRacine) {
					System.err
							.println("je suis une RACINE les gars ! ma ligne est "
									+ fils);

					stagiaireAComparer = rechercherBonStagiaireCache(
							fichierAStructurer, unStagiaire,
							stagiaireAComparer,
							stagiaireAComparer.getChampsFilCache());

					int numeroLigneStagiaireARemonter = stagiaireAComparer
							.getChampsFilCache();

					// SI RACINE OU RACINECACHE A UN FC
					if (stagiaireAComparer.getChampsFilCache() != -1) {

						System.err
								.println("niveau racine avec presence fils cache ligne "
										+ stagiaireAComparer
												.getChampsFilCache());

						Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
								.lireUnStagiaire(fichierAStructurer,
										numeroLigneStagiaireARemonter);
						System.err.println("a remonter "
								+ numeroLigneStagiaireARemonter);

						numeroDeLigneStagiaire = stagiaireARemonter
								.getChampsPere();

						remonterFilsCacheDansArbre(fichierAStructurer,
								stagiaireARemonter,
								numeroLigneStagiaireARemonter,
								stagiaireAComparer, numeroDeLigneStagiaire,
								positionChamps);
					}
					// SINON
					else {

						System.err
								.println("niveau racine SANS fils cache car ligne FC = "
										+ stagiaireAComparer
												.getChampsFilCache());

						if (stagiaireAComparer.getChampsFilCache() != -1) {

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
									.lireUnStagiaire(fichierAStructurer,
											numeroLigneStagiaireARemonter);
							System.err.println("a remonter "
									+ numeroLigneStagiaireARemonter);

							remonterFilsDansArbre(fichierAStructurer,
									stagiaireARemonter,
									stagiaireAComparer.getChampsFilsGauche(),
									stagiaireAComparer, numeroDeLigneStagiaire,
									positionChamps,
									CreationAjoutArbreBinaire.POSITIONPERE);
							System.out.println(stagiaireARemonter);

						} else {
							System.out
									.println("c'est une feuille qui est effacée");
							CreationAjoutArbreBinaire
									.modifierFilsDuPere(
											fichierAStructurer,
											numeroLigneStagiaireARemonter,
											numeroDeLigneStagiaire,
											CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}

					}

				}
				// PAS RACINE A SUPPR
				else {

					// index pere inchangé mais stagiaire <-- pereDuStagiare
					numeroDeLigneStagiaire = fils;
					System.err.println("pas une racine "
							+ numeroDeLigneStagiaire);

					// SI FILS CACHE
					if (stagiaireAComparer.getChampsFilCache() != -1) {
						System.err.println("presence fils cache : "
								+ stagiaireAComparer.getChampsFilCache());

						stagiaireAComparer = rechercherBonStagiaireCache(
								fichierAStructurer, unStagiaire,
								stagiaireAComparer,
								stagiaireAComparer.getChampsFilCache());

						int numeroLigneStagiaireARemonter = stagiaireAComparer
								.getChampsFilCache();
						System.err.println("pas racine FC à la ligne ligne "
								+ stagiaireAComparer.getChampsFilCache());

						if (numeroLigneStagiaireARemonter != -1) {

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
									.lireUnStagiaire(fichierAStructurer,
											numeroLigneStagiaireARemonter);
							System.err.println("a remonter "
									+ numeroLigneStagiaireARemonter);

							numeroDeLigneStagiaire = stagiaireARemonter
									.getChampsPere();

							remonterFilsCacheDansArbre(fichierAStructurer,
									stagiaireARemonter,
									stagiaireAComparer.getChampsFilCache(),
									stagiaireAComparer, numeroDeLigneStagiaire,
									positionChamps);
						} else {
							System.out
									.println("c'est une feuille qui est remontée");
							CreationAjoutArbreBinaire
									.modifierFilsDuPere(
											fichierAStructurer,
											numeroLigneStagiaireARemonter,
											numeroDeLigneStagiaire,
											CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}

					}

					// SI stagiaire.FD -1 (existe pas) remonter le gauche
					else if (stagiaireAComparer.getChampsFilsDroit() == -1) {
						System.err.println("je teste fils droit" + " "
								+ numeroDeLigneStagiaire
								+ " car fils droit existe pas => "
								+ stagiaireAComparer.getChampsFilsDroit());
						System.err.println("a remonter : "
								+ stagiaireAComparer.getChampsFilsGauche());
						CreationAjoutArbreBinaire.modifierPereDuStagiaire(
								fichierAStructurer,
								stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere());

						System.err.println("position champs a modif dans pere"
								+ positionChamps);
						CreationAjoutArbreBinaire.modifierFilsDuPere(
								fichierAStructurer,
								stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere(),
								positionChamps);
					}

					// SI stagiaire.FG -1 (existe pas) remonter le droit
					else {
						if (stagiaireAComparer.getChampsFilsGauche() == -1) {
							System.err.println("je teste fils gauche" + " "
									+ numeroDeLigneStagiaire
									+ " car fils gauche existe pas => "
									+ stagiaireAComparer.getChampsFilsGauche());
							System.err.println("a remonter : "
									+ stagiaireAComparer.getChampsFilsDroit());
							CreationAjoutArbreBinaire.modifierPereDuStagiaire(
									fichierAStructurer,
									stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere());

							System.err
									.println("position champs a modif dans pere"
											+ positionChamps);

							CreationAjoutArbreBinaire.modifierFilsDuPere(
									fichierAStructurer,
									stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere(),
									positionChamps);

						}
						// si deux fils pour stagiaire a suppr dans SAD
						else {
							System.err
									.println("test pour le cas presence FG et FD");
							int numeroLigneStagiaireARemonter = stagiaireAComparer
									.getChampsFilsGauche();
							System.out.println("a remonter : "
									+ numeroLigneStagiaireARemonter);
							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
									.lireUnStagiaire(fichierAStructurer,
											numeroLigneStagiaireARemonter);
							remonterFilsDansArbre(fichierAStructurer,
									stagiaireARemonter,
									numeroLigneStagiaireARemonter,
									stagiaireAComparer, numeroDeLigneStagiaire,
									positionChamps,
									CreationAjoutArbreBinaire.POSITIONPERE);
						}
					}
				}
				System.out.println("j'ai changé " + unStagiaire);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void remonterFilsDansArbre(
			RandomAccessFile fichierAStructurer, Stagiaire stagiaireARemonter,
			int numeroLigneStagiaireARemonter, Stagiaire stagiaireAComparer,
			int numeroLigneStagiaireAcomparer, int positionChamps,
			int positionChampsStagiaireARemonter) throws Exception {

		if (stagiaireARemonter.getChampsFilsDroit() == -1) {

			// MODIF CH PERE DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer, numeroLigneStagiaireARemonter,
					stagiaireAComparer.getChampsPere());
			if (stagiaireAComparer.getChampsPere() == -1) {
				System.out.println("si StaAComp a pour pere la racine");
				fichierAStructurer.seek(0);
				fichierAStructurer.writeInt(numeroLigneStagiaireARemonter);
			} else {
				System.out
						.println("étape 1 bis changement Fils du pere du staAComp");
				CreationAjoutArbreBinaire.modifierFilsDuPere(
						fichierAStructurer, numeroLigneStagiaireARemonter,
						stagiaireAComparer.getChampsPere(), positionChamps);
			}
			// MODIF PERE DU FG DU STA A COMP
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					numeroLigneStagiaireARemonter,
					stagiaireAComparer.getChampsFilsGauche(),
					CreationAjoutArbreBinaire.POSITIONANNEE);
			// MODIF FILS DROIT DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilsDroit(),
					numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			// MODIF FILS DROIT OU GAUCHE DU PERE DU STAGIAIRE A REMONTER
			if (stagiaireAComparer.getChampsFilCache() == -1) {
				CreationAjoutArbreBinaire.modifierFilsDuPere(
						fichierAStructurer,
						stagiaireARemonter.getChampsFilsGauche(),
						stagiaireAComparer.getChampsFilsGauche(),
						CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			}
			// MODIF PERE FILS GAUCHE DU STA A REMONTER
			if (stagiaireARemonter.getChampsFilsGauche() != -1) {
				CreationAjoutArbreBinaire.modifierPereDuStagiaire(
						fichierAStructurer,
						stagiaireARemonter.getChampsFilsGauche(),
						stagiaireAComparer.getChampsFilsGauche());
			} else {
				System.out.println("pas de fils gauche");
			}
			// MODIF FILS GAUCHE DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilsGauche(),
					numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONPERE);
			// MODIF PERE DU FILS DROIT DU STAGIAIRE A SUPPR
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer,
					stagiaireAComparer.getChampsFilsDroit(),
					numeroLigneStagiaireARemonter);

		} else {
			numeroLigneStagiaireARemonter = stagiaireARemonter
					.getChampsFilsDroit();
			System.out.println("a remonter : " + numeroLigneStagiaireARemonter);
			Stagiaire nouveauStagiaireARemonter = CreationAjoutArbreBinaire
					.lireUnStagiaire(fichierAStructurer,
							numeroLigneStagiaireARemonter);
			remonterFilsDansArbre(fichierAStructurer,
					nouveauStagiaireARemonter, numeroLigneStagiaireARemonter,
					stagiaireAComparer, numeroLigneStagiaireAcomparer,
					positionChamps,
					CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
		}

	}

	public static void remonterFilsCacheDansArbre(
			RandomAccessFile fichierAStructurer, Stagiaire stagiaireARemonter,
			int numeroLigneStagiaireARemonter, Stagiaire stagiaireAComparer,
			int numeroLigneStagiaireAcomparer, int positionChamps)
			throws Exception {

		System.err.println("entre dans procedure remonterFilsCache");

		// ON SE MET SUR LA LIGNE DU FILS CACHE ET ON MODIF SON PERE ET SES FILS
		CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer,
				numeroLigneStagiaireARemonter,
				stagiaireAComparer.getChampsPere());
		CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
				stagiaireAComparer.getChampsFilsGauche(),
				numeroLigneStagiaireARemonter,
				CreationAjoutArbreBinaire.POSITIONPERE);
		CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
				stagiaireAComparer.getChampsFilsDroit(),
				numeroLigneStagiaireARemonter,
				CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
		// ON CHANGE LE PERE DU STA A SUPPR
		// ON VERIF SI LE STA A SUPPR A UN FILS CACHE
		fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE
				* stagiaireAComparer.getChampsPere()
				+ CreationAjoutArbreBinaire.POSITIONFILSDROIT
				+ CreationAjoutArbreBinaire.RACINE);
		int fc = fichierAStructurer.readInt();
		if (fc == numeroLigneStagiaireAcomparer) {
			// SI STA A COMP EST UN FC
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilCache(),
					stagiaireAComparer.getChampsPere(),
					CreationAjoutArbreBinaire.POSITIONFILSDROIT);
		}
		// SI STA A COMP N'EST PAS UN FC
		else {
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilCache(),
					stagiaireAComparer.getChampsPere(), positionChamps);
		}
		// ON MODIFIE LES FILS DU STAGIAIRE A SUPPR POUR Y METTRE EN CHAMPS
		// PERE, LE STA A REMONTER
		// CAS OU QQCH A GAUCHE DE STAGIAIRE A SUPPR
		if (stagiaireAComparer.getChampsFilsGauche() != -1) {
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer,
					stagiaireAComparer.getChampsFilsGauche(),
					stagiaireAComparer.getChampsFilCache());
		}
		// CAS OU QQCH A DROITE DE STAGIAIRE A SUPRR
		if (stagiaireAComparer.getChampsFilsDroit() != -1) {
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer,
					stagiaireAComparer.getChampsFilsDroit(),
					stagiaireAComparer.getChampsFilCache());
		}
	}

	public static Stagiaire rechercherBonStagiaireCache(
			RandomAccessFile fichierAStructurer, Stagiaire unStagiaire,
			Stagiaire stagiaireAComparer, int ligneFilsCache) throws Exception {

		if (!unStagiaire.equals(stagiaireAComparer)) {
			System.err.println("mauvais stagiaire, recherche relancée sur : "
					+ stagiaireAComparer.getChampsFilCache());
			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(
					fichierAStructurer, stagiaireAComparer.getChampsFilCache());
			rechercherBonStagiaireCache(fichierAStructurer, unStagiaire,
					stagiaireAComparer, stagiaireAComparer.getChampsFilCache());
		}
		System.out.println("bon stagiaire trouvé sur fils caché");
		return stagiaireAComparer;
	}

	@Override
	public void miseAJour(Stagiaire stagiaire) {

	}

	@Override
	public List<Stagiaire> rechercherParNom(String nomStagiaireRecherche,
			String chemainRaf, int numLigne) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(chemainRaf, "r");
		Stagiaire stagiairePere = CreationAjoutArbreBinaire.lireUnStagiaire(
				raf, numLigne);
		String nomStagierePere = stagiairePere.getNom().toLowerCase().trim();
		String nomStagiaireARechercher = nomStagiaireRecherche.toLowerCase()
				.trim();
		if (nomStagierePere.startsWith(nomStagiaireARechercher)) {
			listeAAfficher.add(stagiairePere);
			if (stagiairePere.getChampsFilCache() != -1) {
				numLigne = stagiairePere.getChampsFilCache();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
			if (stagiairePere.getChampsFilsGauche() != -1) {
				numLigne = stagiairePere.getChampsFilsGauche();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
			if (stagiairePere.getChampsFilsDroit() != -1) {
				numLigne = stagiairePere.getChampsFilsDroit();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
		} else {
			if (nomStagiaireARechercher.compareToIgnoreCase(nomStagierePere) < 0) {
				if (stagiairePere.getChampsFilsGauche() != -1) {
					numLigne = stagiairePere.getChampsFilsGauche();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							numLigne);
				}
			} else if (nomStagiaireARechercher
					.compareToIgnoreCase(nomStagierePere) > 0) {
				if (stagiairePere.getChampsFilsDroit() != -1) {
					numLigne = stagiairePere.getChampsFilsDroit();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							numLigne);
				}
			} else {
				if (stagiairePere.getChampsFilCache() != -1) {
					numLigne = stagiairePere.getChampsFilCache();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							numLigne);
				}
			}
		}
		raf.close();
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParPrenom(String prenomStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listePrenom = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire
						.getPrenom()
						.toLowerCase()
						.trim()
						.contains(prenomStagiaireRecherche.toLowerCase().trim())) {
					if (listePrenom.size() != 0) {
						listePrenom.removeAll(listePrenom);
					}
					listePrenom.add(stagiaire);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listePrenom;
	}

	@Override
	public List<Stagiaire> rechercherParDepartement(
			String departementStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listeDepartement = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire
						.getDepartement()
						.toLowerCase()
						.trim()
						.contains(
								departementStagiaireRecherche.toLowerCase()
										.trim())) {
					listeDepartement.add(stagiaire);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeDepartement;
	}

	@Override
	public List<Stagiaire> rechercherParPromo(String promoStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listePromo = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getPromo().toLowerCase().trim()
						.contains(promoStagiaireRecherche.toLowerCase().trim())) {
					listePromo.add(stagiaire);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listePromo;
	}

	@Override
	public List<Stagiaire> rechercherParAnnee(String anneeStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listeAnnee = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getAnnee().toLowerCase().trim()
						.contains(anneeStagiaireRecherche.toLowerCase().trim())) {
					listeAnnee.add(stagiaire);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAnnee;
	}

	public List<Stagiaire> rechercherEnMulticritere(String nom, String prenom,
			String departement, String promo, String annee,
			List<Stagiaire> listeARecuperer, String chemainRaf)
			throws Exception {
		
		if (!nom.equals("")) {
			System.out.println("dans nom non null");
			List<Stagiaire> laListe = rechercherParNom(nom, chemainRaf, 0);
			if (!prenom.equals("")) {
				System.out.println("prenom non null");
				List<Stagiaire> listePrenom = rechercherParPrenom(prenom, laListe);
				laListe.removeAll(laListe);
				laListe = listePrenom;
			}
			if (!departement.equals("")) {
				System.out.println("departement non null");
				List<Stagiaire> listeDepartement = rechercherParDepartement(departement, laListe);
				laListe.clear();
				laListe = listeDepartement;
			}
			if (!promo.equals("")) {
				System.out.println("promo non null");
				List<Stagiaire> listePromo = rechercherParPromo(promo, laListe);
				laListe.clear();
				laListe = listePromo;
			}
			if (!annee.equals("")) {
				System.out.println("annee non null");
				List<Stagiaire> listeAnnee = rechercherParAnnee(annee, laListe);
				laListe.clear();
				laListe = listeAnnee;
			}
			listeAAfficher = laListe;
		}
		else {
			System.out.println("dans nom null");
			if (!prenom.equals("")) {
				System.out.println("prenom non null");
				listeARecuperer = rechercherParPrenom(prenom, listeARecuperer);
			}
			if (!departement.equals("")) {
				System.out.println("departement non null");
				listeARecuperer = rechercherParDepartement(departement, listeARecuperer);
			}
			if (!promo.equals("")) {
				System.out.println("promo non null");
				listeARecuperer = rechercherParPromo(promo, listeARecuperer);
			}
			if (!annee.equals("")) {
				System.out.println("annee non null");
				listeARecuperer = rechercherParAnnee(annee, listeARecuperer);
			}
			listeAAfficher = listeARecuperer;
		}
		return listeAAfficher;

	}
}
