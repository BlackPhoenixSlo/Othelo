package gui;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.*;
import logika.Igra;
import splosno.KdoIgra;
import splosno.Poteza;

public class Vodja {
	
	//Slovarja ki bele�ita vrsto igralcev in kdo igra
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	//Okno v katerem se izvaja igra
	public static GlavnoOkno okno;
	
	//Igra
	public static Igra igra = null;
	
	public static int igralci = 2;
	
	//Ali je na vrsti clovek
	public static boolean clovekNaVrsti = false;
	
	//Zacetek nove igre
	public static void igramoNovoIgro() {
		igra = new Igra();
		igramo();
	}

	// se izevede med igro
	public static void igramo() {
		okno.osveziGUI();
		switch (igra.stanje()) {
		case ZMAGA_1: 
		case ZMAGA_2: 
		case NEODLOCENO: 
			System.out.println("jakajkaka");
			return; 
		case V_TEKU: 
		
			int vrstaNaPotezi = igra.naPotezi();
			switch (vrstaNaPotezi) {
			case 1: 
				clovekNaVrsti = true;
				break;
			case 2:
				if (igralci == 2) {
					clovekNaVrsti = true;
					break;
					
				}
				clovekNaVrsti = false;

				Koordinati poteza2 =Inteligenca.izberiPotezo(igra);

				if (igra.validMove(poteza2)) {
					igra.odigraj(poteza2);
					igramo();

					
				}  
				clovekNaVrsti = true;

				break;
			}
		}
	}
	
	
	//Igra racunalnikovo potezo z uporabo inteligence
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		Koordinati poteza = Inteligenca.izberiPotezo(zacetkaIgra);
		if (igra.board[poteza.getX()][poteza.getY()]== 0) 
		{
			//igra.odigraj(poteza);
			//igramo();
		}
			
		}
	
	
	
	//Igra clovekovo potezo
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		try {
			
				if (igra.validMove(poteza)) {
					
					igra.odigraj(poteza);

					igramo();
					

				}  
				
			

			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

	 