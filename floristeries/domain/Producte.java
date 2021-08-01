package floristeries.domain;

public class Producte {
	
	private String nom;
	private float preu;
	private int quantitat;
	
	public Producte(String nom, float preu, int quantitat) throws Exception {
		if(nom == null || nom.isBlank()) throw new Exception("Nom del producte buit.");
		if(preu < 0.0f) throw new Exception("Preu \'" + preu + "\' negatiu.");
		if(quantitat < 0) throw new Exception("Quantitat \'" + quantitat + "\' negativa.");
		this.nom = nom;
		this.preu = preu;
		this.quantitat = quantitat;
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
	
	public int getQuantitat() {
		return quantitat;
	}
	
	public String toString(String info) {
		return String.format("%s \"%s\" .... %d x %.2f €", nom, info, quantitat, preu);
	}

}
