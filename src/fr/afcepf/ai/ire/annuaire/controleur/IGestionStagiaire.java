package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.RandomAccessFile;
import java.util.List;

import fr.afcepf.ai.ire.modele.Stagiaire;

public interface IGestionStagiaire {
	void ajouter(Stagiaire stagiaire, String chemainDuRaf,
			CreationAjoutArbreBinaire caab);

	void supprimerDansArbre(Stagiaire unStagiaire, int indexPere,
			RandomAccessFile fichierAStructurer, int numeroDeLigneStagiaire,
			int positionChamps, int fils);

	void miseAJour(RandomAccessFile fichierAStructurer,
			Stagiaire unStagiaire, Stagiaire unStagiairePourModifier,
			int indexPere, int numeroDeLigneStagiaire, int positionChamps,
			int fils);

	List<Stagiaire> rechercherParNom(String nom, String chemainRaf, int numLigne, List<Stagiaire> listeAAfficher)
			throws Exception;

	List<Stagiaire> rechercherParPrenom(String prenom,
			List<Stagiaire> listeARecuperer);

	List<Stagiaire> rechercherParDepartement(String departement,
			List<Stagiaire> listeARecuperer);

	List<Stagiaire> rechercherParPromo(String promo,
			List<Stagiaire> listeARecuperer);

	List<Stagiaire> rechercherParAnnee(String annee,
			List<Stagiaire> listeARecuperer);

	List<Stagiaire> rechercherEnMulticritere(String nom, String prenom,
			String departement, String promo, String annee,
			List<Stagiaire> listeARecuperer, String chemainRaf, List<Stagiaire> listeAAfficher)
			throws Exception;
}
