package fr.afcepf.ai.ire.annuaire.vue;

import java.util.List;

import fr.afcepf.ai.ire.modele.Stagiaire;

public interface IGestionStagiaire {
	void ajouter(Stagiaire stagiaire);
	void supprimer(String nom, int numeroLigne);
	void miseAJour(Stagiaire stagiaire);
	List<Stagiaire> rechercherParNom(String nom);
	List<Stagiaire> rechercherParPromo(String promo);
	List<Stagiaire> rechercherParDepartement(String departement);
}
