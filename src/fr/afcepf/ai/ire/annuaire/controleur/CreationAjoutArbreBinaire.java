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


public class CreationAjoutArbreBinaire extends Stagiaire {

	static final int NOM = 40;
	static final int PRENOM = 25;
	static final int DEPARTEMENT = 3;
	static final int PROMO = 12;
	static final int ANNEE = 5;
	static final int PERE = -1;
	static final int FILSGAUCHE = -1;
	static final int FILSDROIT = -1;
	static final int FILSCACHE = -1;

	static final int NBCHAMPSTOT = 4;

	static final int LONGUEURLIGNE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 4;
	static final int POSITIONANNEE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2;
	static final int POSITIONPERE = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4);
	static final int POSITIONFILSGAUCHE = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 2);
	static final int POSITIONFILSDROIT = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 3);
	static final int POSITIONFILSCACHE = LONGUEURLIGNE;

	static int indexPere = 0;

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
			int numeroDeLigne = 0;
			int positionLignePere = 0;
			while ((ligneRecuperee = br.readLine()) != null) {

				String rubrique = "";
				String ligneACopier = "";

				Stagiaire unStagiaire = new Stagiaire();

				if (!ligneRecuperee.contains("*")) {

					for (int indice = 0; indice < nbElementACopie; indice++) {
						rubrique = ligneRecuperee;
						creerUnstagiaire(rubrique, unStagiaire, indice);

						rubrique = "";

						// PASSE A LA LIGNE SUIVANTE
						ligneRecuperee = br.readLine();
					}

					// INITIALISE LES CHAMPS A -1
					unStagiaire.setChampsPere(-1);
					unStagiaire.setChampsFilsGauche(-1);
					unStagiaire.setChampsFilsDroit(-1);
					unStagiaire.setChampsFilsCache(-1);

					ligneRecuperee = "";

					formaterEnRubrique(unStagiaire, fichierAStructurer);

					// creerArbre
					creerArbre(unStagiaire, fichierAStructurer, numeroDeLigne);

					System.out.println(lireUnStagiaire(fichierAStructurer,
							numeroDeLigne).donneUnStagiaireEntier());

				}

				// affiche(fichierAStructurer, numeroDeLigne);

				numeroDeLigne += 1;

				// TEST OK OK OK
				System.out.println("numero de ligne : " + numeroDeLigne);

			}

			fr.close();
			br.close();

		} catch (FileNotFoundException e) {
			System.out.println("ne trouve pas le fichier");
		} catch (Exception e) {
			System.out.println("Erreur Exception dans Init");
		}

		// TEST

	}

	public static void creerUnstagiaire(String rubrique, Stagiaire unStagiaire,
			int indice) {

		switch (indice) {
		case 0:
			// attention au BOM (vu dans bloc notes)
			unStagiaire.setNom(rubrique.replace("\uFEFF", ""));
			break;
		case 1:
			unStagiaire.setPrenom(rubrique);
			break;
		case 2:
			unStagiaire.setDepartement(rubrique);
			break;
		case 3:
			unStagiaire.setPromo(rubrique);
			break;
		case 4:
			unStagiaire.setAnnee(rubrique);
			break;
		}

	}

	public static void formaterEnRubrique(Stagiaire unStagiaire,
			RandomAccessFile fichierAStructurer) {

		String rubriqueNom = ajouteEspace(unStagiaire.getNom(), NOM);
		String rubriquePrenom = ajouteEspace(unStagiaire.getPrenom(), PRENOM);
		String rubriqueDepartement = ajouteEspace(unStagiaire.getDepartement(),
				DEPARTEMENT);
		String rubriquePromo = ajouteEspace(unStagiaire.getPromo(), PROMO);
		String rubriqueAnnee = ajouteEspace(unStagiaire.getAnnee(), ANNEE);

		try {

			long finDeFichier = fichierAStructurer.length();
			fichierAStructurer.seek(finDeFichier);

			fichierAStructurer.writeChars(rubriqueNom + rubriquePrenom
					+ rubriqueDepartement + rubriquePromo + rubriqueAnnee);

			fichierAStructurer.writeInt(unStagiaire.getChampsPere());

			fichierAStructurer.writeInt(unStagiaire.getChampsFilsGauche());

			fichierAStructurer.writeInt(unStagiaire.getChampsFilsDroit());

			fichierAStructurer.writeInt(unStagiaire.getChampsFilCache());

		} catch (Exception e) {
			System.out.println("Pas d'ecriture sur le raf");
		}

	}

	/**
	 * Fonction permettant d'ajouter des espaces a une rubrique donnee en
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

	public static Stagiaire lireUnStagiaire(
			RandomAccessFile fichierAStructurer, int numeroDeLigne) {

		Stagiaire unStagiaire = new Stagiaire();

		try {

			fichierAStructurer.seek(LONGUEURLIGNE * numeroDeLigne);
			String rubriqueNom = LireRaf(fichierAStructurer, NOM);
			String rubriquePrenom = LireRaf(fichierAStructurer, PRENOM);
			String rubriqueDepartement = LireRaf(fichierAStructurer,
					DEPARTEMENT);
			String rubriquePromo = LireRaf(fichierAStructurer, PROMO);
			String rubriqueAnnee = LireRaf(fichierAStructurer, ANNEE);

			int champsP = fichierAStructurer.readInt();
			int champsFG = fichierAStructurer.readInt();
			int champsFD = fichierAStructurer.readInt();
			int champsFC = fichierAStructurer.readInt();

			unStagiaire.setNom(rubriqueNom.trim());
			unStagiaire.setPrenom(rubriquePrenom.trim());
			unStagiaire.setDepartement(rubriqueDepartement.trim());
			unStagiaire.setPromo(rubriquePromo.trim());
			unStagiaire.setAnnee(rubriqueAnnee.trim());
			unStagiaire.setChampsPere(champsP);
			unStagiaire.setChampsFilsGauche(champsFG);
			unStagiaire.setChampsFilsDroit(champsFD);
			unStagiaire.setChampsFilsCache(champsFC);

		} catch (Exception e) {
			System.out.println("Erreur dans procedure LireUnStagiaire");
			e.printStackTrace();
		}

		return unStagiaire;

	}

	public static String recupChampsDansUneLigne(
			RandomAccessFile fichierAStructurer, int numeroDeLigne,
			int champsDebut, int champsFin) {

		String champsRecupere = "";

		try {
			fichierAStructurer
					.seek(LONGUEURLIGNE * numeroDeLigne + champsDebut);
			for (int i = 0; i < (champsFin - champsDebut); i++) {
				champsRecupere += fichierAStructurer.readChar();
			}
		} catch (IOException e) {
			System.out.println("Erreur recupChampsDansUneLigne");
		}

		// //ENLEVER LES ESPACES POUR LES DOUBLONS
		// champsRecupere = champsRecupere.trim();
		return champsRecupere;
	}

	public static void modifierPereDuStagiaire(
			RandomAccessFile fichierAStructurer, int numeroDeLigne,
			int indexPere) {

		try {
			fichierAStructurer.seek(LONGUEURLIGNE * numeroDeLigne
					+ POSITIONANNEE);
			fichierAStructurer.writeInt(indexPere);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void modifierFilsDuPere(RandomAccessFile fichierAStructurer,
			int numeroDeLigne, int indexPere, int champs) {

		try {
			fichierAStructurer.seek(LONGUEURLIGNE * indexPere + champs);
			fichierAStructurer.writeInt(numeroDeLigne);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String LireRaf(RandomAccessFile fichierAStructurer, int champs)
			throws Exception {

		String lecture = "";
		int i = 0;
		while (i < champs) {
			lecture += fichierAStructurer.readChar();
			i++;
		}
		return lecture;
	}

	public static void rechercherPereDuStagiaire(int indexPere,
			RandomAccessFile fichierAStructurer, int numeroDeLigne) {

		Stagiaire unStagiaire = new Stagiaire();
		Stagiaire stagiairePere = new Stagiaire();
		int fils = 0;
		int position = 0;

		try {
			unStagiaire = lireUnStagiaire(fichierAStructurer, numeroDeLigne);
			stagiairePere = lireUnStagiaire(fichierAStructurer, indexPere);

			if (unStagiaire.getNom().trim()
					.compareTo(stagiairePere.getNom().trim()) < 0) {
				fichierAStructurer.seek(LONGUEURLIGNE * indexPere
						+ POSITIONPERE);
				fils = fichierAStructurer.readInt();
				position = POSITIONPERE;
			} else if (unStagiaire.getNom().trim()
					.compareTo(stagiairePere.getNom().trim()) > 0) {
				fichierAStructurer.seek(LONGUEURLIGNE * indexPere
						+ POSITIONFILSGAUCHE);
				fils = fichierAStructurer.readInt();
				position = POSITIONFILSGAUCHE;

			} else {
				fichierAStructurer.seek(LONGUEURLIGNE * indexPere
						+ POSITIONFILSDROIT);
				fils = fichierAStructurer.readInt();
				position = POSITIONFILSDROIT;
			}

			if (fils == -1) {
				modifierFilsDuPere(fichierAStructurer, numeroDeLigne,
						indexPere, position);
				modifierPereDuStagiaire(fichierAStructurer, numeroDeLigne,
						indexPere);
			} else {
				indexPere = fils;
				rechercherPereDuStagiaire(indexPere, fichierAStructurer,
						numeroDeLigne);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static int creerArbre(Stagiaire unStagiaire,
			RandomAccessFile fichierAStructurer, int numeroDeLigne)
			throws Exception {
//
//		fichierAStructurer.seek(LONGUEURLIGNE * indexPere);
//		String nomDuPere = recupChampsDansUneLigne(fichierAStructurer,
//				indexPere, 0, NOM);
//		nomDuPere = nomDuPere.trim();
//		String nomStagiaireFils = unStagiaire.getNom();
//		int champs = 0;
//
//		if (numeroDeLigne > 0 && fichierAStructurer.length() > LONGUEURLIGNE) {
//
//			int chiffreDeComparaison = nomStagiaireFils.compareTo(nomDuPere);
//
//			// TEST OK OK OK
//			System.out.println("\t" + nomStagiaireFils + "/" + nomDuPere
//					+ " --> chiffre de comparaison : " + chiffreDeComparaison);
//
//			//
//			if (chiffreDeComparaison < 0) {
//				champs = POSITIONPERE;
//				rechercherPereDuStagiaire(indexPere, fichierAStructurer,
//						numeroDeLigne, champs);
//
//			} else {
//				if (chiffreDeComparaison > 0) {
//					champs = POSITIONFILSGAUCHE;
//					rechercherPereDuStagiaire(indexPere, fichierAStructurer,
//							numeroDeLigne, champs);
//
//				} else {
//					champs = POSITIONFILSDROIT;
//					rechercherPereDuStagiaire(indexPere, fichierAStructurer,
//							numeroDeLigne, champs);
//
//				}
//			}
//		}
		return numeroDeLigne;
	}


}
