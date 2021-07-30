/** Back-end Java, IT Academy
*** M11 - Floristeries (Pair Programming)
*** Miguel Blasco & Roger Torrent */

package floristeries.viewAPI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
	private final JButton crearFloristeria, valorStock, sortir;
	final JComboBox<String> llistaFloristeries;
	private Box box1, box2;
	
	private final ButtonsProducteFactory[] factoriesBotons = {
		new ButtonsArbreFactory(),
		new ButtonsFlorFactory(),
		new ButtonsDecoracioFactory(),
		// Nuevas l�neas de productos se a�aden aqu�
	};
	// Mantenemos una lista de botones para poder activarlas/desactivarlas al un�sono
	private List<JButton> botons = new ArrayList<>();
	
	// Controlador asignado al usuario
	Controller m11;
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	// Constructor de la ventana principal y men� del programa
	public Main() {
		super("M�dul 11 -- Floristeries");
		
		panell = (JPanel)getContentPane();
		panell.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		panell.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		crearFloristeria = new JButton("CrearFloristeria");
		llistaFloristeries = new JComboBox<>();
		llistaFloristeries.setPrototypeDisplayValue("1234567890"); // Fixa la grand�ria m�xima
		valorStock = new JButton("ValorStock");
		valorStock.setEnabled(false);
		botons.add(valorStock);
		box1 = Box.createHorizontalBox();
		box1.add(crearFloristeria);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(llistaFloristeries);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(valorStock);
		panell.add(box1);
		
		// Florister�a nueva
		crearFloristeria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ev) {
				String nom = JOptionPane.showInputDialog(panell, "Nom de la floristeria:",
						"Nova floristeria", JOptionPane.QUESTION_MESSAGE);
				
				try { if(nom != null  && m11.crearFloristeria(new Floristeria(nom))) {
					llistaFloristeries.addItem(nom);
					llistaFloristeries.setSelectedItem(nom);
					botons.forEach(bP -> bP.setEnabled(true)); // Activaci� dels botons
				} } catch(Exception ex) { error(ex); }
			}
			
		});
		
		// Actualiza la lista de florister�as en 'llistaFloristeries'. Esto no resulta necesario en este
		// programa, pues todas las florister�as se a�aden �in situ� mediante el bot�n 'crearFloristeria'
		// y simplemente ocupan espacio local en memoria. Pero ser�a imprescindible en caso de acceder a
		// la BBDD como un usuario m�s, tanto al disco duro como a un servidor remoto.
		llistaFloristeries.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				llistaFloristeries.setModel(new DefaultComboBoxModel<String>(m11.nomsFloristeries()));
				llistaFloristeries.showPopup();
			}
			
			@Override
			public void focusLost(FocusEvent e) {}
			
		});
		
		// Mostrar el valor total del inventario de la florister�a seleccionada
		valorStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(panell, m11.valorStock(llistaFloristeries.getSelectedIndex()),
						"Stock \'" + llistaFloristeries.getSelectedItem() + "\'",
								JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		box2 = Box.createVerticalBox();
		// Hileras de botones con la terna 'Producte'
		afegirBotonsProducte((bPF) -> bPF.afegirButton());  // Creaci� botons 'AfegirXXX'
		afegirBotonsProducte((bPF) -> bPF.stockButton());   // Creaci� botons 'StockXXX'
		afegirBotonsProducte((bPF) -> bPF.retirarButton()); // Creaci� botons 'RetirarXXX'
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
				System.out.println("Ad�u!");
				System.exit(0);
			}
			
		});
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Llamadas secuenciales a las factor�as pertinentes para crear una hilera de 
	// dedicada a una operaci�n concreta, desglosada por familias de productos.
	private void afegirBotonsProducte(Function<ButtonsProducteFactory, ButtonProducte> operacio) {
		Box box = Box.createHorizontalBox();
		for(int i = 0; i < factoriesBotons.length; i++) {
			ButtonProducte bP = operacio.apply(factoriesBotons[i]);
			botons.add(bP);
			box.add(bP);
			if(i < factoriesBotons.length-1)
				box.add(Box.createHorizontalStrut(5));
		}
		box2.add(Box.createVerticalStrut(10));
		box2.add(box);
	}
	
	// M�todo principal; instancia el controlador
	private void start() {
		m11 = new Controller();
	}
	
	// Mensaje de error
	public static void error(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	// Generaci�n de los botones particulares a cada l�nea
	// de productos mediante el patr�n "Abstract Factory".
	interface ButtonsProducteFactory {
		
		ButtonProducte afegirButton();
		ButtonProducte stockButton();
		ButtonProducte retirarButton();
		
	}
	
	class ButtonsArbreFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonProducte afegirButton() {
			return new ButtonAfegirArbre("AfegirArbre", Main.this);
		}
		
		@Override
		public ButtonProducte stockButton() {
			return new ButtonStockArbres("StockArbres", Main.this);
		}
		
		@Override
		public ButtonProducte retirarButton() {
			return new ButtonRetirarArbre("RetirarArbre", Main.this);
		}
		
	}
	
	class ButtonsFlorFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonProducte afegirButton() {
			return new ButtonAfegirFlor("AfegirFlor", Main.this);
		}
		
		@Override
		public ButtonProducte stockButton() {
			return new ButtonStockFlors("StockFlors", Main.this);
		}
		
		@Override
		public ButtonProducte retirarButton() {
			return new ButtonRetirarFlor("RetirarFlor", Main.this);
		}
		
	}
	
	class ButtonsDecoracioFactory implements ButtonsProducteFactory {
		
		@Override
		public ButtonProducte afegirButton() {
			return new ButtonAfegirDecoracio("AfegirDecoraci�", Main.this);
		}
		
		@Override
		public ButtonProducte stockButton() {
			return new ButtonStockDecoracions("StockDecoracions", Main.this);
		}
		
		@Override
		public ButtonProducte retirarButton() {
			return new ButtonRetirarDecoracio("RetirarDecoraci�", Main.this);
		}
		
	}
	
}
