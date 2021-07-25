package application;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringJoiner;

import domain.Arbre;
import domain.Decoracio;
import domain.Flor;
import domain.Floristeria;
import domain.Producte;
import repositoryPersistence.repositoryFloristeries;
import viewAPI.Main;

public class Controller {
	
	
	//constructor
	public Controller () {
		
		repository = new repositoryFloristeries ();
	}
	
	private repositoryFloristeries repository;

	
/*	public static void crearFloristeria() {
		
		
		
		System.out.print("\nIntroduir nom de la floristeria: ");		
		String nom = entrada.next();
		
		Floristeria novaFloristeria = new Floristeria (); // SE PONE AQUÌ O ARRIBA DEL TODO????????
		
		
		//rellenamos el objeto que esta en blanco
		novaFloristeria(nom); // POR QUE HACE FALTA?????????. EN EL EJERCICIO DEL HOTEL NO SE HACIA ESTE PASO
		
		//guardamos "incorporarCliente"(que es el nuevo cliente a añadir) en el arrayList "clientes"
		floristeries.add(novaFloristeria);
		
		System.out.println("S`ha creat una nova floristeria");
	} */
	
	public boolean crearFloristeria (Floristeria floristeria) {try {
		return repository.crearFloristeria(floristeria);
		
	}catch(Exception e) {Main.error(e);return false;}}
	
	public boolean afegirProducte (int indexFloristeria ,Producte producte) {try {
		
		if (producte instanceof Arbre )
			return repository.afegirArbre(indexFloristeria, producte);
			
		else if (producte instanceof Flor )
			return repository.afegirFlor(indexFloristeria, producte);
		
		else if (producte instanceof Decoracio )
			return repository.afegirDecoracio(indexFloristeria, producte);
		
		else return false;
		
	}catch(Exception e) {Main.error(e);return false;}}
	
	
	public String [] nomsFloristeries () {try {
		
		int limit = repository.midaFloristeries();
		
		String [] catalegFloristeries = new String [limit];
		
		for (int i = 0; i < limit; i++ ) {
			
			catalegFloristeries [i] = repository.llegirFloristeria(i).getNom() ;
		}
		return catalegFloristeries ;
		
	}catch(Exception e) {Main.error(e);return null;}}
	
	public String stock (int indexFloristeria) {try {
		Floristeria floristeriaActual = repository.llegirFloristeria(indexFloristeria);
		StringJoiner inventari = new StringJoiner ("\n");
		
		if (!floristeriaActual.getArbres().isEmpty()) {
			
			inventari.add("Arbres:");
			floristeriaActual.getArbres().forEach(arbre -> inventari.add(arbre.toString()));
		}
		
		if (!floristeriaActual.getFlors().isEmpty()) {
				
				inventari.add("Flors:");
				floristeriaActual.getFlors().forEach(flor -> inventari.add(flor.toString()));
			}
		
		if (!floristeriaActual.getDecoracions().isEmpty()) {
			
			inventari.add("Decoracions:");
			floristeriaActual.getDecoracions().forEach(decoracio -> inventari.add(decoracio.toString()));
		}
		
		return inventari.toString();
	}catch(Exception e) {Main.error(e);return null;}}
	
	

}
