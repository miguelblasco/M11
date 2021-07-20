/** Back-end Java, IT Academy */

package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.*;
import application.Controller;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private final JPanel panell;
	private final JButton crearFloristeria, mostrarStock, afegirArbre, afegirFlor, afegirDecoracio, sortir;
	private final JComboBox<String> llistaFloristeries;
	
	private Controller m11;
	
	public static void main(String[] args) {
		new Main().start();
	}
	
	// Constructor de la ventana principal y men� del programa
	public Main() {
		super("M�dul 11 -- Milestone 1");
		
		panell = (JPanel)getContentPane();
		panell.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		panell.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		crearFloristeria = new JButton("CrearFloristeria");
		llistaFloristeries = new JComboBox<String>();
		llistaFloristeries.setPrototypeDisplayValue("1234567890"); // Fixa la grand�ria m�xima
		mostrarStock = new JButton("Stock");
		mostrarStock.setEnabled(false);
		Box box1 = Box.createHorizontalBox();
		box1.add(crearFloristeria);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(llistaFloristeries);
		box1.add(Box.createHorizontalStrut(5));
		box1.add(mostrarStock);
		panell.add(box1);
		
		afegirArbre = new JButton("AfegirArbre");
		afegirFlor = new JButton("AfegirFlor");
		afegirDecoracio = new JButton("AfegirDecoraci�");
		afegirArbre.setEnabled(false);
		afegirFlor.setEnabled(false);
		afegirDecoracio.setEnabled(false);
		Box box2 = Box.createHorizontalBox();
		box2.add(afegirArbre);
		box2.add(Box.createHorizontalStrut(5));
		box2.add(afegirFlor);
		box2.add(Box.createHorizontalStrut(5));
		box2.add(afegirDecoracio);
		panell.add(Box.createVerticalStrut(10));
		panell.add(box2);
		
		sortir = new JButton("Sortir");
		sortir.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		panell.add(Box.createVerticalStrut(10));
		panell.add(sortir);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	// M�todo principal; instancia el controlador y activa todos los 'listeners' de los botones
	private void start() {
		m11 = new Controller();
		
		// Florister�a nueva
		crearFloristeria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nom = JOptionPane.showInputDialog(panell, "Nom de la floristeria:",
						"Nova floristeria", JOptionPane.QUESTION_MESSAGE);
				
				try { if(nom != null  && m11.crearFloristeria(new Floristeria(nom))) {
					llistaFloristeries.addItem(nom);
					llistaFloristeries.setSelectedItem(nom);
					mostrarStock.setEnabled(true);
					afegirArbre.setEnabled(true);
					afegirFlor.setEnabled(true);
					afegirDecoracio.setEnabled(true);
				} } catch (Exception ex) { error(ex); }
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
		
		// Mostrar el inventario de la florister�a seleccionada
		mostrarStock.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String stock = m11.stock(llistaFloristeries.getSelectedIndex());
				JOptionPane.showMessageDialog(panell, stock, "Stock \'" + llistaFloristeries.getSelectedItem() + "\'",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		
		// A�adir un �rbol al inventario
		afegirArbre.addActionListener(new ActionListenerProducte("Arbre nou"));
		
		// A�adir una flor al inventario
		afegirFlor.addActionListener(new ActionListenerProducte("Flor nova"));
		
		// A�adir una decoraci�n al inventario
		afegirDecoracio.addActionListener(new ActionListenerProducte("Decoraci� nova"));
		
		// Salida del programa
		sortir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Ad�u!");
				System.exit(0);
			}
			
		});
	}
	
	// Mensaje de error
	public static void error(Exception e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	// Clase com�n a los 3 botones 'afegirXXX'
	private class ActionListenerProducte implements ActionListener {
		
		private final String title;
		private JPanel panellInput;
		private JTextField nom, preu, dada;
		private List<JRadioButton> materials;
		private ButtonGroup grupMaterials;
		
		private ActionListenerProducte(String title) {
			this.title = title;
		}
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			panellInput = new JPanel();
			panellInput.setLayout(new BoxLayout(panellInput, BoxLayout.Y_AXIS));
			
			panellInput.add(new JLabel("Nom del producte:"));
			nom = new JTextField();
			panellInput.add(nom);
			panellInput.add(Box.createVerticalStrut(10));
			panellInput.add(new JLabel("Preu del producte:"));
			preu = new JTextField();
			panellInput.add(preu);
			panellInput.add(Box.createVerticalStrut(10));
			switch(ev.getActionCommand()) {
			case("AfegirArbre"):
				panellInput.add(new JLabel("Al�ada del arbre (cm):"));
				dada = new JTextField();
				panellInput.add(dada);
				break;
			case("AfegirFlor"):
				panellInput.add(new JLabel("Color de la flor:"));
				dada = new JTextField();
				panellInput.add(dada);
				break;
			case("AfegirDecoraci�"):
				panellInput.add(new JLabel("Material de la decoraci�:"));
				materials = new ArrayList<>();
				grupMaterials = new ButtonGroup();
				Stream.of(Decoracio.Material.values()).forEach(t -> materials.add(new JRadioButton(t.name())));
				materials.forEach(m -> {
					grupMaterials.add(m);
					m.setActionCommand(m.getText());
					panellInput.add(m);
				});
				break;
			default: return;
			}
			
			if(JOptionPane.showConfirmDialog(panell, panellInput, title,
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) try {
				String error1 = "Format num�ric erroni o absent.\n"
						+ "NOTA: Les mesures en cm s�n nombres enters, i el separador decimal dins el preu �s el punt.";
				String error2 = "Tipus de material sense especificar.";
				try {
					Float fpreu = Float.parseFloat(preu.getText());
					switch(ev.getActionCommand()) {
					case("AfegirArbre"):
						Integer alcada = Integer.parseInt(dada.getText());
						m11.afegirProducte(llistaFloristeries.getSelectedIndex(),
								new Arbre(nom.getText(), alcada, fpreu));
						break;
					case("AfegirFlor"):
						m11.afegirProducte(llistaFloristeries.getSelectedIndex(),
								new Flor(nom.getText(), dada.getText(), fpreu));
						break;
					case("AfegirDecoraci�"):
						if(grupMaterials.getSelection() == null) throw new Exception(error2);
						m11.afegirProducte(llistaFloristeries.getSelectedIndex(),
								new Decoracio(nom.getText(),
										Decoracio.Material.valueOf(grupMaterials.getSelection().getActionCommand()),
											 fpreu));
						break;
					default:
					}
				} catch (NullPointerException | NumberFormatException ex) { throw new Exception(error1); }
			} catch (Exception ex) { error(ex); }
		}
		
	}
	
}
