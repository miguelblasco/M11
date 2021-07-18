package utilities;

import javax.swing.JOptionPane;

public class Input {
	

	public static String tornarFloristeia(String msg) {
		String nomFloristeria;
		
		try {
			
				nomFloristeria = JOptionPane.showInputDialog(msg);
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
			nomFloristeria = "NulL";
		}
		
		return nomFloristeria; 
	}
	
	
	public static String tornarPreu(String msg) {
		String quantitat;
		
		try {
			
			quantitat = JOptionPane.showInputDialog(msg);
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
			quantitat = "NulL";
		}
		
		
		return quantitat; 
	}
	

}
