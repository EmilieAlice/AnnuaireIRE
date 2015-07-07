package fr.afcepf.ai.ire.annuaire.vue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.afcepf.ai.ire.annuaire.controleur.CreationAjoutArbreBinaire;
import fr.afcepf.ai.ire.modele.Stagiaire;

public class GestionStagiaire implements IGestionStagiaire {

	@Override
	public void ajouter(Stagiaire stagiaire) {

	}

	@Override
	public void supprimer(String nom, int numeroLigne) {

	}

	@Override
	public void miseAJour(Stagiaire stagiaire) {

	}

	@Override
	public List<Stagiaire> rechercherParNom(String nomStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listeAAfficher = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getNom().toLowerCase().trim()
						.contains(nomStagiaireRecherche.toLowerCase().trim())) {
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
	public List<Stagiaire> rechercherParPromo(String promoStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listeAAfficher = new ArrayList<>();
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
	public List<Stagiaire> rechercherParDepartement(String departementStagiaireRecherche, List<Stagiaire> listeARecuperer) {
		List<Stagiaire> listeAAfficher = new ArrayList<>();
		try {
			for (Stagiaire stagiaire : listeARecuperer) {
				if (stagiaire.getDepartement().toLowerCase().trim()
						.contains(departementStagiaireRecherche.toLowerCase().trim())) {
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
