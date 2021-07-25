package repositoryPersistence;

import java.util.ArrayList;

import domain.Arbre;
import domain.Flor;
import domain.Decoracio;
import domain.Floristeria;
import domain.Producte;



public class repositoryFloristeries {
	
	//constructor
	public repositoryFloristeries () {
		
		floristeries = new ArrayList<> () ;
		
		
	}
	
	private ArrayList <Floristeria> floristeries;
	//private ArrayList <Floristeria> floristeries = new ArrayList <Floristeria> ();
	
	public boolean crearFloristeria(Floristeria novaFloristeria){
		
		 
		
	/*	System.out.print("\nIntroducir nombre de la floristeria: ");		
		String nom = entrada.next();
		
		
		
		Floristeria novaFloristeria = new Floristeria ();
		//rellenamos el objeto que esta en blanco
		novaFloristeria(nom);  */
		System.out.println("S´ha creat la nova floristeria");
		//guardamos "incorporarCliente"(que es el nuevo cliente a añadir) en el arrayList "clientes"
		return floristeries.add(novaFloristeria);
		
		
	}
	
	public boolean afegirArbre(int indexFloristeria, Producte producte){
		
		
		return floristeries.get(indexFloristeria).getArbres().add((Arbre) producte);
		
		/*	Boolean arbreTrobat = false;
		int i = 0;
		int indexArbre = 0;
		String resultat = "";
		
		
		 while(i < Floristeria.arbres.size() && arbreTrobat == false) {
			 if (nom.equals(Floristeria.arbres.get(i).getNom())) {
				arbreTrobat = true;
			 	indexArbre = i;
			 } else {	 
				 i++;
			 }
			 if (!arbreTrobat){
			 	 resultat = ("L'arbre no está donat d'alta en la aplicació.");
			 }
			 
			 
			 if (arbreTrobat) {
				 System.out.print("L'arbre introduit ja existeix.  ");
			 } else {
			 
				 System.out.print("Arbre introduit correctament.");
			 } 
		 }	*/	
	}
	
	
	
	public boolean afegirFlor(int indexFloristeria, Producte producte){
		
			return floristeries.get(indexFloristeria).getFlors().add((Flor) producte);	
		
		
		
		
		
		
	/*	Boolean florTrobat = false;
		int i = 0;
		int indexFlor = 0;
		String resultat = "";
		
		
		System.out.print("\nIntroduir nom  de la flor que vols afegir: ");
		String nom = entrada.next();
		
		
		 while(i < flors.size() && florTrobat == false) {
			 if (nom.equals(flors.get(i).getNombre())) {
				florTrobat = true;
			 	indexFlor = i;
			 } else {	 
				 i++;
			 }
			 if (!florTrobat){
			 	 resultat = ("La flor no está donada d'alta en la aplicació.");
			 }
			 
			 
			 if (florTrobat) {
				 System.out.print("La flor introduida ja existeix.  ");
			 } else {
			 
				 System.out.print("Flor introduida correctament.");
			 } 
		 }	*/	
	}
	
	public boolean  afegirDecoracio(int indexFloristeria, Producte producte){
		
		return floristeries.get(indexFloristeria).getDecoracions().add((Decoracio) producte);	
		
	}
		
	/*	Boolean decoracioTrobat = false;
		int i = 0;
		int indexDecoracio = 0;
		String resultat = "";
		
		
		System.out.print("\nIntroduir d'ecoració: ");
		String nom = entrada.next();
		
		
		 while(i < decoracions.size() && decoracioTrobat == false) {
			 if (nom.equals(decoracions.get(i).getNombre())) {
				decoracioTrobat = true;
			 	indexDecoracio = i;
			 } else {	 
				 i++;
			 }
			 if (!decoracioTrobat){
			 	 resultat = ("La decoració no está donada d'alta en la aplicació.");
			 }
			 
			 
			 if (decoracioTrobat) {
				 System.out.print("La decoració introduida ja existeix.  ");
			 } else {
			 
				 System.out.print("Decoració introduida correctament.");
			 } 
		 }		
	}
	
	public static void veureStock(){
		
		Boolean stockTrobat = false;
		int i = 0;
		int j = 0;
		int indexStock = 0;
		String nom = "";
		String resultat = "";
		
		System.out.print("\nStock de flors: ");
		
		 while(i < flors.size() && stockTrobat == false) {
			 if (nom.equals(flors.get(i).getNombre())) {
				stockTrobat = true;
			 	indexStock = i;
			 } else {	 
				 i++;
			 }
			 if (!stockTrobat){
			 	 resultat = ("L'stock de plantes es buit.");
			 }
		 }	
		
	    for(j = 0; j < flors.size(); j ++){
            Flor florView = flors.get(j); 
            System.out.println("L'stock de flors es " + florView.getNumFlors());
        }
	    
		System.out.print("\nStock d'arbres: ");
		
		 while(i < arbres.size() && stockTrobat == false) {
			 if (nom.equals(arbres.get(i).getNombre())) {
				stockTrobat = true;
			 	indexStock = i;
			 } else {	 
				 i++;
			 }
			 if (!stockTrobat){
			 	 resultat = ("L'stock de plantes es buit.");
			 }
		 }	
		
	   for(j = 0; j < arbres.size(); j ++){
	       Arbre arbreView = arbres.get(j); 
	       System.out.println("L'stock d'arbres es " + arbreView.getNumArbres());
	   }   */
	  
public int midaFloristeries () {
	
	return floristeries.size();
	
}

public Floristeria llegirFloristeria (int indexFloristeria) {
	
	return floristeries.get(indexFloristeria);
}

}


