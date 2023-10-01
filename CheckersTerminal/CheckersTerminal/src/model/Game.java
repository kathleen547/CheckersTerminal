package model;

public class Game {
    public enum State {
        WhiteMoves,
        BlackMoves,
        WhiteWon,
        BlackWon,
    }

    public enum Message {
        CorrectMove,
        WrongMove,
    }

    State state = State.WhiteMoves;
    Board board;
    Observer observer;

    public Game(Board brd, Observer obs) {
        board = brd;
        observer = obs;
    }

    public void tryMakeMove(int srcX, int srcY, int tgtX, int tgtY) {
        // determine who moves depending on state
        Message msg = Message.WrongMove;
        if(getState() == State.WhiteMoves){
            msg = board.makeMoveIfValid(srcX, srcY, tgtX, tgtY, true) ? Message.CorrectMove : Message.WrongMove;
            if(!board.checkContainsBlackPiece()){
                state = State.WhiteWon;
            }
        }
        else if(getState() == State.BlackMoves){
            msg = board.makeMoveIfValid(srcX, srcY, tgtX, tgtY, false) ? Message.CorrectMove : Message.WrongMove;
            if(!board.checkContainsWhitePiece()){
                state = State.BlackWon;
            }
        }

        if(msg == Message.CorrectMove && !(state == State.WhiteWon || state == State.BlackWon)) {
            state = state == State.BlackMoves ? State.WhiteMoves : State.BlackMoves;
        }

        observer.notifyObserver(board.pieces, state, msg);
    }

    public State getState() {
        return state;
    }
}
