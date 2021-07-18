package viewAPI;

import java.util.Scanner;



public class principal {
	
	static Scanner entrada = new Scanner (System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int opcio = 0;
		
		do {
			System.out.print ("1.Crear Floristeria \n2.Afegir Arbre"
					+ " \n3.Afegir Flor " + "\n4.Afegir Decoració"
					+ " \n5.Veure Stock " 
					+ "\n0.Sortir de la aplicació \n\n Esculli opció: ");
			
			opcio = entrada.nextInt();
			
			switch (opcio) {
			
				case 1: 
					crearFloristeria();
					System.out.println(floristerias);
				break;
					
				case 2: 
					afegirArbre();
				break;
					
				case 3: 
					afegirFlor();
				break;
				
				case 4: 
					afegirDecoracio();
				break;
				
				case 5: 
					veureStock();
				break;
				
				case 0: 
					System.out.println("ha abandonat l'aplicació");
				break;
			
			} 
		}while (opcio !=0);
			
			
			entrada.close();
			
			
			
		}
		
		
		
		

	}


