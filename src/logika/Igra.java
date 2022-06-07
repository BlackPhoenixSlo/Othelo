package logika;


import java.util.ArrayList;

import gui.Polje;
import gui.Stanje;
import gui.Koordinati;
import splosno.Poteza;


public class Igra {
	
	
	//Velikost polja
  	public static int N = 8;
  	
    public enum Stanje {
        V_TEKU, ZMAGA_1, ZMAGA_2, NEODLOCENO;
    }

    public int[][] board = new int[8][8];
  
    int[] rowDir = new int[] { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] colDir = new int[] { -1, 0, 1, -1, 1, -1, 0, 1 };

    public int igralecNaPotezi = 1;
  
    public Igra() {
      board = new int[8][8];
      // fill in the middle 2x2 square with a checkered pattern
      board[3][3] = 1;
      board[4][4] = 1;
      board[3][4] = 2;
      board[4][3] = 2;
    }

    public Igra(Igra igra) {
        
      
        for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
        this.board[i][j] = igra.board[i][j];
        }
        }
        this.igralecNaPotezi = igra.naPotezi();
    }
  
    // board getter
    public int[][] getBoard() {
      return board;
    }

    public void flipMove(Koordinati p){
        int row = p.getY();
        int col = p.getX();
        // check all 8 directions
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowDir[i];
            int newCol = col + colDir[i];
            int opponent = board[row][col] == 1 ? 2 : 1;
            int count = 0;
            while (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && board[newRow][newCol] == opponent) {
                newRow += rowDir[i];
                newCol += colDir[i];
                count++;
            }
            if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && board[newRow][newCol] == this.board[row][col] && count > 0) {
                newRow = row + rowDir[i];
                newCol = col + colDir[i];
                count = 0;
                while (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && board[newRow][newCol] == opponent) {
                    board[newRow][newCol] = this.board[row][col];
                    newRow += rowDir[i];
                    newCol += colDir[i];
                    count++;
                }
            }
        }
    }
  
    // Returns if the move is valid for black
    boolean validMove(Koordinati p, int player) {
      int row = p.getY();
      int col = p.getX();
  
      // if move in range
      if (row < 0 || row > 7 || col < 0 || col > 7) {
        return false;
      }
  
      // if board is not empty
      if (board[row][col] != 0) {
        return false;
      }
      // check all 8 directions
      for (int i = 0; i < 8; i++) {
        int newRow = row + rowDir[i];
        int newCol = col + colDir[i];
        int opponent = player == 1 ? 2 : 1;
        int count = 0;
        while (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && board[newRow][newCol] == opponent) {
          newRow += rowDir[i];
          newCol += colDir[i];
          count++;
        }
        if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7 && board[newRow][newCol] == player && count > 0) {
          return true;
        }
      }
      return false;
    }

    public boolean validMove(Koordinati p) {
        int player = this.naPotezi();
        return validMove(p, player);
    }

    public ArrayList<Koordinati> getValidMoves(int player) {
        ArrayList<Koordinati> validMoves = new ArrayList<Koordinati>();
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {
        	  Koordinati move = new Koordinati(i, j);
            if (validMove(move, player)) {
              validMoves.add(move);
            }
          }
        }
        return validMoves;
    }

    public ArrayList<Koordinati> getValidMoves() {
        return getValidMoves(this.naPotezi());
    }

    public void odigraj(Koordinati p) {
        if (p != null && this.validMove(p)) {
            int x = p.getX();
            int y = p.getY();
            board[y][x] = this.naPotezi();
            this.flipMove(p);
        }
        if (this.naPotezi() == 1) {
            this.igralecNaPotezi = 2;
        }
        else {
            this.igralecNaPotezi = 1;
        }
    }

    public void odigraj() {
        if (this.naPotezi() == 1) {
            this.igralecNaPotezi = 2;
        }
        else {
            this.igralecNaPotezi = 1;
        }
    }
    
    

    public int naPotezi() {
        return this.igralecNaPotezi;
    }

    public int[] prestejZetoncke() {
        int[] rezultat = new int[2];
        int st_1 = 0;
        int st_2 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {
                    st_1 += 1;
                }
                if (board[i][j] == 2) {
                    st_2 += 1;
                }
            }
        }
        rezultat[0] = st_1;
        rezultat[1] = st_2;
        return rezultat;
    }

    public Stanje stanje() {
        if (this.getValidMoves(1).size() == 0 && this.getValidMoves(2).size() == 0) {
            int[] rezultat = this.prestejZetoncke();
            if (rezultat[0] > rezultat[1]) {
                return Stanje.ZMAGA_1;
            }
            else if (rezultat[0] < rezultat[1]) {
                return Stanje.ZMAGA_2;
            }
            else {
                return Stanje.NEODLOCENO;
            }
        }
        else {
            return Stanje.V_TEKU;
        }
    }
}