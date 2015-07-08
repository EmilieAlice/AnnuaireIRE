package fr.afcepf.ai.ire.annuaire.controleur;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import fr.afcepf.ai.ire.modele.Stagiaire;

public class GestionStagiaire implements IGestionStagiaire {
	private List<Stagiaire> listeAAfficher = new ArrayList<>();
	@Override
	public void ajouter(Stagiaire stagiaire, String chemainDuRaf,
			CreationAjoutArbreBinaire caab, int nbLigneFichier) {

		try {
			RandomAccessFile raf = new RandomAccessFile(chemainDuRaf, "rwd");
			caab.formaterEnRubrique(stagiaire, raf);
			caab.rechercherPereDuStagiaire(0, raf,
					nbLigneFichier);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void supprimer(String nom, int numeroLigne) {

	}

	@Override
	public void miseAJour(Stagiaire stagiaire) {

	}

	@Override
	public List<Stagiaire> rechercherParNom(String nomStagiaireRecherche,
			String chemainRaf, int numLigne) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(chemainRaf, "r");
		Stagiaire stagiairePere = CreationAjoutArbreBinaire.lireUnStagiaire(
				raf, numLigne);
		if (nomStagiaireRecherche.trim().compareToIgnoreCase(stagiairePere.getNom().trim()) < 0) {
			if (stagiairePere.getChampsFilsGauche() != -1) {
				numLigne = stagiairePere.getChampsFilsGauche();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
			else{
				JOptionPane.showMessageDialog(null, "Le nom recherché n'existe pas");
			}
		} else if (nomStagiaireRecherche.trim().compareToIgnoreCase(stagiairePere.getNom().trim()) > 0) {
			if (stagiairePere.getChampsFilsDroit() != -1) {
				numLigne = stagiairePere.getChampsFilsDroit();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
			else{
				JOptionPane.showMessageDialog(null, "Le nom recherché n'existe pas");
			}
		} else {
			if (stagiairePere.getChampsFilCache() != -1) {
				numLigne = stagiairePere.getChampsFilCache();
				rechercherParNom(nomStagiaireRecherche, chemainRaf, numLigne);
			}
			listeAAfficher.add(stagiairePere);
		}
		raf.close();
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParPromo(String promoStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getPromo().toLowerCase().trim()
						.contains(promoStagiaireRecherche.toLowerCase().trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(),
							stagiaire.getPrenom(), stagiaire.getDepartement(),
							stagiaire.getPromo(), stagiaire.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

	@Override
	public List<Stagiaire> rechercherParDepartement(
			String departementStagiaireRecherche,
			List<Stagiaire> listeARecuperer) {
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire
						.getDepartement()
						.toLowerCase()
						.trim()
						.contains(
								departementStagiaireRecherche.toLowerCase()
										.trim())) {
					listeAAfficher.add(new Stagiaire(stagiaire.getNom(),
							stagiaire.getPrenom(), stagiaire.getDepartement(),
							stagiaire.getPromo(), stagiaire.getAnnee()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listeAAfficher;
	}

}
