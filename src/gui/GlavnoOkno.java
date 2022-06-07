package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.*;

import gui.Igralec;
import splosno.KdoIgra;
import gui.Vodja;
import gui.VrstaIgralca;
import logika.Igra;

/**
 * Glavno okno aplikacije
 */

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {
	
	protected IgralnoPolje polje;
	
	//statusna vrstica
	private JLabel status;

	//nekaj gumbov, s katerimi si izberemo nastavitve
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem velikostPolja;
	
	public GlavnoOkno() {
		
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		polje = new IgralnoPolje();
		
		//osnovne nastavitve
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		//status
		status = new JLabel();
		
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
		//menu
		JMenuBar igralni_meni = new JMenuBar();
		
		//dodamo menuje
		JMenu igraj = dodajMenu(igralni_meni, "Nova Igra");
		//JMenu nastavitve = dodajMenu(igralni_meni, "Nastavitve");
		setJMenuBar(igralni_meni);
		
		//menu igraj
		igraClovekRacunalnik = dodajMenuItem(igraj, "Clovek - Crni, Racunalnik - Beli");
		igraClovekClovek = dodajMenuItem(igraj, "Igralec - Crni, Clovek - Beli");
		//igraClovekClovek = dodajMenuItem(igraj, "�lovek - �rni, �lovek - Beli");
		//igraRacunalnikRacunalnik = dodajMenuItem(igraj, "Ra�unalnik - �rni, Ra�unalnik - Beli");
		
		//menu nastavitve
		//velikostPolja = dodajMenuItem(nastavitve, "Velikost Polja");
	}
	
	//funkcije iz vaj
	
	public JMenu dodajMenu(JMenuBar menubar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menubar.add(menu);
		return menu;
	}
	
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//nastavimo igralca clovek-racunalnik
		if (source == igraClovekRacunalnik) {
			
			Vodja.igralci = 1;

			Vodja.igramoNovoIgro();

		}
		//nastavimo igralca clovek-clovek
		else if (source == igraClovekClovek) {
			
			Vodja.igralci = 2;

			Vodja.igramoNovoIgro();
		}
	
		
		//moznost spreminjanja polja
		else if (source == velikostPolja) {
			String velikostPolja = JOptionPane.showInputDialog(this, "Vnesite velikost polja!");
			if (velikostPolja != null && velikostPolja.matches("\\d+")) {
				int velikost = Integer.parseInt(velikostPolja);
				if (velikost > 25) Igra.N = 25;
				else if (velikost < 4) Igra.N = 5;
				else Igra.N = velikost;
			}
		}
	}

	//osvezi GUI
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanje()) {
			case NEODLOCENO: status.setText("Igra je neodlo�ena");
				break;
			case V_TEKU: status.setText("Na potezi je " + " Vodja.igra.naPotezi + \" - \" + Vodja.kdoIgra.get(Vodja.igra.naPotezi()).ime() " );
				break;
			case ZMAGA_1: status.setText("Zmagal je �rni - " + "Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime()");
				break;
			case ZMAGA_2: status.setText("Zmagal je Beli - " + "Vodja.kdoIgra.get(Vodja.igra.naPotezi().nasprotnik()).ime()");
				break;
			}
		}
		polje.repaint();
	}

}
