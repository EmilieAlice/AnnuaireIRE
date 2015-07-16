package fr.afcepf.ai.ire.annuaire.test;

import java.io.RandomAccessFile;

import fr.afcepf.ai.ire.modele.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.GestionStagiaire;
import fr.afcepf.ai.ire.modele.Stagiaire;

public class CreationAnnuaireTest {

	public static void main(String[] args) {

		CreationAjoutArbreBinaire caab = new CreationAjoutArbreBinaire();
		GestionStagiaire gestStag = new GestionStagiaire();
		try {

//			 caab.init("C:/Users/Stagiaire/Desktop/STAGIAIRES.DON",
//			 "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin");

			// for (Stagiaire stagiaire : caab.lireAnnuaire(0,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin")) {
			// System.out.println(stagiaire);
			// }

			RandomAccessFile fichierAStructurer = new RandomAccessFile(
					"C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin",
					"rw");

			Stagiaire unStagiaire0 = new Stagiaire("MArteau", "Rémy", "95",
					"AI 81", "2011");
			// Stagiaire unStagiaire1 = new Stagiaire("SLIMANI", "Mohamed",
			// "95",
			//	"AI 78", "2010");
			// unStagiaire0.setChampsPere(-1);
			// unStagiaire0.setChampsFilsGauche(-1);
			// unStagiaire0.setChampsFilsDroit(-1);
			// unStagiaire0.setChampsFilsCache(-1);
			// gestStag.ajouter(unStagiaire0,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin",
			// caab);

			// for (Stagiaire stagiaire : gestStag
			// .rechercherEnMulticritere(
			// "",
			// "mo",
			// "",
			// "",
			// "",
			// caab.lireAnnuaire(0,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin"),
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin")) {
			// System.out.println(stagiaire);
			// }

			// if (unStagiaire1.getNom().equals("")) {
			// gestStag.miseAJour(fichierAStructurer, unStagiaire0,
			// unStagiaire1, 0,0,0,0);
			// }
			// else {
			// unStagiaire1 =
			// GestionStagiaire.remplaceChampsStagiaire(unStagiaire0,
			// unStagiaire1);
			// System.out.println(unStagiaire1);
			// unStagiaire1.setChampsPere(-1);
			// unStagiaire1.setChampsFilsGauche(-1);
			// unStagiaire1.setChampsFilsDroit(-1);
			// unStagiaire1.setChampsFilsCache(-1);
			// gestStag.supprimerDansArbre(unStagiaire0, 0, fichierAStructurer,
			// 0, 0, 0);
			// gestStag.ajouter(unStagiaire1,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin", caab);
			// }
			//
			//

			fichierAStructurer.seek(0);
			int ligneRacine = fichierAStructurer.readInt();
			System.out.println("ligne racine = " + ligneRacine);

			gestStag.supprimerDansArbre(unStagiaire0, ligneRacine,
					fichierAStructurer, 0, 0, 0);
			
//			fichierAStructurer.seek(0);
//			ligneRacine = fichierAStructurer.readInt();
//			System.out.println("ligne racine = " + ligneRacine);
			
			for (Stagiaire stagiaire : caab.lireAnnuaire(ligneRacine,
					"C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin")) {
				System.out.println(stagiaire);
			}

			fichierAStructurer.close();

		} catch (Exception e) {
			System.err.println("Pas d'initialisation");
		}

	}
}
