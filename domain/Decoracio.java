package domain;

enum Material {FUSTA, PLASTIC};

public class Decoracio extends Producte {
	
	private Material material;
	
	public Decoracio (String nom, Material material, float preu)	{
		super(nom, preu);
		this.material = material;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}