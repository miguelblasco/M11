package domain;

public class Arbre extends Producte {
	
	private int alcada;
	
	public Arbre(String nom, int alcada, float preu) throws Exception {
		super(nom, preu);
		if(alcada <= 0) throw new Exception("Alçada \'" + alcada + "\' negativa.");
		this.alcada = alcada;
	}
	
	public int getAlcada() {
		return alcada;
	}
	
	public void setAlcada(int alcada) {
		this.alcada = alcada;
	}
	
	@Override
	public String toString() {
		return toString(alcada + " cm");
	}
	
}
