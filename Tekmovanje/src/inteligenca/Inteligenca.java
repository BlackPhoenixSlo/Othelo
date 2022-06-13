package inteligenca;

import java.util.ArrayList;
import splosno.*;
import logika.*;
import logika.Igra.Stanje;

class Inteligenca extends KdoIgra {
    public static void main(String[] args) {
        
    }
    
    public Inteligenca() {
        super("Hypertext Assassins");
    }

    public static int oceniPozicijo(Igra igra, int player) {
        int[] ocene = igra.prestejZetoncke();
        int ocena = 0;
        if (player == 1) {
            ocena = ocene[0];
        }
        else {
            ocena = ocene[1];
        }
        return ocena;
    }

    public static OcenjenaPoteza izberiPotezo(Igra igra, int globina, int alpha, int beta, int player) {
        int ocena;
        int WIN = 100;
        int LOSS = -WIN;
        int DRAW = 0;

        int ocenap;
        Poteza kandidat = null;
        // Če igra player, maksimiziramo oceno z začetno oceno LOSS
        // Če ne igra player, minimiziramo oceno z začetno oceno WIN
        if (igra.naPotezi() == player) {ocena = LOSS;} else {ocena = WIN;}
        ArrayList<Poteza> moznePoteze = igra.getValidMoves();
        if (moznePoteze.size() == 0) {
            /*kopijaIgre.odigraj(); //funkcija, ki samo zamenja igralca na potezi
            ocenap = izberiPotezo(kopijaIgre, globina-1, alpha, beta, player).ocena;*/
            moznePoteze.add(null);
        }

        kandidat = moznePoteze.get(0);
        for (Poteza p : moznePoteze) {
            Igra kopijaIgre = new Igra(igra);
            kopijaIgre.odigraj (p);
            switch (kopijaIgre.stanje()) {
                case ZMAGA_1: ocenap = (player == 1 ? WIN : LOSS); break;
                case ZMAGA_2: ocenap = (player == 2 ? WIN : LOSS); break;
                case NEODLOCENO: ocenap = DRAW; break;
                default: // Nekdo je na potezi
            }
                if (globina == 1 || igra.getValidMoves().size() == 0) {
                    ocenap = oceniPozicijo(kopijaIgre, player);
                }
                else {
                    ocenap = izberiPotezo(kopijaIgre, globina-1, alpha, beta, player).ocena;
                }
            if (igra.naPotezi() == player) { // Maksimiziramo oceno
                if (ocenap > ocena) { 
                    ocena = ocenap;
                    kandidat = p;
                    alpha = Math.max(alpha, ocena);
                }
            } 
            else { // igra.naPotezi() != player, torej minimiziramo oceno
                if (ocenap < ocena) { 
                    ocena = ocenap;
                    kandidat = p;
                    beta = Math.min(beta, ocena);
                }
            }
            if (alpha >= beta) {// Ostale poteze ne pomagajo
                return new OcenjenaPoteza (kandidat, ocena);
            }
        }
        
        return new OcenjenaPoteza (kandidat, ocena);
    }

    public static Poteza izberiPotezo(Igra igra) {
        return izberiPotezo(igra, 8, -100, 100, igra.naPotezi()).poteza;
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {
            System.out.print(board[i][j] == 1 ? "X" : board[i][j] == 2 ? "O" : ".");
          }
          System.out.println();
        }
        System.out.println();
    }
}

class OcenjenaPoteza {
    Poteza poteza;
    int ocena;
    public OcenjenaPoteza (Poteza poteza, int ocena) {
    this.poteza = poteza;
    this.ocena = ocena;
    }
}

