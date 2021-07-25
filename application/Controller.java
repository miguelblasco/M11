package application;

import java.util.StringJoiner;

import domain.Arbre;
import domain.Decoracio;
import domain.Flor;
import domain.Floristeria;
import domain.Producte;
import repositoryPersistence.RepositoryFloristeries;
import viewAPI.Main;

public class Controller {
	
	// Constructor
	public Controller () {
		repository = RepositoryFloristeries.getInstance();
	}
	
	
	private RepositoryFloristeries repository;
	
	
	public boolean crearFloristeria(Floristeria floristeria) { try {
		
		return repository.crearFloristeria(floristeria);
		
	} catch(Exception e) { Main.error(e); return false; } }
	
	
	public boolean afegirProducte (int indexFloristeria, Producte producte) { try {
		
		if (producte instanceof Arbre)
			return repository.afegirArbre(indexFloristeria, producte);
		else if (producte instanceof Flor)
			return repository.afegirFlor(indexFloristeria, producte);
		else if (producte instanceof Decoracio)
			return repository.afegirDecoracio(indexFloristeria, producte);
		else return false;
		
	} catch(Exception e) { Main.error(e); return false; } }
	
	
	public String[] nomsFloristeries() { try {
		
		int limit = repository.midaFloristeries();
		String[] catalegFloristeries = new String[limit];
		for (int i = 0; i < limit; i++) {
			catalegFloristeries[i] = repository.llegirFloristeria(i).getNom() ;
		}
		
		return catalegFloristeries;
		
	} catch(Exception e) { Main.error(e); return null; } }
	
	
	public String stock(int indexFloristeria) { try {
		Floristeria floristeriaActual = repository.llegirFloristeria(indexFloristeria);
		StringJoiner inventari = new StringJoiner("\n");
		
		if (!floristeriaActual.getArbres().isEmpty()) {
			inventari.add("ARBRES:");
			floristeriaActual.getArbres().forEach(arbre -> inventari.add(arbre.toString()));
			inventari.add("");
		}
		if (!floristeriaActual.getFlors().isEmpty()) {
			inventari.add("FLORS:");
			floristeriaActual.getFlors().forEach(flor -> inventari.add(flor.toString()));
			inventari.add("");
		}
		if (!floristeriaActual.getDecoracions().isEmpty()) {	
			inventari.add("DECORACIONS:");
			floristeriaActual.getDecoracions().forEach(decoracio -> inventari.add(decoracio.toString()));
		}
		
		return inventari.toString();
		
	} catch(Exception e) { Main.error(e); return null; } }
	
}
