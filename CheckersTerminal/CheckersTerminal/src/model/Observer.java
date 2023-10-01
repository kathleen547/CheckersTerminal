package model;

public interface Observer {
    void notifyObserver(Board.Piece[][] pieces, Game.State state, Game.Message message);
}
