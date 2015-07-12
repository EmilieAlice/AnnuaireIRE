package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.modele.Stagiaire;

public class GestionStagiaire implements IGestionStagiaire {
	private List<Stagiaire> listeAAfficher = new ArrayList<>();

	@Override
	public void ajouter(Stagiaire stagiaire, String chemainDuRaf, CreationAjoutArbreBinaire caab) {
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
	public void supprimerDansArbre(Stagiaire unStagiaire, int indexPere, RandomAccessFile fichierAStructurer, int numeroDeLigneStagiaire, int positionChamps,
			int fils) {

		Stagiaire stagiaireAComparer = new Stagiaire();


		try {
			int ligneRacine = indexPere;

			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, indexPere);
			System.out.println(stagiaireAComparer + " fils : " + fils);
			if (unStagiaire.getNom().trim().compareToIgnoreCase(stagiaireAComparer.getNom().trim()) < 0) {
				// fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE
				// * indexPere + CreationAjoutArbreBinaire.POSITIONPERE);
				// fils = fichierAStructurer.readInt();
				System.err.println("je suis fils gauche " + stagiaireAComparer.getChampsFilsGauche() + " de " + stagiaireAComparer.getNom());
				fils = stagiaireAComparer.getChampsFilsGauche();
				positionChamps = CreationAjoutArbreBinaire.POSITIONPERE;
				supprimerDansArbre(unStagiaire, fils, fichierAStructurer, numeroDeLigneStagiaire, positionChamps, fils);

			} else if (unStagiaire.getNom().trim().compareToIgnoreCase(stagiaireAComparer.getNom().trim()) > 0) {
				// fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE
				// * indexPere + CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
				// fils = fichierAStructurer.readInt();
				fils = stagiaireAComparer.getChampsFilsDroit();
				positionChamps = CreationAjoutArbreBinaire.POSITIONFILSGAUCHE;
				supprimerDansArbre(unStagiaire, fils, fichierAStructurer, numeroDeLigneStagiaire, positionChamps, fils);

				// QUAND TROUVE
			} else {
				System.out.println("j'y suis " + unStagiaire + " ma ligne --> " + fils);

				// SI RACINE A SUPPR
				fichierAStructurer.seek(0);
				ligneRacine = fichierAStructurer.readInt();
				fils = ligneRacine;
				if (fils == ligneRacine) {
					System.err.println("je suis une RACINE les gars ! ma ligne est " + fils);

					int numeroLigneStagiaireARemonter = stagiaireAComparer.getChampsFilsGauche();
					System.err.println("le num de ligne du stagiaire a remonter s'il n'a pas de FC est " +numeroLigneStagiaireARemonter);
					System.err.println("le stagiaire a supprimer est "+ stagiaireAComparer);

					//SI RACINE A UN FC
					if (stagiaireAComparer.getChampsFilCache() != -1) {
						System.err.println("la racine a un fils caché, donc on recherche le bon stagiaire à supprimer");

						stagiaireAComparer = rechercherBonStagiaireCache(fichierAStructurer, unStagiaire, stagiaireAComparer,
								stagiaireAComparer.getChampsFilCache());
						System.err.println("il faut m'effacer "+stagiaireAComparer);
						
//						Si RACINECACHE de la racine A UN FC
						if (stagiaireAComparer.getChampsFilCache() != -1){
							numeroLigneStagiaireARemonter = stagiaireAComparer.getChampsFilCache();
							System.err.println("niveau racine avec presence fils cache ligne " + stagiaireAComparer.getChampsFilCache());
							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
							System.err.println("le stagiaire caché à remonter est " + stagiaireARemonter);
							remonterFilsCacheDansArbre(fichierAStructurer, stagiaireARemonter, numeroLigneStagiaireARemonter, stagiaireAComparer,
									numeroDeLigneStagiaire, positionChamps);			
						}
						else {
							numeroLigneStagiaireARemonter = stagiaireAComparer.getChampsFilCache();
							System.out.println("ce fils caché à supprimer n'a pas de FC");
							System.out.println("c'est une feuille des fils caché qui est a effacée");
							CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, numeroLigneStagiaireARemonter, numeroDeLigneStagiaire, CreationAjoutArbreBinaire.POSITIONFILSDROIT);
//							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
//							System.err.println("a remonter " + numeroLigneStagiaireARemonter);
//
//							numeroDeLigneStagiaire = stagiaireARemonter.getChampsPere();
//
//							remonterFilsCacheDansArbre(fichierAStructurer, stagiaireARemonter, numeroLigneStagiaireARemonter, stagiaireAComparer,
//									numeroDeLigneStagiaire, positionChamps);
						}

					} 
					//SINON
					else {

						System.err.println("niveau racine SANS fils cache car ligne FC = " + stagiaireAComparer.getChampsFilCache());

						if (stagiaireAComparer.getChampsFilCache() == -1) {
							System.err.println("le stagiaire a comparer n'a pas de fils caché et le num de ligne du stagiaire a remonter est " +numeroLigneStagiaireARemonter);

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
							System.err.println("a remonter " + numeroLigneStagiaireARemonter);

							numeroDeLigneStagiaire = stagiaireARemonter.getChampsPere();

							remonterFilsDansArbre(fichierAStructurer, stagiaireARemonter, stagiaireAComparer.getChampsFilsGauche(), stagiaireAComparer,
									numeroDeLigneStagiaire, positionChamps, CreationAjoutArbreBinaire.POSITIONPERE);
							System.out.println(stagiaireARemonter);
							ligneRacine = numeroLigneStagiaireARemonter;

						}
						else {
							System.out.println("c'est une feuille qui est effacée");
							CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, numeroLigneStagiaireARemonter, numeroDeLigneStagiaire, CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}

					}

				}
				// PAS RACINE A SUPPR
				else {

					// index pere inchangé mais stagiaire <-- pereDuStagiare
					numeroDeLigneStagiaire = fils;
					System.err.println("pas une racine " + numeroDeLigneStagiaire);

					// SI FILS CACHE
					if (stagiaireAComparer.getChampsFilCache() != -1) {
						System.err.println("presence fils cache : " + stagiaireAComparer.getChampsFilCache());

						stagiaireAComparer = rechercherBonStagiaireCache(fichierAStructurer, unStagiaire, stagiaireAComparer,
								stagiaireAComparer.getChampsFilCache());

						int numeroLigneStagiaireARemonter = stagiaireAComparer.getChampsFilCache();
						System.err.println("pas racine FC à la ligne ligne " + stagiaireAComparer.getChampsFilCache());

						if (numeroLigneStagiaireARemonter != -1) {

							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
							System.err.println("a remonter " + numeroLigneStagiaireARemonter);

							numeroDeLigneStagiaire = stagiaireARemonter.getChampsPere();

							remonterFilsCacheDansArbre(fichierAStructurer, stagiaireARemonter, stagiaireAComparer.getChampsFilCache(), stagiaireAComparer,
									numeroDeLigneStagiaire, positionChamps);
						}
						else {
							System.out.println("c'est une feuille qui est remontée");
							CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, numeroLigneStagiaireARemonter, numeroDeLigneStagiaire, CreationAjoutArbreBinaire.POSITIONFILSDROIT);
						}

					}

					// SI stagiaire.FD -1 (existe pas) remonter le gauche
					else if (stagiaireAComparer.getChampsFilsDroit() == -1) {
						System.err.println("je teste fils droit" + " " + numeroDeLigneStagiaire + " car fils droit existe pas => "
								+ stagiaireAComparer.getChampsFilsDroit());
						System.err.println("a remonter : " + stagiaireAComparer.getChampsFilsGauche());
						CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere());

						System.err.println("position champs a modif dans pere" + positionChamps);
						CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsGauche(),
								stagiaireAComparer.getChampsPere(), positionChamps);
					}

