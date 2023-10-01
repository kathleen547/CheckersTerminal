import controller.Controller;
import model.Board;
import model.Game;
import view.View;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Board.Piece[][] pieces = new Board.Piece[8][8];
        Board board = new Board(pieces);
        Game game = new Game(board, view);
        Controller controller = new Controller(game);

        view.notifyObserver(pieces, Game.State.WhiteMoves, Game.Message.CorrectMove);
        while (true) {
            Scanner out = new Scanner(System.in);
            String input = out.nextLine();
            if (!controller.processInput(input))
                break;
        }
        System.out.println("Game quit");
    }
}