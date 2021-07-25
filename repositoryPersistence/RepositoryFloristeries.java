package repositoryPersistence;

import java.util.ArrayList;

import domain.Arbre;
import domain.Flor;
import domain.Decoracio;
import domain.Floristeria;
import domain.Producte;

public class RepositoryFloristeries {
	
	private static RepositoryFloristeries instance = null;
	
	// Aplicación del patrón "singleton". Evidentemente, esto sólo sería relevante
	// en el caso de tener múltiples usuarios accediendo a la vez.
	public static RepositoryFloristeries getInstance() {
		if(instance == null)
			instance = new RepositoryFloristeries();
		return instance;
	}
	
	// Constructor
	private RepositoryFloristeries () {
		
		floristeries = new ArrayList<Floristeria>();
		
	}
	
	private ArrayList<Floristeria> floristeries;
	
	public boolean crearFloristeria(Floristeria novaFloristeria) {
		
		return floristeries.add(novaFloristeria);
		
	}
	
	public boolean afegirArbre(int indexFloristeria, Producte producte) {
		
		return floristeries.get(indexFloristeria).getArbres().add((Arbre) producte);
		
	}
	
	public boolean afegirFlor(int indexFloristeria, Producte producte) {
		
			return floristeries.get(indexFloristeria).getFlors().add((Flor) producte);
			
	}
	
	public boolean afegirDecoracio(int indexFloristeria, Producte producte) {
		
		return floristeries.get(indexFloristeria).getDecoracions().add((Decoracio) producte);	
		
	}
	
	public int midaFloristeries() {
		
		return floristeries.size();
		
	}

	public Floristeria llegirFloristeria(int indexFloristeria) {
		
		return floristeries.get(indexFloristeria);
		
	}

}
