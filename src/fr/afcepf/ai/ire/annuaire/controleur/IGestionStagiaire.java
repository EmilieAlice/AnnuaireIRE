package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.RandomAccessFile;
import java.util.List;

import fr.afcepf.ai.ire.modele.Stagiaire;

public interface IGestionStagiaire {
	void ajouter(Stagiaire stagiaire, String chemainDuRaf, CreationAjoutArbreBinaire caab);
	void supprimer(String nom, int numeroLigne);
	void miseAJour(Stagiaire stagiaire);
	List<Stagiaire> rechercherParNom(String nom, String chemainRaf, int numLigne) throws Exception;
	List<Stagiaire> rechercherParPromo(String promo, List<Stagiaire> listeARecuperer);
	List<Stagiaire> rechercherParDepartement(String departement, List<Stagiaire> listeARecuperer);
}
