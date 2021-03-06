package gui;

import java.util.Objects;
/**
 * Koordinate polja v plosci
 */
public class Koordinati {
	private int x;
	private int y;
	
	public Koordinati(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() { 
		return x; 
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Poteza [x=" + x + ", y=" + y + "]";
	}
	
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || this.getClass() != o.getClass()) return false;
		Koordinati k = (Koordinati) o;
		return this.x == k.x && this.y == k.y;
	}
	
	@Override
	public int hashCode () {
		return Objects.hash(this.x, this.y);
	}
}
