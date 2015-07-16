package fr.afcepf.ai.ire.modele;

import java.io.RandomAccessFile;
import java.util.List;

public interface IGestionStagiaire {
	/**
	 * Methode qui permet d'ajouter un stagiaire à l'annuaire
	 * 
	 * @param stagiaire
	 * @param chemainDuRaf
	 * @param caab
	 */
	void ajouter(Stagiaire stagiaire, String chemainDuRaf,
			CreationAjoutArbreBinaire caab);

	/**
	 * 
	 * Methode qui permet la suppression d'un stagiaire dans l'annuaire
	 * 
	 * @param unStagiaire
	 * @param indexPere
	 * @param fichierAStructurer
	 * @param numeroDeLigneStagiaire
	 * @param positionChamps
	 * @param fils
	 */
	void supprimerDansArbre(Stagiaire unStagiaire, int indexPere,
			RandomAccessFile fichierAStructurer, int numeroDeLigneStagiaire,
			int positionChamps, int fils);

	/**
	 * Methode qui permet de mettre à jour un stagiaire dans l'annuaire
	 * 
	 * @param fichierAStructurer
	 * @param unStagiaire
	 * @param unStagiairePourModifier
	 * @param indexPere
	 * @param numeroDeLigneStagiaire
	 * @param positionChamps
	 * @param fils
	 */
	void miseAJour(RandomAccessFile fichierAStructurer, Stagiaire unStagiaire,
			Stagiaire unStagiairePourModifier, int indexPere,
			int numeroDeLigneStagiaire, int positionChamps, int fils);

	/**
	 * Methode qui permet une recherche par nom d'un stagiaire
	 * 
	 * @param nom
	 * @param chemainRaf
	 * @param numLigne
	 * @param listeAAfficher
	 * @return
	 * @throws Exception
	 */
	List<Stagiaire> rechercherParNom(String nom, String chemainRaf,
			int numLigne, List<Stagiaire> listeAAfficher) throws Exception;

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
			List<Stagiaire> listeARecuperer, String chemainRaf,
			List<Stagiaire> listeAAfficher) throws Exception;
}
