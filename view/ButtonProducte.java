package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import domain.Arbre;
import domain.Decoracio;
import domain.Flor;

abstract class ButtonProducte extends JButton implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	final Main panellPare;
	
	ButtonProducte(String textButton, Main panellPare) {
		super(textButton);
		this.panellPare = panellPare;
		setEnabled(false);
		addActionListener(this);
	}
	
}

abstract class ButtonAfegirProducte extends ButtonProducte {
	
	private static final long serialVersionUID = 1L;
	
	final String titol;
	
	final JPanel panellInput;
	final JTextField nom, preu;
	float fpreu;
	final String errNum = "Format numèric erroni o absent.\n"
			+ "NOTA: Les mesures en cm són nombres enters, i el separador decimal dins el preu és el punt.";
	
	ButtonAfegirProducte(String textButton, String titol, Main panellPare) {
		super(textButton, panellPare);
		this.titol = titol;
		
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
	}
	
	// Acció del botó: finistra nova per a un producte nou
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(JOptionPane.showConfirmDialog(panellPare, panellInput, titol,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) try {
			try { fpreu = Float.parseFloat(preu.getText());
			} catch(NullPointerException | NumberFormatException e) { throw new Exception(errNum); }
			newProducte();
		} catch(Exception ex) { Main.error(ex); } finally { buidarCamps(); }
	}
	
	// Particularitas del producte i ordre de creació
	abstract void newProducte() throws Exception;
	
	// Neteja dels camps del botó
	void buidarCamps() {
		nom.setText("");
		preu.setText("");
	}
	
}

class ButtonAfegirArbre extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	final JTextField alcada;
	int ialcada;
	
	ButtonAfegirArbre(String textButton, String titol, Main panellPare) {
		super(textButton, titol, panellPare);
		
		panellInput.add(new JLabel("Alçada del arbre (cm):"));
		alcada = new JTextField();
		panellInput.add(alcada);
	}
	
	@Override
	void newProducte() throws Exception {
		try { ialcada = Integer.parseInt(alcada.getText());
		} catch(NullPointerException | NumberFormatException e) { throw new Exception(errNum); }
		panellPare.m11.afegirProducte(panellPare.llistaFloristeries.getSelectedIndex(),
				new Arbre(nom.getText(), ialcada, fpreu));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps(); // Buida 'nom' i 'preu'
		alcada.setText("");
	}
	
}

class ButtonAfegirFlor extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	final JTextField color;
	
	ButtonAfegirFlor(String textButton, String titol, Main panellPare) {
		super(textButton, titol, panellPare);
		
		panellInput.add(new JLabel("Color de la flor:"));
		color = new JTextField();
		panellInput.add(color);
	}
	
	@Override
	void newProducte() throws Exception {
		panellPare.m11.afegirProducte(panellPare.llistaFloristeries.getSelectedIndex(),
				new Flor(nom.getText(), color.getText(), fpreu));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps(); // Buida 'nom' i 'preu'
		color.setText("");
	}
	
}

class ButtonAfegirDecoracio extends ButtonAfegirProducte {
	
	private static final long serialVersionUID = 1L;
	
	List<JRadioButton> materials;
	ButtonGroup grupMaterials;
	
	ButtonAfegirDecoracio(String textButton, String titol, Main panellPare) {
		super(textButton, titol, panellPare);
		
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
	void newProducte() throws Exception {
		if(grupMaterials.getSelection() == null) throw new Exception("Tipus de material sense especificar.");
		panellPare.m11.afegirProducte(panellPare.llistaFloristeries.getSelectedIndex(),
				new Decoracio(nom.getText(), Decoracio.Material.valueOf(grupMaterials.getSelection().getActionCommand()),
						fpreu));
	}
	
	@Override
	void buidarCamps() {
		super.buidarCamps(); // Buida 'nom' i 'preu'
		grupMaterials.clearSelection();
	}
	
}
