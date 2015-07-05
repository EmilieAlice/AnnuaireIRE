package fr.afcepf.ai.ire.annuaire.test;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAnnuaireEnArbreBinaire;

public class CreationAnnuaireTest {
	
	public static void main(String[] args) {
		CreationAnnuaireEnArbreBinaire annuaire = new CreationAnnuaireEnArbreBinaire();
		try {
			annuaire.init();
			System.out.println("Init ok");
			System.out.println("coucou !");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Pas d'initialisation");
		}
	}
	
}
