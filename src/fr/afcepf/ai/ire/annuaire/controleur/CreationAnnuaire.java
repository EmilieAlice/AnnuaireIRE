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

public class CreationAnnuaire {

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

	protected String fichierARecuperer = "C:/Users/Stagiaire/Desktop/STAGIAIRES.DON";
	protected String fichierAEnregistrer = "C:/Users/Stagiaire/Desktop/fichierStagiaires.bin";

	public void init() {
		try {
			InputStream isr = new FileInputStream(fichierARecuperer);
			InputStreamReader fr = new InputStreamReader(isr,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);

			RandomAccessFile annuaireBinaire = new RandomAccessFile(
					fichierAEnregistrer, "rw");

//			stagiairePere = recupereStagiaire(br);
//			annuaireBinaire.writeBytes(stagiairePere.donneLeStagiaire() + "\r\n");

			String ligneRecuperee = "";

			int nbElementACopie = 5;
			int numeroLigne = 1;
			while ((ligneRecuperee = br.readLine()) != null) {

				int positionLignePere = 0;
				String rubrique = "";

				if (!ligneRecuperee.contains("*")) {
					stagiaire = new Stagiaire();
					stagiairePere = new Stagiaire();
					for (int indice = 0; indice < 5; indice++) {
						rubrique = br.readLine();
						stagiaire = traiterRetourLigne(rubrique, indice);
					}
				}
				annuaireBinaire.writeBytes(stagiaire.donneLeStagiaire() + "\r\n");

				numeroLigne++;
			}
			fr.close();
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Fichier inexistant");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erreur de chargement de l'application");
		}

	}

	private Stagiaire recupereStagiaire(BufferedReader br) {
		stagiaire = new Stagiaire();
		String rubrique = "";
		try {
			for (int indice = 0; indice < 5; indice++) {
				rubrique = br.readLine();
				stagiaire = traiterRetourLigne(rubrique, indice);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Stagiaire non recupere");
		}
		return stagiaire;
	}

	public static Stagiaire traiterRetourLigne(String rubrique, int indice) {
		switch (indice) {
		case 0:
			stagiaire.setNom(ajouteEspace(rubrique, NOM));
			break;
		case 1:
			stagiaire.setPrenom(ajouteEspace(rubrique, PRENOM));
			break;
		case 2:
			stagiaire.setDepartement(ajouteEspace(rubrique, DEPARTEMENT));
			break;
		case 3:
			stagiaire.setPromo(ajouteEspace(rubrique, PROMO));
			break;
		case 4:
			stagiaire.setAnnee(ajouteEspace(rubrique, ANNEE));
			stagiaire.setChampPere(ajouteEspace("-1", PERE));
			stagiaire.setChampFilsGauche(ajouteEspace("-1", FILSGAUCHE));
			stagiaire.setChampFilsDroit(ajouteEspace("-1", FILSDROIT));
			stagiaire.setChampFilsCache(ajouteEspace("-1", FILSCACHE));
			break;
		}
		return stagiaire;
	}

	public static String ajouteEspace(String rubrique, int constante) {
		int longueur = constante - rubrique.length();
		for (int i = 0; i < longueur; i++) {
			rubrique = rubrique + " ";
		}
		return rubrique;
	}

}
