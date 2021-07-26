/** Back-end Java, IT Academy
*** M11 - Floristeries (Pair Programming)
*** Miguel Blasco & Roger Torrent */

package floristeries.viewAPI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import floristeries.application.Controller;
import floristeries.domain.Floristeria;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JPanel panell;
	private final JButton crearFloristeria, mostrarStock, sortir;
	final JComboBox<String> llistaFloristeries;
	
	private final ButtonsProducteFactory[] factoriesBotons = {
		new ButtonsArbreFactory(),
		new ButtonsFlorFactory(),
		new ButtonsDecoracioFactory(),
		// Nuevas líneas de productos se añaden aquí
	};
	private ButtonProducte[] botonsAfegir = new ButtonProducte[3];
	
	Controller m11;
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	// Constructor de la ventana principal y menú del programa
	public Main() {
		super("Mòdul 11 -- Floristeries");
		
		panell = (JPanel)getContentPane();
		panell.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		panell.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		crearFloristeria = new JButton("CrearFloristeria");
		llistaFloristeries = new JComboBox<String>();
		llistaFloristeries.setPrototypeDisplayValue("1234567890"); // Fixa la grandària màxima
		mostrarStock = new JButton("Stock");
		mostrarStock.setEnabled(false);
		Box box1 = Box.createHorizontalBox();
		box1.add(crearFloristeria);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(llistaFloristeries);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(mostrarStock);
		panell.add(box1);
		
		// Floristería nueva
		crearFloristeria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				String nom = JOptionPane.showInputDialog(panell, "Nom de la floristeria:",
						"Nova floristeria", JOptionPane.QUESTION_MESSAGE);
				
				try { if(nom != null  && m11.crearFloristeria(new Floristeria(nom))) {
					llistaFloristeries.addItem(nom);
					llistaFloristeries.setSelectedItem(nom);
					mostrarStock.setEnabled(true);
					for(int i = 0; i < botonsAfegir.length; i++)
						botonsAfegir[i].setEnabled(true);
				} } catch (Exception ex) { error(ex); }
			}
			
		});
		
		// Actualiza la lista de floristerías en 'llistaFloristeries'. Esto no resulta necesario en este
		// programa, pues todas las floristerías se añaden «in situ» mediante el botón 'crearFloristeria'
		// y simplemente ocupan espacio local en memoria. Pero sería imprescindible en caso de acceder a
		// la BBDD como un usuario más, tanto al disco duro como a un servidor remoto.
		llistaFloristeries.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				llistaFloristeries.setModel(new DefaultComboBoxModel<String>(m11.nomsFloristeries()));
				llistaFloristeries.showPopup();
			}
			
			@Override
			public void focusLost(FocusEvent e) {}
			
		});
		
		// Mostrar el inventario de la floristería seleccionada
		mostrarStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panell, m11.stock(llistaFloristeries.getSelectedIndex()),
						"Stock \'" + llistaFloristeries.getSelectedItem() + "\'",
								JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		// Botones 'AfegirXXX', de las factorías pertinentes
		Box box2 = Box.createHorizontalBox();
		for(int i = 0; i < factoriesBotons.length; i++) {
			botonsAfegir[i] = factoriesBotons[i].afegirButton();
			box2.add(botonsAfegir[i]);
			if(i < factoriesBotons.length-1)
				box2.add(Box.createHorizontalStrut(5));
		}
		panell.add(Box.createVerticalStrut(10));
		panell.add(box2);
		
		// Salida del programa
		sortir = new JButton("Sortir");
		sortir.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		panell.add(Box.createVerticalStrut(10));
		panell.add(sortir);
		sortir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Adéu!");
				System.exit(0);
			}
			
		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Método principal; instancia el controlador
	private void start() {
		m11 = new Controller();
	}
	
	// Mensaje de error
	public static void error(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	// Generación de los botones particulares a cada línea
	// de productos mediante el patrón "Abstract Factory".
	interface ButtonsProducteFactory {
		
		ButtonAfegirProducte afegirButton();
//		ButtonRetirarProducte retirarButton();
//		ButtonRetirarProducte stockButton();
	}
	
	class ButtonsArbreFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonAfegirProducte afegirButton() {
			return new ButtonAfegirArbre("AfegirArbre", "Arbre nou", Main.this); 
		}
		
	}
	
	class ButtonsFlorFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonAfegirProducte afegirButton() {
			return new ButtonAfegirFlor("AfegirFlor", "Flor nova", Main.this);
		}
		
	}
	
	class ButtonsDecoracioFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonAfegirProducte afegirButton() {
			return new ButtonAfegirDecoracio("AfegirDecoració", "Decoració nova", Main.this);
		}
		
	}
	
}
