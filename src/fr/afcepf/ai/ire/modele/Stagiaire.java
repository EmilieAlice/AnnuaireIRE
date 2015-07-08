package fr.afcepf.ai.ire.modele;

public class Stagiaire implements Comparable<Stagiaire> {

	private String nom;
	private String prenom;
	private String departement;
	private String promo;
	private String annee;
	private int champsPere;
	private int champsFilsGauche;
	private int champsFilsDroit;
	private int champsFilsCache;

	// int crit�reDeTri = 0;

	public Stagiaire() {
	}

	public Stagiaire(String nom, String prenom, String departement,
			String promo, String annee) {
		super();
		this.nom = nom.toUpperCase();
		this.prenom = prenom;
		this.departement = departement;
		this.promo = promo;
		this.annee = annee;
	}

	@Override
	public int compareTo(Stagiaire unStagiaire) {
		int nb = this.getNom().compareTo(unStagiaire.getNom());
		return nb;
	}


	
	@Override
	public String toString() {
		return "Stagiaire [nom=" + nom + ", prenom=" + prenom
				+ ", departement=" + departement + ", promo=" + promo
				+ ", annee=" + annee + ", champsPere=" + champsPere
				+ ", champsFilsGauche=" + champsFilsGauche
				+ ", champsFilsDroit=" + champsFilsDroit + ", champsFilsCache="
				+ champsFilsCache + "]";
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

	public int getChampsPere() {
		return champsPere;
	}

	public void setChampsPere(int champsPere) {
		this.champsPere = champsPere;
	}

	public int getChampsFilsGauche() {
		return champsFilsGauche;
	}

	public void setChampsFilsGauche(int champsFilsGauche) {
		this.champsFilsGauche = champsFilsGauche;
	}

	public int getChampsFilsDroit() {
		return champsFilsDroit;
	}

	public void setChampsFilsDroit(int champsFilsDroit) {
		this.champsFilsDroit = champsFilsDroit;
	}

	public int getChampsFilCache() {
		return champsFilsCache;
	}

	public void setChampsFilsCache(int champsFilsCache) {
		this.champsFilsCache = champsFilsCache;
	}

}
