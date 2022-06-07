package gui;

/**
 * Mozne vrste igralcev
 */
public enum VrstaIgralca {
	R, C; 
	
	@Override
	public String toString() {
		switch (this) {
		case C: return "�lovek";
		case R: return "Ra�unalnik";
		default: assert false; return "";
		}
	}
}
