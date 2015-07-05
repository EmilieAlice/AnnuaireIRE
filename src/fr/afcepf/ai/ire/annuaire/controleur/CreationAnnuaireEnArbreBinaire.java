package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

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

	public void init() throws Exception {


		try {

			InputStream isr = new FileInputStream("C:/Users/Stagiaire/Desktop/STAGIAIRES.DON");
			InputStreamReader fr = new InputStreamReader(isr, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);
			RandomAccessFile fichierAStructurer = new RandomAccessFile(
					"C:/Users/Stagiaire/Desktop/fichierStagiaires.bin", "rw");
			String ligneRecuperee = "";
			int nbElementACopie = 5;
			int numeroLigne = 0;
			while ((ligneRecuperee = br.readLine()) != null) {
				int positionLignePere = 0;

				String rubrique = "";
				String ligneACopier = "";
				if (!ligneRecuperee.contains("*")) {
					for (int indice = 0; indice < nbElementACopie; indice++) {
						rubrique = ligneRecuperee;
						ligneACopier = traiterRetourLigne(indice, rubrique,
								ligneACopier);
						rubrique = "";
						ligneRecuperee = br.readLine();
					}
					ligneRecuperee = "";
				}
				ajouterStagiaire(numeroLigne, ligneACopier,
						fichierAStructurer, positionLignePere);
				//Ceci ne marche pas !!!		numeroLigne+=1;

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
			ligneACopier = ecrireChamps(rubrique, ligneACopier, NOM);
			break;
		case 1:
			ligneACopier = ecrireChamps(rubrique, ligneACopier, PRENOM);
			break;
		case 2:
			ligneACopier = ecrireChamps(rubrique, ligneACopier, DEPARTEMENT);
			break;
		case 3:
			ligneACopier = ecrireChamps(rubrique, ligneACopier, PROMO);
			break;
		case 4:
			ligneACopier = ecrireChamps(rubrique, ligneACopier, ANNEE);
			ligneACopier += "-1  -1  -1  -1  \r\n";
			break;
		}
		return ligneACopier;
	}

	/**
	 * Procedure permettant d'ecrire dans la ligneACopier rentree en argument,
	 * des champs � taille fixe dans lesquels sont renseignes differentes
	 * informations. Utilise la methode ajouteEspace
	 * 
	 * @param rubrique
	 * @param ligneACopier
	 * @param constante
	 */
	public static String ecrireChamps(String rubrique, String ligneACopier,
			int constante) {
		rubrique = ajouteEspace(rubrique, constante);
		ligneACopier = ligneACopier + rubrique;
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
		int longueur = constante - rubrique.replace("\uFEFF", "").length();
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
	 * @param ligneAcopier
	 * @param fichierAStructurer
	 * @param positionLignePere
	 */
	public static void ajouterStagiaire(int numeroLigne, String ligneAcopier, RandomAccessFile fichierAStructurer, int positionLignePere) {
		String nomLigneACopier = ligneAcopier.substring(0, NOM);
		int fils = 0;
		boolean isVide = true;
		try {
			if (isVide) {
				fichierAStructurer.writeChars(ligneAcopier);
				isVide = false;

			} else {
				fichierAStructurer.seek(LONGUEURLIGNE * positionLignePere);
				String nomLigneAComparer = lireChaine(fichierAStructurer, NOM);
				if (nomLigneACopier.compareTo(nomLigneAComparer) < 0) {
					rechercherPosition(numeroLigne, ligneAcopier,
							positionLignePere, fils, fichierAStructurer);

				} else {
					if (nomLigneACopier.compareTo(nomLigneAComparer) > 0) {
						fils = 1;
						rechercherPosition(numeroLigne, ligneAcopier,
								positionLignePere, fils, fichierAStructurer);
					} else {
						fils = 2;
						rechercherPosition(numeroLigne, ligneAcopier,
								positionLignePere, fils, fichierAStructurer);					
					}
				}
			}
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
	 * @param ligneAcopier
	 * @param positionLignePere
	 * @param fils
	 * @param fichierAStructurer
	 */
	private static void rechercherPosition(int numeroLigne,
			String ligneAcopier, int positionLignePere, int fils,
			RandomAccessFile fichierAStructurer) {

		String filsLigneACopier = recupChamps(ligneAcopier, fils);
		if (!filsLigneACopier.equals("-1")) {
			positionLignePere = Integer.parseInt(filsLigneACopier);
			ajouterStagiaire(numeroLigne, ligneAcopier, fichierAStructurer,
					positionLignePere);
		} else {
			ligneAcopier = modifierPere(ligneAcopier, positionLignePere);
			positionLignePere = numeroLigne;
		}
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
		String filsLigneACopier = "";
		switch (fils) {
		case 0:
			filsLigneACopier = ligneAcopier.substring(PERE, FILSGAUCHE);
			break;
		case 1:
			filsLigneACopier = ligneAcopier.substring(FILSGAUCHE, FILSDROIT);
			break;
		case 2:
			filsLigneACopier = ligneAcopier.substring(FILSDROIT, FILSCACHE);
			break;
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
	private static String modifierPere(String ligneAcopier,
			int positionLignePere) {
		String personne = ligneAcopier.substring(0, ANNEE);
		String pere = ligneAcopier.substring(ANNEE, PERE);
		String fils = ligneAcopier.substring(PERE, FILSCACHE);
		pere = String.valueOf(positionLignePere);
		ligneAcopier = personne + pere + fils;
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
			chaineARetourner = chaineARetourner + fichierAStructurer.readChar();
		}
		return chaineARetourner;
	}

}
