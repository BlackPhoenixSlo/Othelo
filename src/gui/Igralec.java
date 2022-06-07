package gui;

/**
 * Igralci igre
 */

public enum Igralec {
	B, W;
	
	public Igralec nasprotnik() {
		return (this == B ? W : B);
	}
	
	public Polje getPolje() {
		return (this == B ? Polje.B : Polje.W);
	}
	
	public Stanje getStanje() {
		return (this == B ? Stanje.ZMAGA_B : Stanje.ZMAGA_W);
	}

}
