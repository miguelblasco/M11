package domain;

public class Flor extends Producte {
	
	private String color;
	
	public Flor (String nom, String color, float preu )	{
		super(nom, preu);
		this.color = color;	
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	

}