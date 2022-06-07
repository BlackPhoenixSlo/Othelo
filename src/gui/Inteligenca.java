package gui;

import java.util.ArrayList;

import logika.Igra;
import splosno.KdoIgra;
import splosno.Poteza;


class Inteligenca extends KdoIgra {
    public static void main(String[] args) {
        int win1 = 0;
        int win2 = 0;
        long dursum = 0;
        int N = 100;
        int penisfuckcumlollmaotouchgrass = 0;
        long max_cas = 0;
        for(int i = 0; i < N; i++){
            System.out.println(i);
            Igra igra1 = new Igra();
            while (igra1.stanje() == Igra.Stanje.V_TEKU) {
                if(igra1.getValidMoves().size() == 0){
                    igra1.igralecNaPotezi = igra1.igralecNaPotezi == 1 ? 2 : 1;
                }
                long startTime = System.nanoTime();
                Koordinati p;
                if(igra1.igralecNaPotezi == 1){
                    ArrayList<Koordinati> moznePoteze = igra1.getValidMoves();
                    p = moznePoteze.get((int)(moznePoteze.size() * Math.random()));
                }else{
                    p = izberiPotezo(igra1, 6, -500, 500, igra1.naPotezi()).poteza;
                }
                long endTime = System.nanoTime();

                if(igra1.igralecNaPotezi == 2){
                    dursum += (endTime - startTime);
                    penisfuckcumlollmaotouchgrass++;
                    if((endTime - startTime) > max_cas){
                        max_cas = (endTime - startTime);
                    }
                }
                igra1.odigraj(p);
                printBoard(igra1.getBoard());
            }

            if(igra1.stanje() == Igra.Stanje.ZMAGA_1){
                win1++;
            }else if(igra1.stanje() == Igra.Stanje.ZMAGA_2){
                win2++;
            }
        }

        System.out.printf("Random je zmagal: %d iger (%d%%)\n", win1, win1 * 100/(win1+win2));
        System.out.printf("Mojca je zmagala: %d iger (%d%%)\n",win2, win2 * 100/(win1+win2));
        System.out.printf("Povprecni cas odlocanja: %d ms \n", (int)(dursum/penisfuckcumlollmaotouchgrass ) / 1000);
        System.out.printf("Max cas odlocanja: %d ms\n", (int) max_cas / 1000);
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
        Koordinati kandidat = null;
        // Če igra player, maksimiziramo oceno z začetno oceno LOSS
        // Če ne igra player, minimiziramo oceno z začetno oceno WIN
        if (igra.naPotezi() == player) {ocena = LOSS;} else {ocena = WIN;}
        ArrayList<Koordinati> moznePoteze = igra.getValidMoves();
        if (moznePoteze.size() == 0) {
            /*kopijaIgre.odigraj(); //funkcija, ki samo zamenja igralca na potezi
            ocenap = izberiPotezo(kopijaIgre, globina-1, alpha, beta, player).ocena;*/
            moznePoteze.add(null);
        }

        kandidat = moznePoteze.get(0);
        for (Koordinati p : moznePoteze) {
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

    public static Koordinati izberiPotezo(Igra igra) {
        return izberiPotezo(igra, 8, -20, 20, igra.naPotezi()).poteza;
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
    Koordinati poteza;
    int ocena;
    public OcenjenaPoteza (Koordinati poteza, int ocena) {
    this.poteza = poteza;
    this.ocena = ocena;
    }
}

