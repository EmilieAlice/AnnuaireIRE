package fr.afcepf.ai.ire.modele;

public class Stagiaire implements Comparable<Stagiaire> {

	private String nom;
	private String prenom;
	private String departement;
	private String promo;
	private String annee;
	private String champPere;
	private String champFilsGauche;
	private String champFilsDroit;
	private String champFilsCache;

	@Override
	public int compareTo(Stagiaire stagiaireAComparer) {
		int compAuteur = this.getNom().compareTo(stagiaireAComparer.getNom());
		if (compAuteur != 0) {
			return compAuteur;
		} else {
			compAuteur = 0;
		}
		return compAuteur;
	}

	public String donneLeStagiaire() {
		return this.nom + this.prenom + this.departement + this.promo + this.annee + this.champPere
				+ this.champFilsGauche + this.champFilsDroit + this.champFilsCache;
	}

	public Stagiaire() {
		super();
	}

	public Stagiaire(String nom, String prenom, String departement,
			String promo, String annee, String champPere,
			String champFilsGauche, String champFilsDroit, String champFilsCache) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.promo = promo;
		this.annee = annee;
		this.champPere = champPere;
		this.champFilsGauche = champFilsGauche;
		this.champFilsDroit = champFilsDroit;
		this.champFilsCache = champFilsCache;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getChampPere() {
		return champPere;
	}

	public void setChampPere(String champPere) {
		this.champPere = champPere;
	}

	public String getChampFilsGauche() {
		return champFilsGauche;
	}

	public void setChampFilsGauche(String champFilsGauche) {
		this.champFilsGauche = champFilsGauche;
	}

	public String getChampFilsDroit() {
		return champFilsDroit;
	}

	public void setChampFilsDroit(String champFilsDroit) {
		this.champFilsDroit = champFilsDroit;
	}

	public String getChampFilsCache() {
		return champFilsCache;
	}

	public void setChampFilsCache(String champFilsCache) {
		this.champFilsCache = champFilsCache;
	}

}
