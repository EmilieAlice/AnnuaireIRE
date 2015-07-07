package fr.afcepf.ai.ire.annuaire.test;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.annuaire.controleur.CreationAnnuaire;
import fr.afcepf.ai.ire.annuaire.controleur.CreationAnnuaireEnArbreBinaire;

public class CreationAnnuaireTest {
	
	public static void main(String[] args) {
		//CreationAnnuaireEnArbreBinaire annuaire = new CreationAnnuaireEnArbreBinaire();
		//CreationAnnuaire annuaire = new CreationAnnuaire();
		CreationAjoutArbreBinaire annuaire = new CreationAjoutArbreBinaire();
		try {
			annuaire.init();
			System.out.println("Init ok");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Pas d'initialisation");
		}
	}
	
}
