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

	// int critereDeTri = 0;

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

	public Stagiaire(String nom, String prenom, String departement,
			String promo, String annee, int champsPere, int champsFilsGauche,
			int champsFilsDroit, int champsFilsCache) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.departement = departement;
		this.promo = promo;
		this.annee = annee;
		this.champsPere = champsPere;
		this.champsFilsGauche = champsFilsGauche;
		this.champsFilsDroit = champsFilsDroit;
		this.champsFilsCache = champsFilsCache;
	}

	@Override
	public int compareTo(Stagiaire unStagiaire) {
		int nb = this.getNom().compareTo(unStagiaire.getNom());
		return nb;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((annee == null) ? 0 : annee.hashCode());
		result = prime * result + champsFilsCache;
		result = prime * result + champsFilsDroit;
		result = prime * result + champsFilsGauche;
		result = prime * result + champsPere;
		result = prime * result
				+ ((departement == null) ? 0 : departement.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((promo == null) ? 0 : promo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stagiaire other = (Stagiaire) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (departement == null) {
			if (other.departement != null)
				return false;
		} else if (!departement.equals(other.departement))
			return false;
		if (promo == null) {
			if (other.promo != null)
				return false;
		} else if (!promo.equals(other.promo))
			return false;
		if (annee == null) {
			if (other.annee != null)
				return false;
		} else if (!annee.equals(other.annee))
			return false;
		return true;
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
