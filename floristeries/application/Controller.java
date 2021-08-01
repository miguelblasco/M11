package floristeries.application;

import java.util.StringJoiner;

import floristeries.domain.Arbre;
import floristeries.domain.Decoracio;
import floristeries.domain.Flor;
import floristeries.domain.Floristeria;
import floristeries.domain.Producte;
import floristeries.repositoryPersistence.RepositoryFloristeries;
import floristeries.viewAPI.Main;

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
			return repository.afegirArbre(indexFloristeria, (Arbre) producte);
		else if (producte instanceof Flor)
			return repository.afegirFlor(indexFloristeria, (Flor) producte);
		else if (producte instanceof Decoracio)
			return repository.afegirDecoracio(indexFloristeria, (Decoracio) producte);
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
	
	
	public Producte retirarProducte (int indexFloristeria, Class<? extends Producte> classProducte, int indexProducte) {try {
		
		
		switch (classProducte.getSimpleName()) {
		
			case "Arbre":
				return repository.eliminarArbre(indexFloristeria, indexProducte);
			case "Flor":
				return repository.eliminarFlor(indexFloristeria, indexProducte);
			case "Decoracio":
				return repository.eliminarDecoracio(indexFloristeria, indexProducte);
				
			default: return null;
		}
	}catch(Exception e) { Main.error(e); return null; } }
	
	public String stockProductes (int indexFloristeria, Class<? extends Producte> classProducte) {try {
		
		Floristeria floristeriaActual = repository.llegirFloristeria(indexFloristeria);
		StringJoiner inventari = new StringJoiner("\n");
		
		switch (classProducte.getSimpleName()) {
		
			case "Arbre":
				floristeriaActual.getArbres().forEach(arbre -> inventari.add(arbre.toString()));
				
				if (floristeriaActual.getArbres().isEmpty())
					inventari.add("Stock d'arbres buit.");
			break;
			
			case "Flor":
				floristeriaActual.getFlors().forEach(flor -> inventari.add(flor.toString()));
				
				if (floristeriaActual.getFlors().isEmpty())
					inventari.add("Stock de flors buit.");
			break;
			
			case "Decoracio":
				floristeriaActual.getDecoracions().forEach(decoracio -> inventari.add(decoracio.toString()));
				
				if (floristeriaActual.getDecoracions().isEmpty())
					inventari.add("Stock de decoracions buit.");
			break;
			
			default: return null;
		}
		return inventari.toString();
	}catch(Exception e) { Main.error(e); return null; } }
			
	public String valorStock (int indexFloristeria) {try {
		
		Floristeria floristeriaActual = repository.llegirFloristeria(indexFloristeria);
		
		float totalArbres = 0.0f;
		float totalFlors = 0.0f;
		float totalDecoracions = 0.0f;
		float total = 0.0f;
		StringJoiner totalStock = new StringJoiner("\n"); 
		
		for (Arbre arbre : floristeriaActual.getArbres()) {
			totalArbres += arbre.getPreu() * arbre.getQuantitat();
		}
		for (Flor flor : floristeriaActual.getFlors()) {
			totalFlors += flor.getPreu() * flor.getQuantitat();
		}
		for (Decoracio decoracio : floristeriaActual.getDecoracions()) {
			totalDecoracions += decoracio.getPreu() * decoracio.getQuantitat();
		}
		
		total = totalArbres + totalFlors + totalDecoracions;
		
		totalStock.add(String.format("Valor total Arbres: %.2f €", totalArbres));
		totalStock.add(String.format("Valor total Flors: %.2f €", totalFlors));
		totalStock.add(String.format("Valor total Decoracions: %.2f €", totalDecoracions));
		totalStock.add("");
		totalStock.add(String.format("Valor total Stock: %.2f €", total));
		
		return totalStock.toString();
	}catch(Exception e) { Main.error(e); return null; } }
	
	
	
}