					// SI stagiaire.FG -1 (existe pas) remonter le droit
					else {
						if (stagiaireAComparer.getChampsFilsGauche() == -1) {
							System.err.println("je teste fils gauche" + " " + numeroDeLigneStagiaire + " car fils gauche existe pas => "
									+ stagiaireAComparer.getChampsFilsGauche());
							System.err.println("a remonter : " + stagiaireAComparer.getChampsFilsDroit());
							CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere());

							System.err.println("position champs a modif dans pere" + positionChamps);

							CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(),
									stagiaireAComparer.getChampsPere(), positionChamps);

						}
						// si deux fils pour stagiaire a suppr
						else {
							fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE * numeroDeLigneStagiaire + CreationAjoutArbreBinaire.POSITIONFILSGAUCHE
									+ CreationAjoutArbreBinaire.RACINE);
							int numeroLigneStagiaireARemonter = fichierAStructurer.readInt();
							System.out.println("a remonter : "+ numeroLigneStagiaireARemonter);
							Stagiaire stagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
							remonterFilsDansArbre(fichierAStructurer, stagiaireARemonter, numeroLigneStagiaireARemonter, stagiaireAComparer,
									numeroDeLigneStagiaire, positionChamps, CreationAjoutArbreBinaire.POSITIONPERE);
						}
					}
				}
				// supprimerDansFichier(fichierAStructurer,
				// numeroDeLigneStagiaire);
				System.out.println("j'ai changé " + unStagiaire);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void remonterFilsDansArbre(RandomAccessFile fichierAStructurer, Stagiaire stagiaireARemonter, int numeroLigneStagiaireARemonter,
			Stagiaire stagiaireAComparer, int numeroLigneStagiaireAcomparer, int positionChamps, int positionChampsStagiaireARemonter) throws Exception {

		if (stagiaireARemonter.getChampsFilsDroit() == -1) {

			// MODIF PERE DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter, stagiaireAComparer.getChampsPere());
			if (stagiaireAComparer.getChampsPere() == -1) {
				fichierAStructurer.seek(0);
				fichierAStructurer.writeInt(numeroLigneStagiaireARemonter);
			}
			// MODIF PERE DU FG DU STA A COMP  OK
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, numeroLigneStagiaireARemonter, stagiaireAComparer.getChampsFilsGauche(),
					CreationAjoutArbreBinaire.POSITIONANNEE);
			// MODIF FILS DROIT DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(), numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			// MODIF FILS DROIT DU PERE DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireARemonter.getChampsFilsGauche(),
					stagiaireAComparer.getChampsFilsGauche(), CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
			// MODIF PERE FILS GAUCHE DU STA A REMONTER
			if (stagiaireARemonter.getChampsFilsGauche() != -1) {
				CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireARemonter.getChampsFilsGauche(),
						stagiaireAComparer.getChampsFilsGauche());
			} else {
				System.out.println("pas de fils gauche");
			}
			// MODIF FILS GAUCHE DU STAGIAIRE A REMONTER
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsGauche(), numeroLigneStagiaireARemonter,
					CreationAjoutArbreBinaire.POSITIONPERE);
			// MODIF PERE DU FILS DROIT DU STAGIAIRE A SUPPR
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(), numeroLigneStagiaireARemonter);

		} else {
			numeroLigneStagiaireARemonter = stagiaireARemonter.getChampsFilsDroit();
			System.out.println("a remonter : "+numeroLigneStagiaireARemonter);
			Stagiaire nouveauStagiaireARemonter = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter);
			System.out.println("Pas le bon stagiaire à remonter, on change pour "+ nouveauStagiaireARemonter);
			remonterFilsDansArbre(fichierAStructurer, nouveauStagiaireARemonter, numeroLigneStagiaireARemonter, stagiaireAComparer,
					numeroLigneStagiaireAcomparer, positionChamps, CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
		}

	}

	public static void remonterFilsCacheDansArbre(RandomAccessFile fichierAStructurer, Stagiaire stagiaireARemonter, int numeroLigneStagiaireARemonter,
			Stagiaire stagiaireAComparer, int numeroLigneStagiaireAcomparer, int positionChamps) throws Exception {

		System.err.println("entre dans procedure remonterFilsCache");

		// ON SE MET SUR LA LIGNE DU FILS CACHE ET ON MODIF SON PERE ET SES FILS
		CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, numeroLigneStagiaireARemonter, stagiaireAComparer.getChampsPere());
		CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsGauche(), numeroLigneStagiaireARemonter,
				CreationAjoutArbreBinaire.POSITIONPERE);
		CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(), numeroLigneStagiaireARemonter,
				CreationAjoutArbreBinaire.POSITIONFILSGAUCHE);
		// ON CHANGE LE PERE DU STA A SUPPR
		// ON VERIF SI LE STA A SUPPR A UN FILS CACHE
		fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE * stagiaireAComparer.getChampsPere() + CreationAjoutArbreBinaire.POSITIONFILSDROIT
				+ CreationAjoutArbreBinaire.RACINE);
		int fc = fichierAStructurer.readInt();
		if (fc ==numeroLigneStagiaireAcomparer) {
			//SI STA A COMP EST UN FC
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilCache(), stagiaireAComparer.getChampsPere(),
					CreationAjoutArbreBinaire.POSITIONFILSDROIT);
		}
		//SI STA A COMP N'EST PAS UN FC
		else {
			CreationAjoutArbreBinaire.modifierFilsDuPere(fichierAStructurer, stagiaireAComparer.getChampsFilCache(), stagiaireAComparer.getChampsPere(),
					CreationAjoutArbreBinaire.POSITIONFILSDROIT);
		}
		// ON MODIFIE LES FILS DU STAGIAIRE A SUPPR POUR Y METTRE EN CHAMPS PERE, LE STA A REMONTER
		//CAS OU QQCH A GAUCHE DE STAGIAIRE A SUPPR
		if (stagiaireAComparer.getChampsFilsGauche() != -1) {
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilsGauche(),
					stagiaireAComparer.getChampsFilCache());
		}
		//CAS OU QQCH A DROITE DE STAGIAIRE A SUPRR
		if (stagiaireAComparer.getChampsFilsDroit() != -1) {
			CreationAjoutArbreBinaire.modifierPereDuStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilsDroit(),
					stagiaireAComparer.getChampsFilCache());
		}
	}

	public static Stagiaire rechercherBonStagiaireCache(RandomAccessFile fichierAStructurer, Stagiaire unStagiaire, Stagiaire stagiaireAComparer,
			int ligneFilsCache) throws Exception {

		if (!unStagiaire.equals(stagiaireAComparer)) {
			System.err.println("mauvais stagiaire, recherche relancée sur : " + stagiaireAComparer.getChampsFilCache());
			stagiaireAComparer = CreationAjoutArbreBinaire.lireUnStagiaire(fichierAStructurer, stagiaireAComparer.getChampsFilCache());
			rechercherBonStagiaireCache(fichierAStructurer, unStagiaire, stagiaireAComparer, stagiaireAComparer.getChampsFilCache());
		}
		System.out.println("bon stagiaire trouvé sur fils caché");
		return stagiaireAComparer;
	}

	public static void supprimerDansFichier(RandomAccessFile fichierAStructurer, int stagiaireAsupprimer) throws Exception {
		fichierAStructurer.seek(CreationAjoutArbreBinaire.LONGUEURLIGNE * stagiaireAsupprimer + CreationAjoutArbreBinaire.RACINE);
		for (int i = 0; i < CreationAjoutArbreBinaire.POSITIONANNEE; i++) {
			fichierAStructurer.writeChars("2");
		}

		fichierAStructurer.writeInt(-2);
		fichierAStructurer.writeInt(-2);
		fichierAStructurer.writeInt(-2);
		fichierAStructurer.writeInt(-2);

	}

	public static void lireAnnuaire(int indexRacine, RandomAccessFile raf) throws Exception {
		raf = new RandomAccessFile("C:/Users/Stagiaire/Desktop/fichierStagiaires.bin", "r");
		parcourirAnnuaire(raf, indexRacine);
	}

	public static void parcourirAnnuaire(RandomAccessFile raf, int indexRacine) {
		Stagiaire sta = CreationAjoutArbreBinaire.lireUnStagiaire(raf, indexRacine);
		if (sta.getChampsFilsGauche() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilsGauche());
		}
		if (sta.getChampsFilCache() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilCache());
		}
		System.out.println(sta);
		if (sta.getChampsFilsDroit() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilsDroit());
		}
	}

	@Override
	public void miseAJour(Stagiaire stagiaire) {

	}

	@Override
	public List<Stagiaire> rechercherParNom(String nomStagiaireRecherche, String chemainRaf, int numLigne) throws Exception {

		RandomAccessFile raf = new RandomAccessFile(chemainRaf, "r");
		String nomPereBase = CreationAjoutArbreBinaire.lireUnStagiaire(raf, 0).getNom().toLowerCase().trim();
		Stagiaire stagiairePere = CreationAjoutArbreBinaire.lireUnStagiaire(raf, numLigne);
		String nomPere = stagiairePere.getNom().toLowerCase().trim();
		String nomStag = nomStagiaireRecherche.toLowerCase().trim();

		boolean debutePar = nomPere.startsWith(nomStag);
		if (debutePar) {
			if (nomPereBase.equals(nomPere)) {
				if (stagiairePere.getChampsFilCache() == -1) {
					numLigne = stagiairePere.getChampsFilCache();
					rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
				}
				listeAAfficher.add(stagiairePere);
			}
			if (nomStagiaireRecherche.trim().compareToIgnoreCase(stagiairePere.getNom().trim()) < 0) {
				if (stagiairePere.getChampsFilsGauche() != -1) {
					numLigne = stagiairePere.getChampsFilsGauche();
					rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
				}
			} else if (nomStagiaireRecherche.trim().compareToIgnoreCase(stagiairePere.getNom().trim()) > 0) {
				if (stagiairePere.getChampsFilsDroit() != -1) {
					numLigne = stagiairePere.getChampsFilsDroit();
					rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
				}
			} else {
				if (stagiairePere.getChampsFilCache() != -1) {
					numLigne = stagiairePere.getChampsFilCache();
					rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
				}
				listeAAfficher.add(stagiairePere);
			}

		}

		raf.close();
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParPromo(String promoStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		listeAAfficher.clear();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getPromo().toLowerCase().trim().contains(promoStagiaireRecherche.toLowerCase().trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getDepartement(), stagiaire.getPromo(), stagiaire
							.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParDepartement(String departementStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		listeAAfficher.clear();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getDepartement().toLowerCase().trim().contains(departementStagiaireRecherche.toLowerCase().trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getDepartement(), stagiaire.getPromo(), stagiaire
							.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParPrenom(String prenomStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		try {
			listeAAfficher.clear();
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getPrenom().toLowerCase().trim().contains(prenomStagiaireRecherche.toLowerCase().trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getDepartement(), stagiaire.getPromo(), stagiaire
							.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParAnnee(String anneeStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		listeAAfficher.clear();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getAnnee().toLowerCase().trim().contains(anneeStagiaireRecherche.toLowerCase().trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(), stagiaire.getPrenom(), stagiaire.getDepartement(), stagiaire.getPromo(), stagiaire
							.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

}
