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
			// caab.init();
			// System.out.println("Init ok");

			Stagiaire unStagiaire = new Stagiaire("Idkjd", "Martin", "97",
					"AI95", "2015");
			unStagiaire.setChampsPere(-1);
			unStagiaire.setChampsFilsGauche(-1);
			unStagiaire.setChampsFilsDroit(-1);
			unStagiaire.setChampsFilsCache(-1);

			Stagiaire unStagiaire1 = new Stagiaire("Rsebte", "Emilie", "62",
					"AI95", "2015");
			unStagiaire1.setChampsPere(-1);
			unStagiaire1.setChampsFilsGauche(-1);
			unStagiaire1.setChampsFilsDroit(-1);
			unStagiaire1.setChampsFilsCache(-1);

			Stagiaire unStagiaire2 = new Stagiaire("Sereh", "Idriss", "95",
					"AI95", "2015");
			unStagiaire2.setChampsPere(-1);
			unStagiaire2.setChampsFilsGauche(-1);
			unStagiaire2.setChampsFilsDroit(-1);
			unStagiaire2.setChampsFilsCache(-1);

			Stagiaire unStagiaire3 = new Stagiaire("Mkefh", "Rosane", "13",
					"AI95", "2015");
			unStagiaire3.setChampsPere(-1);
			unStagiaire3.setChampsFilsGauche(-1);
			unStagiaire3.setChampsFilsDroit(-1);
			unStagiaire3.setChampsFilsCache(-1);

			Stagiaire unStagiaire4 = new Stagiaire("Mkefh", "Rosane", "13",
					"AL82", "2015");
			unStagiaire4.setChampsPere(-1);
			unStagiaire4.setChampsFilsGauche(-1);
			unStagiaire4.setChampsFilsDroit(-1);
			unStagiaire4.setChampsFilsCache(-1);
			
			Stagiaire unStagiaire5 = new Stagiaire("Mkefh", "Rosane", "13",
					"WEB5", "2015");
			unStagiaire5.setChampsPere(-1);
			unStagiaire5.setChampsFilsGauche(-1);
			unStagiaire5.setChampsFilsDroit(-1);
			unStagiaire5.setChampsFilsCache(-1);
			
			RandomAccessFile raf = new RandomAccessFile(
					"fichierBinaireTest.bin", "rw");

			caab.formaterEnRubrique(unStagiaire, raf);
			caab.formaterEnRubrique(unStagiaire1, raf);
			caab.formaterEnRubrique(unStagiaire2, raf);
			caab.formaterEnRubrique(unStagiaire3, raf);
			caab.formaterEnRubrique(unStagiaire4, raf);
			caab.formaterEnRubrique(unStagiaire5, raf);



			// System.out.println(caab.LONGUEURLIGNE);

			Stagiaire sb = caab.lireUnStagiaire(raf, 0);
			System.out.println(sb);

			sb = caab.lireUnStagiaire(raf, 1);
			System.out.println(sb);
			
			sb = caab.lireUnStagiaire(raf, 2);
			System.out.println(sb);
			
			sb = caab.lireUnStagiaire(raf, 3);
			System.out.println(sb);
			
			sb = caab.lireUnStagiaire(raf, 4);
			System.out.println(sb);
			
			sb = caab.lireUnStagiaire(raf, 5);
			System.out.println(sb);
			
			caab.rechercherPereDuStagiaire(3, raf, 2);
			caab.rechercherPereDuStagiaire(3, raf, 0);
			caab.rechercherPereDuStagiaire(3, raf, 1);
			caab.rechercherPereDuStagiaire(3, raf, 4);
			caab.rechercherPereDuStagiaire(3, raf, 5);




			System.out.println(caab.lireUnStagiaire(raf, 0));
			System.out.println(caab.lireUnStagiaire(raf, 1));
			System.out.println(caab.lireUnStagiaire(raf, 2));
			System.out.println(caab.lireUnStagiaire(raf, 3));
			System.out.println(caab.lireUnStagiaire(raf, 4));
			System.out.println(caab.lireUnStagiaire(raf, 5));



			

		} catch (Exception e) {
			System.err.println("Pas d'initialisation");
		}

	}

}
