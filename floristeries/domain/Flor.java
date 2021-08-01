package floristeries.domain;

public class Flor extends Producte {
	
	private String color;
	
	public Flor(String nom, String color, float preu, int quantitat ) throws Exception {
		super(nom, preu, quantitat);
		if(color == null || color.isBlank()) throw new Exception("Color sense especificar.");
		this.color = color;	
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return toString(color);
	}
	
}
