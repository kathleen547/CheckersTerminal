package view;

import model.Board.Piece;
import model.Game.State;
import model.Game.Message;
import model.Observer;

public class View implements Observer {
    @Override
    public void notifyObserver(Piece[][] pieces, State state, Message message) {
        printUpperMessage(state);
        printBoard(pieces);
        printBottomMessage(message);
    }

    void printUpperMessage(State state) {
        switch (state){
            case WhiteMoves -> System.out.println("White is moving");
            case BlackMoves -> System.out.println("Black is moving");
            case WhiteWon -> System.out.println("White won");
            case BlackWon -> System.out.println("Black won");
        }
    }

    void printBoard(Piece[][] pieces) {

        for(int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                switch (pieces[i][j]) {
                    case Empty -> System.out.print('.');
                    case WhiteRegular -> System.out.print('O');
                    case BlackRegular -> System.out.print('1');
                    case WhiteQueen -> System.out.println('7');
                    case BlackQueen -> System.out.println('9');
                }
            }
            System.out.println();
        }
    }

    void printBottomMessage(Message message) {
        switch (message){
            case CorrectMove -> System.out.println("Correct move");
            case WrongMove -> System.out.println("Wrong move. Try again");
        }
    }
}
