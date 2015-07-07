package fr.afcepf.ai.ire.annuaire.test;

import java.io.RandomAccessFile;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.Stagiaire;

public class CreationAnnuaireTest {

	public static void main(String[] args) {

		// CreationAnnuaireEnArbreBinaire annuaire = new
		// CreationAnnuaireEnArbreBinaire();
		// try {
		// annuaire.init();
		// System.out.println("Init ok");
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.err.println("Pas d'initialisation");
		// }

		CreationAjoutArbreBinaire caab = new CreationAjoutArbreBinaire();
		try {
			 //caab.init();
			 caab.lireAnnuaire(0, "C:/Users/Stagiaire/Desktop/fichierStagiaires.bin");
//			 System.out.println("Init ok");


//			RandomAccessFile raf = new RandomAccessFile(
//					"fichierBinaireTest.bin", "rw");
//			Stagiaire unStagiaire0 = new Stagiaire("MARTEAU", "Rémy", "97",
//					"AI95", "2015");
//			unStagiaire0.setChampsPere(-1);
//			unStagiaire0.setChampsFilsGauche(-1);
//			unStagiaire0.setChampsFilsDroit(-1);
//			unStagiaire0.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire0, raf);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire1 = new Stagiaire("SMETS", "Boris", "62",
//					"AI95", "2015");
//			unStagiaire1.setChampsPere(-1);
//			unStagiaire1.setChampsFilsGauche(-1);
//			unStagiaire1.setChampsFilsDroit(-1);
//			unStagiaire1.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire1, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 1);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire2 = new Stagiaire("FREVA", "Benjamin", "95",
//					"AI95", "2015");
//			unStagiaire2.setChampsPere(-1);
//			unStagiaire2.setChampsFilsGauche(-1);
//			unStagiaire2.setChampsFilsDroit(-1);
//			unStagiaire2.setChampsFilsCache(-1);
//
//			caab.formaterEnRubrique(unStagiaire2, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 2);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire3 = new Stagiaire("CHICHEPORTICHE", "Stéphane", "13",
//					"AI95", "2015");
//			unStagiaire3.setChampsPere(-1);
//			unStagiaire3.setChampsFilsGauche(-1);
//			unStagiaire3.setChampsFilsDroit(-1);
//			unStagiaire3.setChampsFilsCache(-1);
//
//			caab.formaterEnRubrique(unStagiaire3, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 3);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire4 = new Stagiaire("YATALIT", "Mourad", "13",
//					"AL82", "2015");
//			unStagiaire4.setChampsPere(-1);
//			unStagiaire4.setChampsFilsGauche(-1);
//			unStagiaire4.setChampsFilsDroit(-1);
//			unStagiaire4.setChampsFilsCache(-1);
//
//			caab.formaterEnRubrique(unStagiaire4, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 4);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire5 = new Stagiaire("SOUMHI", "Haroun", "13",
//					"WEB5", "2015");
//			unStagiaire5.setChampsPere(-1);
//			unStagiaire5.setChampsFilsGauche(-1);
//			unStagiaire5.setChampsFilsDroit(-1);
//			unStagiaire5.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire5, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 5);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire6 = new Stagiaire("MOUHLI", "Idriss", "13",
//					"WEB5", "2015");
//			unStagiaire6.setChampsPere(-1);
//			unStagiaire6.setChampsFilsGauche(-1);
//			unStagiaire6.setChampsFilsDroit(-1);
//			unStagiaire6.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire6, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 6);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire7 = new Stagiaire("DEROUILLON", "Lucie", "13",
//					"WEB5", "2015");
//			unStagiaire7.setChampsPere(-1);
//			unStagiaire7.setChampsFilsGauche(-1);
//			unStagiaire7.setChampsFilsDroit(-1);
//			unStagiaire7.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire7, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 7);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire8 = new Stagiaire("SLIMANI", "Mohamed", "13",
//					"WEB5", "2015");
//			unStagiaire8.setChampsPere(-1);
//			unStagiaire8.setChampsFilsGauche(-1);
//			unStagiaire8.setChampsFilsDroit(-1);
//			unStagiaire8.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire8, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 8);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire9 = new Stagiaire("TERRACOL", "Emmanuelle", "13",
//					"WEB5", "2015");
//			unStagiaire9.setChampsPere(-1);
//			unStagiaire9.setChampsFilsGauche(-1);
//			unStagiaire9.setChampsFilsDroit(-1);
//			unStagiaire9.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire9, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 9);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire10 = new Stagiaire("ABDOULAMIDE", "Riyadh", "13",
//					"WEB5", "2015");
//			unStagiaire10.setChampsPere(-1);
//			unStagiaire10.setChampsFilsGauche(-1);
//			unStagiaire10.setChampsFilsDroit(-1);
//			unStagiaire10.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire10, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 10);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire11 = new Stagiaire("BEN AISSA", "Mohamed Fadhel", "13",
//					"WEB5", "2015");
//			unStagiaire11.setChampsPere(-1);
//			unStagiaire11.setChampsFilsGauche(-1);
//			unStagiaire11.setChampsFilsDroit(-1);
//			unStagiaire11.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire11, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 11);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire12 = new Stagiaire("MARTEAU", "Rémy", "13",
//					"WEB5", "2015");
//			unStagiaire12.setChampsPere(-1);
//			unStagiaire12.setChampsFilsGauche(-1);
//			unStagiaire12.setChampsFilsDroit(-1);
//			unStagiaire12.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire12, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 12);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire13 = new Stagiaire("KECIRI", "Rachid", "13",
//					"WEB5", "2015");
//			unStagiaire13.setChampsPere(-1);
//			unStagiaire13.setChampsFilsGauche(-1);
//			unStagiaire13.setChampsFilsDroit(-1);
//			unStagiaire13.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire13, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 13);
//			/////////////////////////////////////////
//			Stagiaire unStagiaire14 = new Stagiaire("BENAMARA", "Sophien", "13",
//					"WEB5", "2015");
//			unStagiaire14.setChampsPere(-1);
//			unStagiaire14.setChampsFilsGauche(-1);
//			unStagiaire14.setChampsFilsDroit(-1);
//			unStagiaire14.setChampsFilsCache(-1);
//			
//			caab.formaterEnRubrique(unStagiaire14, raf);
//			caab.rechercherPereDuStagiaire(0, raf, 14);
//			/////////////////////////////////////////
//
//
//			System.out.println(caab.lireUnStagiaire(raf, 0));
//			System.out.println(caab.lireUnStagiaire(raf, 1));
//			System.out.println(caab.lireUnStagiaire(raf, 2));
//			System.out.println(caab.lireUnStagiaire(raf, 3));
//			System.out.println(caab.lireUnStagiaire(raf, 4));
//			System.out.println(caab.lireUnStagiaire(raf, 5));
//			System.out.println(caab.lireUnStagiaire(raf, 6));
//			System.out.println(caab.lireUnStagiaire(raf, 7));
//			System.out.println(caab.lireUnStagiaire(raf, 8));
//			System.out.println(caab.lireUnStagiaire(raf, 9));
//			System.out.println(caab.lireUnStagiaire(raf, 10));
//			System.out.println(caab.lireUnStagiaire(raf, 11));
//			System.out.println(caab.lireUnStagiaire(raf, 12));
//			System.out.println(caab.lireUnStagiaire(raf, 13));
//			System.out.println(caab.lireUnStagiaire(raf, 14));
//			

		} catch (Exception e) {
			System.err.println("Pas d'initialisation");
		}

	}

}
