package gomoku;

import gui.GlavnoOkno;

import gui.Vodja;

public class Othello {

	public static void main(String[] args) {
		GlavnoOkno glavno_okno = new GlavnoOkno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
				
	}
	
}