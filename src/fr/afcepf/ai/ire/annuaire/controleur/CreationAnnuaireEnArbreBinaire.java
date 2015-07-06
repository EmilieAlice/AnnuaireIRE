package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import fr.afcepf.ai.ire.modele.Stagiaire;

public class CreationAnnuaireEnArbreBinaire {

	static final int NOM = 50;
	static final int PRENOM = 25;
	static final int DEPARTEMENT = 3;
	static final int PROMO = 12;
	static final int ANNEE = 5;
	static final int PERE = 4;
	static final int FILSGAUCHE = 4;
	static final int FILSDROIT = 4;
	static final int FILSCACHE = 4;
	static final int LONGUEURLIGNE = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE + PERE + FILSGAUCHE + FILSDROIT + FILSCACHE) * 2;
	static final int POSITIONANNEE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE);
	static final int POSITIONPERE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE + PERE);
	static final int POSITIONFILSGAUCHE = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE + PERE + FILSGAUCHE);
	static final int POSITIONFILSDROITS = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE + PERE + FILSGAUCHE + FILSDROIT);
	static final int POSITIONFILSCACHE = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE + PERE + FILSGAUCHE + FILSDROIT + FILSCACHE);
	static Stagiaire stagiairePere;
	static Stagiaire stagiaire;

	public void init() throws Exception {

		try {

			InputStream isr = new FileInputStream(
					"C:/Users/Stagiaire/Desktop/STAGIAIRES.DON");
			InputStreamReader fr = new InputStreamReader(isr,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);
			RandomAccessFile fichierAStructurer = new RandomAccessFile(
					"C:/Users/Stagiaire/Desktop/fichierStagiaires.bin", "rw");

			String ligneRecuperee = "";

			int nbElementACopie = 5;
			int numeroLigne = 1;

			while ((ligneRecuperee = br.readLine()) != null) {

				int positionLignePere = 0;

				String rubrique = "";
				String ligneACopier = "";

				if (!ligneRecuperee.contains("*")) {
					stagiaire = new Stagiaire();
					stagiairePere = new Stagiaire();
					for (int indice = 0; indice < nbElementACopie; indice++) {
						rubrique = ligneRecuperee;
						ligneACopier = traiterRetourLigne(indice, rubrique,
								ligneACopier);
						rubrique = "";
						ligneRecuperee = br.readLine();
					}
					ligneRecuperee = "";
					ajouterStagiaire(numeroLigne, ligneACopier,
							fichierAStructurer, positionLignePere);
				}

				numeroLigne++;
			}
			fr.close();
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("ne trouve pas le fichier");
		} catch (Exception e) {
			System.out.println("Erreur Exception");
		}

	}

	/**
	 * Fonction permettant de traiter le retour a la ligne d'une string donnee.
	 * Utilise la methode ecrireChamps.
	 * 
	 * @param indice
	 * @param rubrique
	 * @param ligneACopier
	 * @return ligneACopier rentree en parametre
	 */
	public static String traiterRetourLigne(int indice, String rubrique,
			String ligneACopier) {
		switch (indice) {
		case 0:
			stagiaire.setNom(ajouteEspace(rubrique, NOM));
			ligneACopier = stagiaire.getNom();
			break;
		case 1:
			stagiaire.setPrenom(ajouteEspace(rubrique, PRENOM));
			ligneACopier = ligneACopier + stagiaire.getPrenom();
			break;
		case 2:
			stagiaire.setDepartement(ajouteEspace(rubrique, DEPARTEMENT));
			ligneACopier = ligneACopier + stagiaire.getDepartement();
			break;
		case 3:
			stagiaire.setPromo(ajouteEspace(rubrique, PROMO));
			ligneACopier = ligneACopier + stagiaire.getPromo();
			break;
		case 4:
			stagiaire.setAnnee(ajouteEspace(rubrique, ANNEE));
			ligneACopier = ligneACopier + stagiaire.getAnnee();
			stagiaire.setChampPere(ajouteEspace("-1", PERE));
			ligneACopier = ligneACopier + stagiaire.getChampPere();
			stagiaire.setChampFilsGauche(ajouteEspace("-1", FILSGAUCHE));
			ligneACopier = ligneACopier + stagiaire.getChampFilsGauche();
			stagiaire.setChampFilsDroit(ajouteEspace("-1", FILSDROIT));
			ligneACopier = ligneACopier + stagiaire.getChampFilsDroit();
			stagiaire.setChampFilsCache(ajouteEspace("-1", FILSCACHE));
			ligneACopier = ligneACopier + stagiaire.getChampFilsCache();
			ligneACopier = stagiaire.donneLeStagiaire() + "\r\n";
			break;
		}
		return ligneACopier;
	}

	/**
	 * Fonction permettant d'ajouter des espaces � une rubrique donnee en
	 * argument.
	 * 
	 * 
	 * @param rubrique
	 * @param constante
	 * @return la rubrique modifiee
	 */
	public static String ajouteEspace(String rubrique, int constante) {
		int longueur = constante - rubrique.length();
		for (int i = 0; i < longueur; i++) {
			rubrique = rubrique + " ";
		}
		return rubrique;
	}

	/**
	 * Procedure permettant d'ecrire dans un nouveau fichier une ligne donnee en
	 * argument. Ce fichier est structure en arbre binaire. Utilise les methodes
	 * rechercherPosition et lireChaine.
	 * 
	 * @param numeroLigne
	 * @param ligneACopier
	 * @param fichierAStructurer
	 * @param positionLignePere
	 */
	public static int ajouterStagiaire(int numeroLigne, String ligneACopier,
			RandomAccessFile fichierAStructurer, int positionLignePere) {

		int fils;
		try {
			if (fichierAStructurer.length() > 0) {
				fichierAStructurer.seek(LONGUEURLIGNE * positionLignePere);
				recupereDonneesDuPere(fichierAStructurer, stagiairePere);
				if (stagiaire.compareTo(stagiairePere) < 0) {
					fils = 0;
					rechercherPosition(numeroLigne, ligneACopier,
							positionLignePere, fils, fichierAStructurer);
				} else {
					if (stagiaire.compareTo(stagiairePere) > 0) {
						fils = 1;
						rechercherPosition(numeroLigne, ligneACopier,
								positionLignePere, fils, fichierAStructurer);
					} else {
						fils = 2;
						rechercherPosition(numeroLigne, ligneACopier,
								positionLignePere, fils, fichierAStructurer);
					}
				}
			} else {
				fichierAStructurer.writeBytes(ligneACopier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numeroLigne;
	}

	/**
	 * Procedure permettant de recuperer les champs du pere
	 * 
	 * @param fichierAStructurer
	 * @param stagiairePere2
	 */
	private static void recupereDonneesDuPere(
			RandomAccessFile fichierAStructurer, Stagiaire stagiairePere) {
		try {
			stagiairePere.setNom(lireChaine(fichierAStructurer, NOM));
			stagiairePere.setPrenom(lireChaine(fichierAStructurer, PRENOM));
			stagiairePere.setDepartement(lireChaine(fichierAStructurer,
					DEPARTEMENT));
			stagiairePere.setPromo(lireChaine(fichierAStructurer, PROMO));
			stagiairePere.setAnnee(lireChaine(fichierAStructurer, ANNEE));
			stagiairePere.setChampPere(lireChaine(fichierAStructurer, PERE));
			stagiairePere.setChampFilsGauche(lireChaine(fichierAStructurer,
					FILSGAUCHE));
			stagiairePere.setChampFilsDroit(lireChaine(fichierAStructurer,
					FILSDROIT));
			stagiairePere.setChampFilsCache(lireChaine(fichierAStructurer,
					FILSCACHE));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Procedure permettant de determiner la position d'un element en
	 * comparaison a un autre element pour l'insertion dans un arbre binaire.
	 * Utilise les methodes recupChamps, ajouterStagiaire et modifierPere.
	 * 
	 * @param numeroLigne
	 * @param ligneACopier
	 * @param positionLignePere
	 * @param fils
	 * @param fichierAStructurer
	 */
	private static void rechercherPosition(int numeroLigne,
			String ligneACopier, int positionLignePere, int fils,
			RandomAccessFile fichierAStructurer) {
		try {
			String filsLigneACopier = recupChamps(ligneACopier, fils);
			if (filsLigneACopier.equals("-1  ")) {
				ligneACopier = modifierChampPere(ligneACopier,
						positionLignePere);
				fichierAStructurer.seek(positionLignePere * LONGUEURLIGNE);
				fichierAStructurer.writeChars(modifierChampFils(stagiairePere,
						numeroLigne, fils));
				positionLignePere = numeroLigne;
			} else {
				positionLignePere = Integer.parseInt(filsLigneACopier);
				fichierAStructurer.seek(positionLignePere * LONGUEURLIGNE);
				fichierAStructurer.writeChars(modifierChampFils(stagiairePere,
						numeroLigne, fils));
				ajouterStagiaire(numeroLigne, ligneACopier, fichierAStructurer,
						positionLignePere);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Procedure permettant de modifier le champs fils du pere
	 * 
	 * @param stagiairePere
	 * @param numeroLigne
	 */
	private static String modifierChampFils(Stagiaire stagiairePere,
			int numeroLigne, int fils) {
		switch (fils) {
		case 0:
			stagiairePere.setChampFilsGauche(String.valueOf(numeroLigne));
			break;
		case 1:
			stagiairePere.setChampFilsDroit(String.valueOf(numeroLigne));
			break;
		case 2:
			stagiairePere.setChampFilsCache(String.valueOf(numeroLigne));
			break;
		}
		return stagiairePere.donneLeStagiaire();
	}

	/**
	 * Fonction permettant de recuperer l'emplacement d'un element en
	 * comparaison avec un autre element.
	 * 
	 * @param ligneAcopier
	 * @param fils
	 * @return la valeur du fils rentre en argument
	 */
	private static String recupChamps(String ligneAcopier, int fils) {
		String filsLigneACopier;
		if (fils == 0) {
			filsLigneACopier = stagiaire.getChampFilsGauche();
		} else {
			if (fils == 1) {
				filsLigneACopier = stagiaire.getChampFilsDroit();
			} else {
				filsLigneACopier = stagiaire.getChampFilsCache();
			}
		}
		return filsLigneACopier;

	}

	/**
	 * Fonction permettant de modifier la valeur d'un element.
	 * 
	 * @param ligneAcopier
	 * @param positionLignePere
	 * @return la ligne a copier modifiee
	 */
	private static String modifierChampPere(String ligneAcopier,
			int positionLignePere) {
		stagiaire.setChampPere(ajouteEspace(String.valueOf(positionLignePere),
				PERE));
		ligneAcopier = stagiaire.donneLeStagiaire();
		return ligneAcopier;
	}

	/**
	 * Fonction permettant de lire une chaine de caracteres jusqu'a un index
	 * donne
	 * 
	 * @param fichierAStructurer
	 * @param constante
	 * @return la chaine de caractere lue
	 * @throws Exception
	 */
	private static String lireChaine(RandomAccessFile fichierAStructurer,
			int constante) throws Exception {
		String chaineARetourner = "";
		for (int i = 0; i < constante; i++) {
			chaineARetourner = chaineARetourner + fichierAStructurer.readByte();
		}
		return chaineARetourner;
	}

}
