package domain;

public class Producte {
	
	private String nom;
	private float preu;
	
	public Producte(String nom, float preu) throws Exception {
		if(nom == null || nom.isBlank()) throw new Exception("Nom del producte buit.");
		if(preu < 0.0f) throw new Exception("Preu \'" + preu + "\' negatiu.");
		this.nom = nom;
		this.preu = preu;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public double getPreu() {
		return preu;
	}
	
	public void setPreu(float preu) {
		this.preu = preu;
	}
	
	public String toString(String info) {
		return String.format("%s \"%s\" .... %.2f €", nom, info, preu);
	}
	
}
