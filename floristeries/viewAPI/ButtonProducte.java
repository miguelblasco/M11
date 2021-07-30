package floristeries.viewAPI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import floristeries.domain.Arbre;
import floristeries.domain.Decoracio;
import floristeries.domain.Flor;

// Colección de todas las familias de botones de la interfaz de usuario
// vinculadas a los objetos de la clase 'Producte' y sus derivados.
abstract class ButtonProducte extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	final Main panellPare;
	String titol;
	int idxFloristeria;
	Floristeria floristeria;
	
	ButtonProducte(String textButton, Main panellPare) {
		super(textButton);
		this.panellPare = panellPare;
		
		setEnabled(false);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		idxFloristeria = panellPare.llistaFloristeries.getSelectedIndex();
		floristeria = panellPare.m11.llegirFloristeria(idxFloristeria);
		titol = "\'" + floristeria.getNom() + "\' -- ";
	}
	
}

// Familia 'AfegirXXX'
abstract class ButtonAfegirProducte extends ButtonProducte {
	
	private static final long serialVersionUID = 1L;
	
	final JPanel panellInput;
	final JTextField nom, preu, quantitat;
	float fpreu;
	int iquantitat;
	final String errNum = "Format numèric erroni o absent.\n"
			+ "NOTA: Les mesures en cm i les quantitats són nombres\n"
			+ "enters, i el separador decimal dins el preu és el punt.";
	
	ButtonAfegirProducte(String textButton, Main panellPare) {
		super(textButton, panellPare);
		
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
		panellInput.add(new JLabel("Quantitat del producte:"));
		quantitat = new JTextField();
		panellInput.add(quantitat);
		panellInput.add(Box.createVerticalStrut(10));
	}
	
	// Acció del botó: finestra nova per a un producte nou
	void finestra() {
		if(JOptionPane.showConfirmDialog(panellPare, panellInput, titol,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) try {
			try {
				fpreu = Float.parseFloat(preu.getText());
				iquantitat = Integer.parseInt(quantitat.getText());
			} catch(NullPointerException | NumberFormatException e) { throw new Exception(errNum); }
			newProducte();
		} catch(Exception ex) { Main.error(ex); }
		buidarCamps();
	}
	
	// Particularitas del producte i ordre de creació
	abstract void newProducte() throws Exception;
	
	// Neteja dels camps del botó
	void buidarCamps() {
		nom.setText("");
		preu.setText("");
		quantitat.setText("");
	}
	
}

class ButtonAfegirArbre extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	final JTextField alcada;
	int ialcada;
	
	ButtonAfegirArbre(String textButton, Main panellPare) {
		super(textButton, panellPare);
		
		panellInput.add(new JLabel("Alçada del arbre (cm):"));
		alcada = new JTextField();
		panellInput.add(alcada);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Afegir ARBRE";
		finestra();
	}
	
	@Override
	void newProducte() throws Exception {
		try { ialcada = Integer.parseInt(alcada.getText());
		} catch(NullPointerException | NumberFormatException e) { throw new Exception(errNum); }
		panellPare.m11.afegirProducte(idxFloristeria,
				new Arbre(nom.getText(), ialcada, fpreu, iquantitat));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps();
		alcada.setText("");
	}
	
}

class ButtonAfegirFlor extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	final JTextField color;
	
	ButtonAfegirFlor(String textButton, Main panellPare) {
		super(textButton, panellPare);
		
		panellInput.add(new JLabel("Color de la flor:"));
		color = new JTextField();
		panellInput.add(color);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Afegir FLOR";
		finestra();
	}
	
	@Override
	void newProducte() throws Exception {
		panellPare.m11.afegirProducte(idxFloristeria,
				new Flor(nom.getText(), color.getText(), fpreu, iquantitat));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps();
		color.setText("");
	}
	
}

