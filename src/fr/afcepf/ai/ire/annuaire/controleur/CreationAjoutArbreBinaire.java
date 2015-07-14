package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import fr.afcepf.ai.ire.modele.Stagiaire;

public class CreationAjoutArbreBinaire {

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

	final static int LONGUEURLIGNE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 4;
	static final int POSITIONANNEE = (NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2;
	static final int POSITIONPERE = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4);
	static final int POSITIONFILSGAUCHE = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 2);
	static final int POSITIONFILSDROIT = ((NOM + PRENOM + DEPARTEMENT + PROMO + ANNEE) * 2 + 4 * 3);
	static final int POSITIONFILSCACHE = LONGUEURLIGNE;

	static final int RACINE = 4;

	private String recuperation;
	private String sauvegarde;
//	private List<Stagiaire> listeStagiaire = new ArrayList<>();
	private RandomAccessFile fichierAStructurer;

	public void init(String recuperation, String sauvegarde) throws Exception {
		try {

			this.recuperation = recuperation;
			this.sauvegarde = sauvegarde;
			InputStream isr = new FileInputStream(recuperation);

			InputStreamReader fr = new InputStreamReader(isr,
					StandardCharsets.UTF_8);

			BufferedReader br = new BufferedReader(fr);
			fichierAStructurer = new RandomAccessFile(sauvegarde, "rw");

			String ligneRecuperee = "";
			int nbElementACopie = 5;
			int numeroDeLigne = 0;
			int positionLignePere = 0;

			fichierAStructurer.writeInt(0);
			while ((ligneRecuperee = br.readLine()) != null) {
				String rubrique = "";

				Stagiaire unStagiaire = new Stagiaire();
				if (!ligneRecuperee.contains("*")) {
					for (int indice = 0; indice < nbElementACopie; indice++) {
						rubrique = ligneRecuperee;
						creerUnstagiaire(rubrique, unStagiaire, indice);
						rubrique = "";
						ligneRecuperee = br.readLine();
					}
					unStagiaire.setChampsPere(-1);
					unStagiaire.setChampsFilsGauche(-1);
					unStagiaire.setChampsFilsDroit(-1);
					unStagiaire.setChampsFilsCache(-1);

					formaterEnRubrique(unStagiaire, fichierAStructurer);
					rechercherPereDuStagiaire(positionLignePere,
							fichierAStructurer, numeroDeLigne);
				}
				numeroDeLigne += 1;
			}

			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("ne trouve pas le fichier");
		} catch (Exception e) {
			System.out.println("Erreur Exception dans Init");
			e.getMessage();
		}
	}

	public List<Stagiaire> lireAnnuaire(int indexRacine,
			String chemainAnnuaireALire) throws Exception {
		List<Stagiaire> listeStagiaire = new ArrayList<>();
		RandomAccessFile raf = new RandomAccessFile(chemainAnnuaireALire, "r");
		return parcourirAnnuaire(raf, indexRacine, listeStagiaire);
	}

	private List<Stagiaire> parcourirAnnuaire(RandomAccessFile raf,
			int indexRacine, List<Stagiaire> listeStagiaire) {
		Stagiaire sta = lireUnStagiaire(raf, indexRacine);
		if (sta.getChampsFilsGauche() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilsGauche(), listeStagiaire);
		}
		if (sta.getChampsFilCache() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilCache(), listeStagiaire);
		}
		listeStagiaire.add(sta);
		if (sta.getChampsFilsDroit() != -1) {
			parcourirAnnuaire(raf, sta.getChampsFilsDroit(), listeStagiaire);
		}
		return listeStagiaire;

	}

	/**
	 * Methode permettant la creation d'un stagiaire grace a une rubrique donnée
	 * selon un indice
	 * 
	 * @param rubrique
	 * @param unStagiaire
	 * @param indice
	 */
	public static void creerUnstagiaire(String rubrique, Stagiaire unStagiaire,
			int indice) {
		switch (indice) {
		case 0:
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

	/**
	 * Methode permettant d'ecrire correctement dans un fichier
	 * 
	 * @param unStagiaire
	 * @param fichierAStructurer
	 */
	public void formaterEnRubrique(Stagiaire unStagiaire,
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
		// setNbLigne(nbLigne + 1);
	}

	/**
	 * Fonction permettant d'ajouter des espaces a une rubrique donnee en
	 * argument.
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
	 * Methode qui permet la lecture d'un stagiaire dans un fichier a une ligne
	 * donnee
	 * 
	 * @param fichierAStructurer
	 * @param numeroDeLigne
	 * @return
	 */
	public static Stagiaire lireUnStagiaire(
			RandomAccessFile fichierAStructurer, int numeroDeLigne) {
		Stagiaire unStagiaire = new Stagiaire();
		try {
			fichierAStructurer.seek(LONGUEURLIGNE * numeroDeLigne + RACINE);
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

			unStagiaire.setNom(rubriqueNom.toUpperCase().trim());
			unStagiaire.setPrenom(rubriquePrenom.substring(0, 1).toUpperCase() + rubriquePrenom.substring(1).trim());
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

	// public static String recupChampsDansUneLigne(
	// RandomAccessFile fichierAStructurer, int numeroDeLigne,
	// int champsDebut, int champsFin) {
	// String champsRecupere = "";
	// try {
	// fichierAStructurer
	// .seek(LONGUEURLIGNE * numeroDeLigne + champsDebut);
	// for (int i = 0; i < (champsFin - champsDebut); i++) {
	// champsRecupere += fichierAStructurer.readChar();
	// }
	// } catch (IOException e) {
	// System.out.println("Erreur recupChampsDansUneLigne");
	// }
	// return champsRecupere;
	// }

	/**
	 * Methode permettant de modifier le champs pere d'un stagiaire grace a son
	 * index
	 * 
	 * @param fichierAStructurer
	 * @param numeroDeLigne
	 * @param indexPere
	 */
	public static void modifierPereDuStagiaire(
			RandomAccessFile fichierAStructurer, int numeroDeLigne,
			int indexPere) {
		try {
			fichierAStructurer.seek(LONGUEURLIGNE * numeroDeLigne
					+ POSITIONANNEE + RACINE);
			fichierAStructurer.writeInt(indexPere);
			System.err.println("se met sur la ligne " + numeroDeLigne
					+ " ajoute au champs pere : " + indexPere);
		} catch (IOException e) {
			System.err.println("Pere introuvable");
			e.printStackTrace();
		}
	}

	/**
	 * Methode permettant de modifier un champ fils du pere grace a son index
	 * 
	 * @param fichierAStructurer
	 * @param numeroDeLigne
	 * @param indexPere
	 * @param champs
	 */
	public static void modifierFilsDuPere(RandomAccessFile fichierAStructurer,
			int numeroDeLigne, int indexPere, int champs) {
		try {
			fichierAStructurer
					.seek(LONGUEURLIGNE * indexPere + champs + RACINE);
			fichierAStructurer.writeInt(numeroDeLigne);

			System.err.println("se met sur ligne " + indexPere
					+ " et ecrit dans champs fils " + champs + " : "
					+ numeroDeLigne);

		} catch (IOException e) {
			System.err.println("Fils introuvable");
			e.printStackTrace();
		}
	}

	/**
	 * Methode qui permet de lire un champ dans un fichier
	 * 
	 * @param fichierAStructurer
	 * @param champs
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * Methode qui permet de rechercher la position d'un fils par rapport a son
	 * pere dans un fichier grace a leur index
	 * 
	 * @param indexPere
	 * @param fichierAStructurer
	 * @param numeroDeLigne
	 */
	public void rechercherPereDuStagiaire(int indexPere,
			RandomAccessFile fichierAStructurer, int numeroDeLigne) {
		Stagiaire unStagiaire = new Stagiaire();
		Stagiaire stagiairePere = new Stagiaire();

		int valeurFils = 0;
		int positionChamp = 0;
		try {

			unStagiaire = lireUnStagiaire(fichierAStructurer, numeroDeLigne);

			stagiairePere = lireUnStagiaire(fichierAStructurer, indexPere);

			fichierAStructurer.seek(0);
			int ligneRacine = fichierAStructurer.readInt();

			if (numeroDeLigne != ligneRacine
					&& fichierAStructurer.length() > LONGUEURLIGNE) {
				if (unStagiaire.getNom().trim()
						.compareToIgnoreCase(stagiairePere.getNom().trim()) < 0) {
					fichierAStructurer.seek(LONGUEURLIGNE * indexPere
							+ POSITIONPERE + RACINE);
					valeurFils = fichierAStructurer.readInt();
					positionChamp = POSITIONPERE;
				} else if (unStagiaire.getNom().trim()
						.compareToIgnoreCase(stagiairePere.getNom().trim()) > 0) {
					fichierAStructurer.seek(LONGUEURLIGNE * indexPere
							+ POSITIONFILSGAUCHE + RACINE);
					valeurFils = fichierAStructurer.readInt();
					positionChamp = POSITIONFILSGAUCHE;
				} else {
					fichierAStructurer.seek(LONGUEURLIGNE * indexPere
							+ POSITIONFILSDROIT + RACINE);
					valeurFils = fichierAStructurer.readInt();
					positionChamp = POSITIONFILSDROIT;
				}

				if (valeurFils == -1) {
					modifierFilsDuPere(fichierAStructurer, numeroDeLigne,
							indexPere, positionChamp);
					modifierPereDuStagiaire(fichierAStructurer, numeroDeLigne,
							indexPere);
				} else {
					indexPere = valeurFils;
					rechercherPereDuStagiaire(indexPere, fichierAStructurer,
							numeroDeLigne);
				}
			}
		} catch (Exception e) {
			System.err.println("Recherche non effectué");
		}

	}

	public String getRecuperation() {
		return recuperation;
	}

	public void setRecuperation(String recuperation) {
		this.recuperation = recuperation;
	}

	public String getSauvegarde() {
		return sauvegarde;
	}

	public void setSauvegarde(String sauvegarde) {
		this.sauvegarde = sauvegarde;
	}

	public RandomAccessFile getFichierAStructurer() {
		return fichierAStructurer;
	}

	public void setFichierAStructurer(String sauvegarde) throws Exception {
		this.sauvegarde = sauvegarde;
	}

}
