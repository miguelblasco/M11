package application;


import java.util.Scanner;

import domain.Floristeria;

public class FloristeriasFactory {
	
	static Scanner entrada = new Scanner (System.in);
	
	public static void crearFloristeria() {
		
		
		
		System.out.print("\nIntroduir nom de la floristeria: ");		
		String nom = entrada.next();
		
		Floristeria novaFloristeria = new Floristeria (); // SE PONE AQUÌ O ARRIBA DEL TODO????????
		
		
		//rellenamos el objeto que esta en blanco
		novaFloristeria(nom); // POR QUE HACE FALTA?????????. EN EL EJERCICIO DEL HOTEL NO SE HACIA ESTE PASO
		
		//guardamos "incorporarCliente"(que es el nuevo cliente a añadir) en el arrayList "clientes"
		floristerias.add(novaFloristeria);
		
		System.out.println("S`ha creat una nova floristeria");
	}
	

}
