package floristeries.repositoryPersistence;

import java.util.ArrayList;

import floristeries.domain.Arbre;
import floristeries.domain.Decoracio;
import floristeries.domain.Flor;
import floristeries.domain.Floristeria;

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
	
	public boolean afegirArbre(int indexFloristeria, Arbre arbre) {
		
		return floristeries.get(indexFloristeria).getArbres().add(arbre);
		
	}
	
	public boolean afegirFlor(int indexFloristeria, Flor flor) {
		
		return floristeries.get(indexFloristeria).getFlors().add(flor);
			
	}
	
	public boolean afegirDecoracio(int indexFloristeria, Decoracio decoracio) {
		
		return floristeries.get(indexFloristeria).getDecoracions().add(decoracio);	
		
	}
	
	public int midaFloristeries() {
		
		return floristeries.size();
		
	}

	public Floristeria llegirFloristeria(int indexFloristeria) {
		
		return floristeries.get(indexFloristeria);
		
	}

}
