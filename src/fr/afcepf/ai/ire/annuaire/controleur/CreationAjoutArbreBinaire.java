package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import fr.afcepf.ai.ire.annuaire.modele.Stagiaire;

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
	static final int LONGUEURLIGNE = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE ) * 2 + 4*4;
	static final int POSITIONANNEE = (NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE)*2;
	static final int POSITIONPERE = ((NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE)*2 + 4);
	static final int POSITIONFILSGAUCHE = ((NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE)*2 + 4*2);
	static final int POSITIONFILSDROIT = ((NOM + PRENOM + DEPARTEMENT + PROMO
			+ ANNEE)*2 + 4*3);
	static final int POSITIONFILSCACHE = LONGUEURLIGNE;

	static int indexPere = 0;


	public void init() throws Exception {


		try {

			InputStream isr = new FileInputStream(
					"C:/Users/Rose/Workspace/ProjetUnRessources/STAGIAIRES-SHORTLIST.txt");
			InputStreamReader fr = new InputStreamReader(isr,
					StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(fr);
			RandomAccessFile fichierAStructurer = new RandomAccessFile(
					"C:/Users/Rose/Desktop/fichierStagiaires.bin", "rw");

			String ligneRecuperee = "";
			int nbElementACopie = 5;
			int numeroLigne = 0;
			int positionLignePere = 0;
			while ((ligneRecuperee = br.readLine()) != null) {

				String rubrique = "";
				String ligneACopier = "";

				StagiaireBis unStagiaire = new StagiaireBis();

				if (!ligneRecuperee.contains("*")) {

					for (int indice = 0; indice < nbElementACopie; indice++) {
						rubrique = ligneRecuperee;
						creerUnstagiaire(rubrique, unStagiaire, indice);

						//INITIALISE LES CHAMPS A -1
						unStagiaire.setChampsPere(-1);
						unStagiaire.setChampsFilsGauche(-1);
						unStagiaire.setChampsFilsDroit(-1);
						unStagiaire.setChampsFilsCache(-1);
						rubrique = "";

						//PASSE A LA LIGNE SUIVANTE
						ligneRecuperee = br.readLine();
					}

					ligneRecuperee = "";

					formaterEnRubrique(unStagiaire, fichierAStructurer);

					//TEST OK OK OK
					System.out.println("Numero Stagiaire : " + numeroLigne);

					//TEST OK
					//System.out.println(LONGUEURLIGNE);
					//affiche 206 octets


					//creerArbre
					creerArbre(unStagiaire, fichierAStructurer, numeroLigne);

				}
				numeroLigne+=1;

				//TEST OK OK OK
				//System.out.println(unStagiaire.donneUnStagiaireEntier());
				//System.out.println(numeroLigne);

			}

			fr.close();
			br.close();


		} catch (FileNotFoundException e) {
			System.out.println("ne trouve pas le fichier");
		} catch (Exception e) {
			System.out.println("Erreur Exception dans Init");
		}


		//TEST


	}


	public static void creerUnstagiaire (String rubrique, StagiaireBis unStagiaire, int indice){

		switch (indice) {
		case 0:
			//attention au BOM (vu dans bloc notes)
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

		//VERIF DONNEES OK OK OK
		//		System.out.println(unStagiaire.donneUnStagiaireEntier());
		//		System.out.println(unStagiaire.donneUnStagiaireEntier().length());

	}


	public static void formaterEnRubrique(StagiaireBis unStagiaire, RandomAccessFile fichierAStructurer) {

		String rubriqueNom = ajouteEspace(unStagiaire.getNom(), NOM);
		String rubriquePrenom = ajouteEspace(unStagiaire.getPrenom(), PRENOM);
		String rubriqueDepartement = ajouteEspace(unStagiaire.getDepartement(), DEPARTEMENT);
		String rubriquePromo = ajouteEspace(unStagiaire.getPromo(), PROMO);
		String rubriqueAnnee = ajouteEspace(unStagiaire.getAnnee(), ANNEE);

		try {

			long finDeFichier = fichierAStructurer.length();
			fichierAStructurer.seek(finDeFichier);

			fichierAStructurer.writeChars(rubriqueNom + rubriquePrenom + rubriqueDepartement + rubriquePromo + rubriqueAnnee);

			fichierAStructurer.writeInt(unStagiaire.getChampsPere());
			fichierAStructurer.writeInt(unStagiaire.getChampsFilsGauche());
			fichierAStructurer.writeInt(unStagiaire.getChampsFilsDroit());
			fichierAStructurer.writeInt(unStagiaire.getChampsFilCache());

			//			//TEST syso LIRE INT OK OK OK
			//			System.out.println(fichierAStructurer.read());

		} catch (Exception e) {
			System.out.println("Pas d'écriture sur le raf");
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



	public static String recupChampsDansUneLigne(RandomAccessFile fichierAStructurer, int numeroDeLigne, int champsDebut, int champsFin){

		String champsRecupere = "";

		try {
			fichierAStructurer.seek(LONGUEURLIGNE*numeroDeLigne + champsDebut);
			for(int i=0; i<(champsFin-champsDebut);i++){
				champsRecupere += fichierAStructurer.readChar();
			}
		} catch (IOException e) {
			System.out.println("Erreur recupChampsDansUneLigne");
		}

		//ENLEVER LES ESPACES POUR LES DOUBLONS
		champsRecupere = champsRecupere.trim();
		return champsRecupere;
	}



	public static int creerArbre (StagiaireBis unStagiaire, RandomAccessFile fichierAStructurer, int numeroDeLigne) throws Exception{

		fichierAStructurer.seek(LONGUEURLIGNE*indexPere);
		String nomDuPere = recupChampsDansUneLigne(fichierAStructurer, indexPere, 0, NOM);
		String nomStagiaireFils = unStagiaire.getNom();
		int champs =0;

		if(numeroDeLigne>0 && fichierAStructurer.length()>LONGUEURLIGNE){

			int chiffreDeComparaison = nomStagiaireFils.compareTo(nomDuPere);
			int fils = 0;

			//TEST OK OK OK
			System.out.println("\t" + nomStagiaireFils + "/" + nomDuPere + " --> chiffre de comparaison : " + chiffreDeComparaison);

			//
			if(chiffreDeComparaison<0){
				champs = POSITIONPERE;
				rechercherPere(unStagiaire, fichierAStructurer, fils, champs, numeroDeLigne);

			}
			else {
				if (chiffreDeComparaison>0) {
					champs = POSITIONFILSGAUCHE;
					fils =1; 
					rechercherPere(unStagiaire, fichierAStructurer, fils, champs, numeroDeLigne);

				}
				else {
					champs = POSITIONFILSDROIT;
					fils=2;
					rechercherPere(unStagiaire, fichierAStructurer, fils, champs, numeroDeLigne);
				}
			}
		}
		return numeroDeLigne;
	}

	public static int rechercherPere (StagiaireBis unStagiaire, RandomAccessFile fichierAStructurer, int fils, int champs, int numeroDeLigne) throws Exception{

		//positionnement sur le champs fils gauche ou droite voire fils cache
		fichierAStructurer.seek(LONGUEURLIGNE*indexPere+champs);


		//TEST OK OK OK
		//			long pointeur = fichierAStructurer.getFilePointer();
		//			System.out.println("pointeur : " + pointeur);

		int champsFilsDuPere = fichierAStructurer.readInt();

		//SI FILS PAS VIDE
		if(champsFilsDuPere!=-1){
			indexPere = champsFilsDuPere;
			creerArbre(unStagiaire, fichierAStructurer, numeroDeLigne);
		}

		//SI VIDE ----------------- REVOIR LES DOUBLONS : OK OK OK
		else {
			unStagiaire.setChampsPere(indexPere);
			fichierAStructurer.seek(LONGUEURLIGNE*indexPere+champs);
			fichierAStructurer.writeInt(numeroDeLigne);
			fichierAStructurer.seek(LONGUEURLIGNE*numeroDeLigne+POSITIONANNEE);
			fichierAStructurer.writeInt(unStagiaire.getChampsPere());

		}

		//TEST OK OK OK
		System.out.println("\t" + "LectureChampsFils : " + champsFilsDuPere);
		System.out.println("\t" + "pere du stagiaire : "+ unStagiaire.getChampsPere());
		System.out.println();
		fichierAStructurer.seek(LONGUEURLIGNE*numeroDeLigne);
		//FIN TEST

		indexPere = 0;
		return numeroDeLigne;
	}



	public void afficheUneLigneDuRaf (RandomAccessFile fichierAStructurer, int numeroDeLigne) throws Exception{

		String lecture="";
		int i=0;
		fichierAStructurer.seek(LONGUEURLIGNE*numeroDeLigne);
		while(i<LONGUEURLIGNE){
			lecture += fichierAStructurer.readChar();
			i++;
		}
		System.out.println(lecture);
	}

}





