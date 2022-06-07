package gui;



import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import gui.Polje;
import gui.Vodja;
/**
 * Obmo�je v oknu v katerem je igralno polje
 */
import logika.Igra;
@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener {

	public IgralnoPolje() {
		setBackground(Color.green);
		setPreferredSize(new Dimension(800, 800));
		this.addMouseListener(this);
	}
	
	//Sirina polja
	private final static double SIRINA = 0.1;
	//Rob polja
	private final static double ROB = 0.05;
	//Velikost kvadratka
	private double velikostKvadratka() {
		return Math.min(getHeight(), getWidth()) / Igra.N;
	}
	
	//narise crn krogec
	private void narisiCrnega(Graphics2D g2, int i, int j) {
		double sirina = velikostKvadratka();
		double x_koordinata = i * sirina + (sirina * SIRINA);
		double y_koordinata = j * sirina + (sirina * SIRINA);
		double polmer = sirina - (2 * sirina * SIRINA);
		
		g2.setColor(Color.BLACK);
		g2.fillOval((int) x_koordinata,(int) y_koordinata,(int) polmer,(int) polmer);
		g2.setColor(Color.DARK_GRAY);
		g2.setStroke(new BasicStroke((float) (ROB * sirina)));
		g2.drawOval((int) x_koordinata,(int) y_koordinata,(int) polmer,(int) polmer);
	}
	
	//narise bel krogec
	private void narisiBelega(Graphics2D g2, int i, int j) {
		double sirina = velikostKvadratka();
		double x_koordinata = i * sirina + (sirina * SIRINA);
		double y_koordinata = j * sirina + (sirina * SIRINA);
		double polmer = sirina - (2 * sirina * SIRINA);
		
		g2.setColor(Color.white);
		g2.fillOval((int) x_koordinata,(int) y_koordinata,(int) polmer,(int) polmer);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke((float) (ROB * sirina)));
		g2.drawOval((int) x_koordinata,(int) y_koordinata,(int) polmer,(int) polmer);
	}
	
	//narise navpicne in vodoravne crte
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		double sirina = velikostKvadratka();
		
		g2.setColor(Color.gray);
		g2.setStroke(new BasicStroke((float) (sirina * SIRINA)));
		for (int i = 1; i < Igra.N; i++) {
			g2.drawLine((int) (i * sirina), 0,(int) (i * sirina),(int) (Igra.N * sirina));
			g2.drawLine(0,(int) (i * sirina),(int) (Igra.N * sirina),(int) (i * sirina));
		}
		
		int[][] plosca;
		
		if (Vodja.igra != null) {
			plosca = Vodja.igra.board;
			for (int i = 0; i < Igra.N; i++) {
				for (int j = 0; j < Igra.N; j++) {
					switch(plosca[i][j]) {
					case 1: narisiCrnega(g2, i, j); break;
					case 2: narisiBelega(g2, i, j); break;
					default: break;
					}
				}
			}
		}
	}
	
	//krogec se narise z pritiskom na gumb
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			double velikost = velikostKvadratka();
			int kvadrat_x = x /(int) velikost;
			int kvadrat_y = y /(int) velikost;
			double dovoljen_x = (x % (int) velikost) / velikost;
			double dovoljen_y = (y % (int) velikost) / velikost;
			if (0 <= kvadrat_x && kvadrat_x < Igra.N && (0.5 * ROB) < dovoljen_x && dovoljen_x < (1 - (0.5 * ROB))
					&& 0 <= kvadrat_y && kvadrat_y < Igra.N && (0.5 * ROB) < dovoljen_y && dovoljen_y < (1 - (0.5 * ROB))) {
				Vodja.igrajClovekovoPotezo(new Koordinati(kvadrat_y, kvadrat_x));
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
