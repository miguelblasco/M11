package domain;

import java.util.ArrayList;

public class Floristeria {
	
	
	public Floristeria  (String nom)	{
		this.nom = nom;
		
	}
		String nom;
		private  ArrayList <Arbre> arbres = new ArrayList <Arbre>();
		private ArrayList <Flor> flors = new ArrayList <Flor>();
		private ArrayList <Decoracio> decoracions = new ArrayList <Decoracio>();
		
		
		public String getNom() {
			return nom;
		}
		public ArrayList<Arbre> getArbres() {
			return arbres;
		}
		public ArrayList<Flor> getFlors() {
			return flors;
		}
		public ArrayList<Decoracio> getDecoracions() {
			return decoracions;
		}
		
		
		
	
}


