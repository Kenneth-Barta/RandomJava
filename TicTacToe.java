
import java.util.Scanner;
import java.io.PrintStream;
import java.util.Random;


public class TicTacToe
{
    static Scanner stdin = new Scanner( System.in);
    static PrintStream stdout = System.out;

    private static char human;
    private static char computer;

    public static void main(String[] arg)
    {
      Board bd = new Board();
      char currentPlayer;
      String firstplay = "";
      char firstplaya = 'X';
      char secplaya = 'O';

      System.out.print("May I go first (yes/no):");
      if(stdin.hasNextLine()){
        firstplay = stdin.nextLine();
        firstplay = firstplay.toLowerCase();
      }
      if(firstplay.equals("yes")){
        currentPlayer = firstplaya;
        human = secplaya;
        computer = firstplaya;
      }
      else {
        currentPlayer = firstplaya;
        human = firstplaya;
        computer = secplaya;
      }



      while(true) {
        getmove(bd, currentPlayer); //puts move on the board
        bd.print();

        if( bd.won(currentPlayer)) {
          stdout.printf("Player %c has won!\n", currentPlayer);
          return; //end main
        }

        if( bd.done()) {
          stdout.println("Stalemate!");
          return;
        }

        if( currentPlayer == 'X')
          currentPlayer = 'O';
        else
          currentPlayer = 'X';
      }
    }

    static void getmove( Board bd, char player) {
      if( player == human) {
        usermove(bd);
      } else {
        machinemove(bd);
      }
    }

    static Random r = new Random(99);

    static void machinemove(Board bd) {
      for(int i = 0; i < 3; ++ i){//make winning move
        for(int j = 0; j < 3; ++j){
          if(bd.win(i,j,computer)){
            bd.play(i,j,computer);
            return;
          }
        }
      }
      for(int i = 0; i<3; ++i){//blocking a winning move
        for(int j = 0; j<3;++j){
          if(bd.win(i,j,human)){
            bd.play(i,j,computer);
            return;
          }
        }
      }

      for(int i = 0; i<3; ++i){// making a clever move
        for(int j = 0; j<3;++j){
          if(bd.clever(i,j,computer)){
            bd.play(i,j,computer);
            return;
          }
        }
      }
      for(int i = 0; i < 3; ++ i){// block clever move
        for(int j = 0; j < 3; ++j ){
          if(bd.clever(i,j,human)){
            bd.play(i,j,computer);
            return;
          }
        }
      }

      int row = r.nextInt(3);
      int col = r.nextInt(3);

      while( !bd.play(row, col, computer)) {

        row = r.nextInt(3);
        col = r.nextInt(3);
      }
    }

    static void usermove(Board bd) {
      int row = getpos("row (1,2,3): ");
      int col = getpos("col (1,2,3): ");

      while( !bd.play(row, col, human)) {
        stdout.println("Cannot play on non-blank location!");
        row = getpos("row (1,2,3): ");
        col = getpos("col (1,2,3): ");
      }

    }

    static int getpos(String prompt) {
      int i;

      stdout.print(prompt);

      try {
        i = stdin.nextInt();
      }
      catch( Exception e) {
        return getpos(prompt);
      }

      if( 1 <= i && i <= 3)
        return i - 1; //our coords in range 0..2

      return getpos(prompt);
    }

}




class Board {
  char[][] b;
  int moves;

  boolean done() {
    return moves == 9;
  }

  boolean won(char p) { //either 'X' or 'O'
    for( int i = 0; i < 3; ++i)
      if( (eq(i,0,p) && eq(i,1,p) && eq(i,2,p)) ||
          (eq(0,i,p) && eq(1,i,p) && eq(2,i,p))
      ) return true;
    if( (eq(0,0,p) && eq(1,1,p) && eq(2,2,p)) ||
        (eq(0,2,p) && eq(1,1,p) && eq(2,0,p))
    ) return true;
    return false;
  }

  private boolean eq(int i, int j, char p) {
    return b[i][j] == p;
  }

  Board() {
    b = new char[3][3];
    for( int r = 0; r < 3; ++r)
      for( int c = 0; c < 3; ++c)
        b[r][c] = ' ';
  }

  boolean play(int row, int col, char p) {
    if( b[row][col] == ' ') {
      b[row][col] = p;
      ++moves;
      return true;
    } else return false;
  }

  void print() {
    System.out.println();
    System.out.printf( " %c | %c | %c \n", b[0][0], b[0][1], b[0][2]);
    System.out.println("-----------");
    System.out.printf( " %c | %c | %c \n", b[1][0], b[1][1], b[1][2]);
    System.out.println("-----------");
    System.out.printf( " %c | %c | %c \n", b[2][0], b[2][1], b[2][2]);
    System.out.println();
  }

boolean clever(int row,int col,char p){
  if(!(eq(row,col,' '))){
    return false;
  }
  int count = 0;
  b[row][col] = p;
  for(int i = 0;i<3;++i){
    for(int j = 0; j<3; ++j){
      if(win(i,j,p)){
        count++;
      }
    }
  }
  b[row][col] = ' ';
  if(count >= 2){
    return true;
  }
  return false;
}
boolean win(int row, int col, char p ){
  if(!(eq(row,col,' '))){
    return false;
  }
  b[row][col] = p;
  boolean attempt = won(p);
  b[row][col] = ' ';
  return attempt;
}

}