class ButtonAfegirDecoracio extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	List<JRadioButton> materials;
	ButtonGroup grupMaterials;
	
	ButtonAfegirDecoracio(String textButton, Main panellPare) {
		super(textButton, panellPare);
		
		panellInput.add(new JLabel("Material de la decoració:"));
		materials = new ArrayList<>();
		grupMaterials = new ButtonGroup();
		Stream.of(Decoracio.Material.values()).forEach(t -> materials.add(new JRadioButton(t.name())));
		materials.forEach(m -> {
			grupMaterials.add(m);
			m.setActionCommand(m.getText());
			panellInput.add(m);
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Afegir DECORACIÓ";
		finestra();
	}
	
	@Override
	void newProducte() throws Exception {
		if(grupMaterials.getSelection() == null) throw new Exception("Tipus de material sense especificar.");
		panellPare.m11.afegirProducte(idxFloristeria,
				new Decoracio(nom.getText(), Decoracio.Material.valueOf(grupMaterials.getSelection().getActionCommand()),
						fpreu, iquantitat));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps();
		grupMaterials.clearSelection();
	}
	
}

// Familia 'StockXXX'
abstract class ButtonStockProductes extends ButtonProducte {
	
	private static final long serialVersionUID = 1L;
	
	JTextArea inventari;
	JScrollPane inventariScroll;
	
	ButtonStockProductes(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	// Acció del botó: finestra informativa amb el 'stock'
	void finestra(Class<? extends Producte> classProducte) {
		inventari = new JTextArea(panellPare.m11.stockProductes(idxFloristeria, classProducte));
		inventari.setEditable(false);
		inventari.setBackground(UIManager.getColor("Panel.background"));
		inventariScroll = new JScrollPane(inventari);
		inventariScroll.setPreferredSize(new Dimension(345, 115));
		
		JOptionPane.showMessageDialog(panellPare, inventariScroll, titol, JOptionPane.INFORMATION_MESSAGE);
	}
	
}

class ButtonStockArbres extends ButtonStockProductes {
	
	private static final long serialVersionUID = 1L;
	
	ButtonStockArbres(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Inventari ARBRES";
		finestra(Arbre.class);
	}
	
}

class ButtonStockFlors extends ButtonStockProductes {
	
	private static final long serialVersionUID = 1L;
	
	ButtonStockFlors(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Inventari FLORS";
		finestra(Flor.class);
	}
	
}

class ButtonStockDecoracions extends ButtonStockProductes {
	
	private static final long serialVersionUID = 1L;
	
	ButtonStockDecoracions(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Inventari DECORACIONS";
		finestra(Decoracio.class);
	}
	
}

// Familia 'RetirarXXX'
abstract class ButtonRetirarProducte extends ButtonProducte {
	
	private static final long serialVersionUID = 1L;
	
	String inventari;
	JList<String> llistaInventari;
	JScrollPane inventariScroll;
	JOptionPane pane;
	JDialog dialog;
	JButton buttonOK;
	Object selectedValue;
	
	ButtonRetirarProducte(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	// Acció del botó: finestra informativa «interactiva» amb el 'stock' per retirar un producte
	void finestra(Class<? extends Producte> classProducte) {
		inventari = panellPare.m11.stockProductes(idxFloristeria, classProducte);
		llistaInventari = new JList<>(inventari.split("\n"));
		llistaInventari.setBackground(UIManager.getColor("Panel.background"));
		inventariScroll = new JScrollPane(llistaInventari);
		inventariScroll.setPreferredSize(new Dimension(345, 115));
		
		pane = new JOptionPane(inventariScroll, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		dialog = pane.createDialog(panellPare, titol);
		
		buttonOK = pane.getRootPane().getDefaultButton();
		buttonOK.setEnabled(false);
		
		llistaInventari.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				buttonOK.setEnabled(!e.getValueIsAdjusting() && llistaInventari.getSelectedValue() != null);
			}
			
		});
		
		dialog.setVisible(true);
		
		selectedValue = pane.getValue();
		if(selectedValue != null && ((Integer)selectedValue).intValue() == JOptionPane.OK_OPTION)
			panellPare.m11.retirarProducte(idxFloristeria, classProducte, llistaInventari.getSelectedIndex());
	}
	
}

class ButtonRetirarArbre extends ButtonRetirarProducte {
	
	private static final long serialVersionUID = 1L;
	
	ButtonRetirarArbre(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Retirar ARBRE";
		finestra(Arbre.class);
	}
	
}

class ButtonRetirarFlor extends ButtonRetirarProducte {
	
	private static final long serialVersionUID = 1L;
	
	ButtonRetirarFlor(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Retirar FLOR";
		finestra(Flor.class);
	}
	
}

class ButtonRetirarDecoracio extends ButtonRetirarProducte {
	
	private static final long serialVersionUID = 1L;
	
	ButtonRetirarDecoracio(String textButton, Main panellPare) {
		super(textButton, panellPare);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		titol += "Retirar DECORACIÓ";
		finestra(Decoracio.class);
	}
	
}
