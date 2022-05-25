package logika;

import java.util.ArrayList;

import splosno.Poteza;

public class Igra {

    public enum Stanje {
        V_TEKU, ZMAGA_1, ZMAGA_2, NEODLOCENO;
    }

    int[][] board;
  
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
        this.board = new int[8][8];
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

    void flipMove(Poteza move){
        int row = move.getY();
        int col = move.getX();
        // check all 8 directions
        for (int i = 0; i < 8; i++) {
            int newRow = row + rowDir[i];
            int newCol = col + colDir[i];
            int opponent = this.board[row][col] == 1 ? 2 : 1;
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
    boolean validMove(Poteza move, int player) {
      int row = move.getY();
      int col = move.getX();
  
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

    boolean validMove(Poteza p) {
        int player = this.naPotezi();
        return validMove(p, player);
    }

    public ArrayList<Poteza> getValidMoves(int player) {
        ArrayList<Poteza> validMoves = new ArrayList<Poteza>();
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {
            Poteza move = new Poteza(i, j);
            if (validMove(move, player)) {
              validMoves.add(move);
            }
          }
        }
        return validMoves;
    }

    public ArrayList<Poteza> getValidMoves() {
        return getValidMoves(this.naPotezi());
    }

    public void odigraj(Poteza p) {
        if (p != null && this.validMove(p)) {
            int x = p.getX();
            int y = p.getY();
            this.board[y][x] = this.naPotezi();
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
                if (this.board[i][j] == 1) {
                    st_1 += 1;
                }
                if (this.board[i][j] == 2) {
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