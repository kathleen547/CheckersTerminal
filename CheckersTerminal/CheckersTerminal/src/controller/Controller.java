package controller;

import model.Game;

import java.util.Arrays;

public class Controller {

    static final String exitMessage = "exit";
    static final Character[] firstCoords = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    static final Character[] secondCoords = {'0', '1', '2', '3', '4', '5', '6', '7'};

    Game game;

    public Controller(Game game) {
        this.game = game;
    }

    // process input and return false if game should not be continued
    public boolean processInput(String input) {
        if(isQuitMessage(input)) return false;
        if(game.getState() == Game.State.WhiteWon || game.getState() == Game.State.BlackWon) return false;
        Move move = extractMove(input);
        if(move != null){
            game.tryMakeMove(move.srcX, move.srcY, move.tgtX, move.tgtY);
            return true;
        }
        else{
            System.out.println("incorrect input");
            return true;
        }
    }

    boolean isQuitMessage(String input) {
        return input.equals(exitMessage);
    }

    class Move {
        Move(int sx, int sy, int tx, int ty) {
            srcX = sx;
            srcY = sy;
            tgtX = tx;
            tgtY = ty;
        }
        int srcX, srcY, tgtX, tgtY;
    }

    Move extractMove(String input) {
        Move move;
        if (input.length() == 5 &&
                Arrays.asList(firstCoords).contains(input.charAt(0)) &&
                Arrays.asList(secondCoords).contains(input.charAt(1)) &&
                input.charAt(2) == ' ' &&
                Arrays.asList(firstCoords).contains(input.charAt(3)) &&
                Arrays.asList(secondCoords).contains(input.charAt(4))
        ) {
            return new Move(
                    Arrays.asList(firstCoords).indexOf(input.charAt(0)),
                    Arrays.asList(secondCoords).indexOf(input.charAt(1)),
                    Arrays.asList(firstCoords).indexOf(input.charAt(3)),
                    Arrays.asList(secondCoords).indexOf(input.charAt(4))
            );
        }
        return null;
    }
}
