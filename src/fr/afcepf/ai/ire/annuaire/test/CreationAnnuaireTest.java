package fr.afcepf.ai.ire.annuaire.test;

import java.io.RandomAccessFile;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.annuaire.controleur.GestionStagiaire;
import fr.afcepf.ai.ire.modele.Stagiaire;

public class CreationAnnuaireTest {

	public static void main(String[] args) {

		CreationAjoutArbreBinaire caab = new CreationAjoutArbreBinaire();
		GestionStagiaire gestStag = new GestionStagiaire();
		try {

			// caab.init("C:/Users/Stagiaire/Desktop/STAGIAIRES.DON",
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin");

			// for (Stagiaire stagiaire : caab.lireAnnuaire(0,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin")) {
			// System.out.println(stagiaire);
			// }

			RandomAccessFile fichierAStructurer = new RandomAccessFile(
					"C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin",
					"rwd");
			
//			Stagiaire unStagiaire0 = new Stagiaire("Marteaux",
//					"keke", "95", "AI 4d", "2012");
//			unStagiaire0.setChampsPere(-1);
//			unStagiaire0.setChampsFilsGauche(-1);
//			unStagiaire0.setChampsFilsDroit(-1);
//			unStagiaire0.setChampsFilsCache(-1);
//			gestStag.ajouter(unStagiaire0,
//					"C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin",
//					caab);

//			 gestStag.rechercherParNom("Marteau",
//			 "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin", 0);
			// gestStag.rechercherParDepartement("97", caab.lireAnnuaire(0,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest.bin"));

			fichierAStructurer.seek(0);
			int ligneRacine = fichierAStructurer.readInt();

			for (Stagiaire stagiaire : gestStag.rechercherParNom("m",
			 "C:/Users/Stagiaire/Desktop/fichierStagiairesTest0.bin", 0)) {
				System.out.println(stagiaire);
			}

			//
			// System.out.println("Debut suppression-----------------------------------------");
			//
			//
			// gestStag.supprimerDansArbre(unStagiaire0, ligneRacine,
			// fichierAStructurer, 0, 0, 0);
			//
			// for (Stagiaire stagiaire : caab.lireAnnuaire(ligneRacine,
			// "C:/Users/Stagiaire/Desktop/fichierStagiairesTest5.bin")) {
			// System.out.println(stagiaire);
			// }
			//
			// System.out.println("ligne racine = "+ ligneRacine);
			//
			fichierAStructurer.close();

		} catch (Exception e) {
			System.err.println("Pas d'initialisation");
		}

	}
}
