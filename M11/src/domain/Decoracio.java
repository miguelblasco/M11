package domain;

public class Decoracio extends  Producte {
	
	public enum Material {FUSTA, PLASTIC};
	
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
	
	@Override
	public String toString() {
		return toString(material.name());
	}

}