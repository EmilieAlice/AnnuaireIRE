package fr.afcepf.ai.ire.modele;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class GestionStagiaire implements IGestionStagiaire {

	@Override
	public void ajouter(Stagiaire stagiaire, String chemainDuRaf,
			CreationAjoutArbreBinaire caab) {
		try {
			RandomAccessFile raf = new RandomAccessFile(chemainDuRaf, "rwd");
			int nbLigne = (int) ((raf.length() / CreationAjoutArbreBinaire.LONGUEURLIGNE));
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
			if (unStagiaire.getNom().trim()
					.compareToIgnoreCase(stagiaireAComparer.getNom().trim()) < 0) {
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
			} else {
				fichierAStructurer.seek(0);
				int ligneRacine = fichierAStructurer.readInt();

				if (fils == ligneRacine) {
					stagiaireAComparer = rechercherBonStagiaireCache(
							fichierAStructurer, unStagiaire,
							stagiaireAComparer,
							stagiaireAComparer.getChampsFilCache());

					int numeroLigneStagiaireARemonter = stagiaireAComparer
							.getChampsFilCache();

					if (stagiaireAComparer.getChampsFilCache() != -1) {

						Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
								.lireUnStagiaire(fichierAStructurer,
										numeroLigneStagiaireARemonter);

						numeroDeLigneStagiaire = stagiaireARemonter
								.getChampsPere();

						remonterFilsCacheDansArbre(fichierAStructurer,
								stagiaireARemonter,
								numeroLigneStagiaireARemonter,
								stagiaireAComparer, numeroDeLigneStagiaire,
								positionChamps);
					} else {

						if (stagiaireAComparer.getChampsFilCache() != -1) {

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
									.lireUnStagiaire(fichierAStructurer,
											numeroLigneStagiaireARemonter);

							remonterFilsDansArbre(fichierAStructurer,
									stagiaireARemonter,
									stagiaireAComparer.getChampsFilsGauche(),
									stagiaireAComparer, numeroDeLigneStagiaire,
									positionChamps,
									CreationAjoutArbreBinaire.POSITIONPERE);

						} else {
							CreationAjoutArbreBinaire
									.modifierFilsDuPere(
											fichierAStructurer,
											numeroLigneStagiaireARemonter,
											numeroDeLigneStagiaire,
											CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}
					}
				} else {
					numeroDeLigneStagiaire = fils;
					if (stagiaireAComparer.getChampsFilCache() != -1) {

						stagiaireAComparer = rechercherBonStagiaireCache(
								fichierAStructurer, unStagiaire,
								stagiaireAComparer,
								stagiaireAComparer.getChampsFilCache());

						int numeroLigneStagiaireARemonter = stagiaireAComparer
								.getChampsFilCache();

						if (numeroLigneStagiaireARemonter != -1) {

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire
									.lireUnStagiaire(fichierAStructurer,
											numeroLigneStagiaireARemonter);

							numeroDeLigneStagiaire = stagiaireARemonter
									.getChampsPere();

							remonterFilsCacheDansArbre(fichierAStructurer,
									stagiaireARemonter,
									stagiaireAComparer.getChampsFilCache(),
									stagiaireAComparer, numeroDeLigneStagiaire,
									positionChamps);
						} else {
							CreationAjoutArbreBinaire
									.modifierFilsDuPere(
											fichierAStructurer,
											numeroLigneStagiaireARemonter,
											numeroDeLigneStagiaire,
											CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}
					}

					else if (stagiaireAComparer.getChampsFilsDroit() == -1) {
						CreationAjoutArbreBinaire.modifierPereDuStagiaire(
								fichierAStructurer,
								stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere());

						CreationAjoutArbreBinaire.modifierFilsDuPere(
								fichierAStructurer,
								stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere(),
								positionChamps);
					}

					else {
						if (stagiaireAComparer.getChampsFilsGauche() == -1) {
							CreationAjoutArbreBinaire.modifierPereDuStagiaire(
									fichierAStructurer,
									stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere());

							CreationAjoutArbreBinaire.modifierFilsDuPere(
									fichierAStructurer,
									stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere(),
									positionChamps);

						} else {
							int numeroLigneStagiaireARemonter = stagiaireAComparer
									.getChampsFilsGauche();
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
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer, numeroLigneStagiaireARemonter,
					stagiaireAComparer.getChampsPere());
			if (stagiaireAComparer.getChampsPere() == -1) {
				fichierAStructurer.seek(0);
				fichierAStructurer.writeInt(numeroLigneStagiaireARemonter);
			} else {
				CreationAjoutArbreBinaire.modifierFilsDuPere(
						fichierAStructurer, numeroLigneStagiaireARemonter,
						stagiaireAComparer.getChampsPere(), positionChamps);
			}
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					numeroLigneStagiaireARemonter,
					stagiaireAComparer.getChampsFilsGauche(),
					CreationAjoutArbreBinaire.POSITIONANNEE);
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilsDroit(),
					numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			if (stagiaireAComparer.getChampsFilCache() == -1) {
				CreationAjoutArbreBinaire.modifierFilsDuPere(
						fichierAStructurer,
						stagiaireARemonter.getChampsFilsGauche(),
						stagiaireAComparer.getChampsFilsGauche(),
						CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			}
			if (stagiaireARemonter.getChampsFilsGauche() != -1) {
				CreationAjoutArbreBinaire.modifierPereDuStagiaire(
						fichierAStructurer,
						stagiaireARemonter.getChampsFilsGauche(),
						stagiaireAComparer.getChampsFilsGauche());
			}
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilsGauche(),
					numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONPERE);
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer,
					stagiaireAComparer.getChampsFilsDroit(),
					numeroLigneStagiaireARemonter);

		} else {
			numeroLigneStagiaireARemonter = stagiaireARemonter
					.getChampsFilsDroit();
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
		fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE
				* stagiaireAComparer.getChampsPere()
				+ CreationAjoutArbreBinaire.POSITIONFILSDROIT
				+ CreationAjoutArbreBinaire.RACINE);
		int fc = fichierAStructurer.readInt();
		if (fc == numeroLigneStagiaireAcomparer) {
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilCache(),
					stagiaireAComparer.getChampsPere(),
					CreationAjoutArbreBinaire.POSITIONFILSDROIT);
		} else {
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer,
					stagiaireAComparer.getChampsFilCache(),
					stagiaireAComparer.getChampsPere(), positionChamps);
		}
		if (stagiaireAComparer.getChampsFilsGauche() != -1) {
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(
					fichierAStructurer,
					stagiaireAComparer.getChampsFilsGauche(),
					stagiaireAComparer.getChampsFilCache());
		}
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
			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(
					fichierAStructurer, stagiaireAComparer.getChampsFilCache());
			rechercherBonStagiaireCache(fichierAStructurer, unStagiaire,
					stagiaireAComparer, stagiaireAComparer.getChampsFilCache());
		}
		return stagiaireAComparer;
	}

	@Override
	public void miseAJour(RandomAccessFile fichierAStructurer,
			Stagiaire unStagiaire, Stagiaire unStagiairePourModifier,
			int indexPere, int numeroDeLigneStagiaire, int positionChamps,
			int fils) {

		Stagiaire stagiaireAComparer = new Stagiaire();

		try {
			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(
					fichierAStructurer, indexPere);
			if (unStagiaire.getNom().trim()
					.compareToIgnoreCase(stagiaireAComparer.getNom().trim()) < 0) {
				fils = stagiaireAComparer.getChampsFilsGauche();
				positionChamps = CreationAjoutArbreBinaire.POSITIONPERE;
				miseAJour(fichierAStructurer, unStagiaire,
						unStagiairePourModifier, fils, numeroDeLigneStagiaire,
						positionChamps, fils);

			} else if (unStagiaire.getNom().trim()
					.compareToIgnoreCase(stagiaireAComparer.getNom().trim()) > 0) {
				fils = stagiaireAComparer.getChampsFilsDroit();
				positionChamps = CreationAjoutArbreBinaire.POSITIONFILSGAUCHE;
				miseAJour(fichierAStructurer, unStagiaire,
						unStagiairePourModifier, fils, numeroDeLigneStagiaire,
						positionChamps, fils);

			} else {

				stagiaireAComparer = rechercherBonStagiaireCache(
						fichierAStructurer, unStagiaire, stagiaireAComparer,
						stagiaireAComparer.getChampsFilCache());

				setStagiaire(fichierAStructurer, stagiaireAComparer,
						unStagiairePourModifier, fils);

			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void setStagiaire(RandomAccessFile fichierAStructurer,
			Stagiaire stagiaireAComparer, Stagiaire unStagiairePourModifier,
			int fils) {

		remplaceChampsStagiaire(stagiaireAComparer, unStagiairePourModifier);

		String rubriqueNom = CreationAjoutArbreBinaire
				.ajouteEspace(unStagiairePourModifier.getNom(),
						CreationAjoutArbreBinaire.NOM);
		String rubriquePrenom = CreationAjoutArbreBinaire.ajouteEspace(
				unStagiairePourModifier.getPrenom(),
				CreationAjoutArbreBinaire.PRENOM);
		String rubriqueDepartement = CreationAjoutArbreBinaire.ajouteEspace(
				unStagiairePourModifier.getDepartement(),
				CreationAjoutArbreBinaire.DEPARTEMENT);
		String rubriquePromo = CreationAjoutArbreBinaire.ajouteEspace(
				unStagiairePourModifier.getPromo(),
				CreationAjoutArbreBinaire.PROMO);
		String rubriqueAnnee = CreationAjoutArbreBinaire.ajouteEspace(
				unStagiairePourModifier.getAnnee(),
				CreationAjoutArbreBinaire.ANNEE);
		try {
			fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE
					* fils + CreationAjoutArbreBinaire.RACINE);
			fichierAStructurer.writeChars(rubriqueNom + rubriquePrenom
					+ rubriqueDepartement + rubriquePromo + rubriqueAnnee);

		} catch (Exception e) {
			e.getMessage();
		}

	}

	public static Stagiaire remplaceChampsStagiaire(
			Stagiaire stagiaireAComparer, Stagiaire unStagiairePourModifier) {

		if (unStagiairePourModifier.getNom().equals("")) {
			unStagiairePourModifier.setNom(stagiaireAComparer.getNom());
		}
		if (unStagiairePourModifier.getPrenom().equals("")) {
			unStagiairePourModifier.setPrenom(stagiaireAComparer.getPrenom());
		}
		if (unStagiairePourModifier.getDepartement().equals("")) {
			unStagiairePourModifier.setDepartement(stagiaireAComparer
					.getDepartement());
		}
		if (unStagiairePourModifier.getPromo().equals("")) {
			unStagiairePourModifier.setPromo(stagiaireAComparer.getPromo());
		}
		if (unStagiairePourModifier.getAnnee().equals("")) {
			unStagiairePourModifier.setAnnee(stagiaireAComparer.getAnnee());
		}
		return unStagiairePourModifier;
	}

	@Override
	public List<Stagiaire> rechercherParNom(String nomStagiaireRecherche,
			String chemainRaf, int indexPere, List<Stagiaire> listeAAfficher)
			throws Exception {
		RandomAccessFile raf = new RandomAccessFile(chemainRaf, "r");
		Stagiaire stagiairePere = CreationAjoutArbreBinaire.lireUnStagiaire(
				raf, indexPere);
		String nomStagierePere = stagiairePere.getNom().toLowerCase().trim();
		String nomStagiaireARechercher = nomStagiaireRecherche.toLowerCase()
				.trim();
		if (nomStagierePere.startsWith(nomStagiaireARechercher)) {
			listeAAfficher.add(stagiairePere);
			if (stagiairePere.getChampsFilCache() != -1) {
				indexPere = stagiairePere.getChampsFilCache();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, indexPere,
						listeAAfficher);
			}
			if (stagiairePere.getChampsFilsGauche() != -1) {
				indexPere = stagiairePere.getChampsFilsGauche();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, indexPere,
						listeAAfficher);
			}
			if (stagiairePere.getChampsFilsDroit() != -1) {
				indexPere = stagiairePere.getChampsFilsDroit();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, indexPere,
						listeAAfficher);
			}
		} else {
			if (nomStagiaireARechercher.compareToIgnoreCase(nomStagierePere) < 0) {
				if (stagiairePere.getChampsFilsGauche() != -1) {
					indexPere = stagiairePere.getChampsFilsGauche();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							indexPere, listeAAfficher);
				}
			} else if (nomStagiaireARechercher
					.compareToIgnoreCase(nomStagierePere) > 0) {
				if (stagiairePere.getChampsFilsDroit() != -1) {
					indexPere = stagiairePere.getChampsFilsDroit();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							indexPere, listeAAfficher);
				}
			} else {
				if (stagiairePere.getChampsFilCache() != -1) {
					indexPere = stagiairePere.getChampsFilCache();
					rechercherParNom(nomStagiaireRecherche, chemainRaf,
							indexPere, listeAAfficher);
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
			List<Stagiaire> listeARecuperer, String chemainRaf,
			List<Stagiaire> listeAAfficher) throws Exception {
		@SuppressWarnings("resource")
		RandomAccessFile raf = new RandomAccessFile(chemainRaf, "r");
		raf.seek(0);
		int indexPere = raf.readInt();
		if (!nom.equals("")) {
			List<Stagiaire> laListe = rechercherParNom(nom, chemainRaf,
					indexPere, listeAAfficher);
			if (!prenom.equals("")) {
				List<Stagiaire> listePrenom = rechercherParPrenom(prenom,
						laListe);
				laListe.clear();
				laListe = listePrenom;
			}
			if (!departement.equals("")) {
				List<Stagiaire> listeDepartement = rechercherParDepartement(
						departement, laListe);
				laListe.clear();
				laListe = listeDepartement;
			}
			if (!promo.equals("")) {
				List<Stagiaire> listePromo = rechercherParPromo(promo, laListe);
				laListe.clear();
				laListe = listePromo;
			}
			if (!annee.equals("")) {
				List<Stagiaire> listeAnnee = rechercherParAnnee(annee, laListe);
				laListe.clear();
				laListe = listeAnnee;
			}
			return laListe;
		} else {
			if (!prenom.equals("")) {
				listeARecuperer = rechercherParPrenom(prenom, listeARecuperer);
			}
			if (!departement.equals("")) {
				listeARecuperer = rechercherParDepartement(departement,
						listeARecuperer);
			}
			if (!promo.equals("")) {
				listeARecuperer = rechercherParPromo(promo, listeARecuperer);
			}
			if (!annee.equals("")) {
				listeARecuperer = rechercherParAnnee(annee, listeARecuperer);
			}
			listeAAfficher = listeARecuperer;
			return listeAAfficher;
		}

	}
}
