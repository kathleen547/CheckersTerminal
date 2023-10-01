package model;

import java.util.Arrays;
import java.util.stream.Stream;

public class Board {

    public enum Piece {
        WhiteRegular,
        BlackRegular,
        WhiteQueen,
        BlackQueen,
        Empty,
    }

    Piece [][] pieces;

    public Board(Piece[][] pcs) {
        pieces = pcs;
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces[i].length; j++){
                if(j % 2 == i % 2 && i <= 2){
                    pieces[i][j] = Piece.BlackRegular;
                }
                else if(j % 2 == i % 2 && i >= 5) {
                    pieces[i][j] = Piece.WhiteRegular;
                }
                else {
                    pieces[i][j] = Piece.Empty;
                }
            }
        }
    }

    public boolean makeMoveIfValid(int srcX, int srcY, int tgtX, int tgtY, boolean whiteMoves) {
        if(isMoveValid(srcX, srcY, tgtX, tgtY, whiteMoves)){
            updateTarget(srcX, srcY, tgtX, tgtY);
            makingSourceEmpty(srcX, srcY);
            makingAllTilesBetweenEmpty(srcX, srcY, tgtX, tgtY);
            return true;
        }
        return false;
    }

    public boolean checkContainsWhitePiece() {
        return Stream.of(pieces)
                .flatMap(Arrays::stream)
                .anyMatch(p -> p == Piece.WhiteRegular || p == Piece.WhiteQueen);
    }

    public boolean checkContainsBlackPiece() {
        return Stream.of(pieces)
                .flatMap(Arrays::stream)
                .anyMatch(p -> p == Piece.BlackRegular || p == Piece.BlackQueen);
    }

    boolean isMoveValid(int srcX, int srcY, int tgtX, int tgtY, boolean whiteMoves) {
        if (!checkSourcePiece(srcX, srcY, whiteMoves))
            return false;
        if (!checkTargetTile(tgtX, tgtY))
            return false;
        if(pieces[srcX][srcY] == Piece.WhiteQueen || pieces[srcX][srcY] == Piece.BlackQueen){
            return checkProperQueenMove(srcX, srcY, tgtX, tgtY, whiteMoves);
        }
        else{
            return  checkProperRegularMove(srcX, srcY, tgtX, tgtY, whiteMoves);
        }
    }

    boolean checkSourcePiece(int srcX, int srcY, boolean whiteMoves){
        if(whiteMoves && (pieces[srcX][srcY] == Piece.WhiteRegular || pieces[srcX][srcY] == Piece.WhiteQueen))
            return true;
        else if(!whiteMoves && (pieces[srcX][srcY] == Piece.BlackRegular || pieces[srcX][srcY] == Piece.BlackQueen))
            return true;
        return false;
    }

    boolean checkTargetTile(int tgtX, int tgtY){
        return pieces[tgtX][tgtY] == Piece.Empty && tgtX % 2 == tgtY % 2;
    }

    boolean checkProperRegularMove(int srcX, int srcY, int tgtX, int tgtY, boolean whiteMoves){
        if (Math.abs(srcX - tgtX) == 2 && Math.abs(srcY - tgtY) == 2){
            int middleX = (srcX + tgtX) / 2;
            int middleY = (srcY + tgtY) / 2;
            if (whiteMoves && (pieces[middleX][middleY] == Piece.BlackRegular || pieces[middleX][middleY] == Piece.BlackQueen))
                return true;
            if (!whiteMoves && (pieces[middleX][middleY] == Piece.WhiteRegular || pieces[middleX][middleY] == Piece.WhiteQueen))
                return true;
        }
        if (whiteMoves && srcX - 1 == tgtX && (srcY - 1 == tgtY || srcY + 1 == tgtY)){
            return true;
        } else if(!whiteMoves && srcX + 1 == tgtX && (srcY + 1 == tgtY || srcY - 1 == tgtY))
            return true;
        return false;
    }

    boolean simplestMoveForward(int srcX, int srcY, int tgtX, int tgtY, boolean whiteMoves){
        if(!whiteMoves && srcX + 1 == tgtX && (srcY + 1 == tgtY || srcY - 1 == tgtY)){
            return true;
        }
        else if(whiteMoves && srcX - 1 == tgtX && (srcY + 1 == tgtY || srcY - 1 == tgtY)) {
            return true;
        }
        return false;
    }

    boolean checkProperQueenMove(int srcX, int srcY, int tgtX, int tgtY, boolean whiteMoves){
        int opponent = 0;
        if(Math.abs(srcX - tgtX) != Math.abs(srcY - tgtY))
            return false;
        if(srcX > tgtX && srcY > tgtY){
            for(int i = srcX - 1, j = srcY - 1; i >= tgtX + 1; i--, j--){
                if (whiteMoves && (pieces[i][j] == Piece.BlackRegular || pieces[i][j] == Piece.BlackQueen)) {
                    opponent++;
                }
                else if (!whiteMoves && (pieces[i][j] == Piece.WhiteRegular || pieces[i][j] == Piece.WhiteQueen)){
                    opponent++;
                }
                else if (pieces[i][j] != Piece.Empty) return false;
            }
        }
        else if(srcX > tgtX && srcY < tgtY){
            for(int i = srcX - 1, j = srcY + 1; i >= tgtX + 1; i--, j++){
                if (whiteMoves && (pieces[i][j] == Piece.BlackRegular || pieces[i][j] == Piece.BlackQueen)) {
                    opponent++;
                }
                else if (!whiteMoves && (pieces[i][j] == Piece.WhiteRegular || pieces[i][j] == Piece.WhiteQueen)){
                    opponent++;
                }
                else if (pieces[i][j] != Piece.Empty) return false;
            }
            }

        else if(srcX < tgtX && srcY < tgtY){
            for(int i = srcX + 1, j = srcY + 1; i <= tgtX - 1 ; i++, j++){
                if (whiteMoves && (pieces[i][j] == Piece.BlackRegular || pieces[i][j] == Piece.BlackQueen)) {
                    opponent++;
                }
                else if (!whiteMoves && (pieces[i][j] == Piece.WhiteRegular || pieces[i][j] == Piece.WhiteQueen)){
                    opponent++;
                }
                else if (pieces[i][j] != Piece.Empty) return false;
            }
        } else{
            for(int i = srcX + 1, j = srcY - 1; i <= tgtX - 1; i++, j--){
                if (whiteMoves && (pieces[i][j] == Piece.BlackRegular || pieces[i][j] == Piece.BlackQueen)) {
                    opponent++;
                }
                else if (!whiteMoves && (pieces[i][j] == Piece.WhiteRegular || pieces[i][j] == Piece.WhiteQueen)){
                    opponent++;
                }
                else if (pieces[i][j] != Piece.Empty) return false;
            }
        }
        return !(opponent > 1);
   }

   void updateTarget(int srcX, int srcY, int tgtX, int tgtY){
        pieces[tgtX][tgtY] = pieces[srcX][srcY];
   }

   void makingSourceEmpty(int srcX, int srcY){
        pieces[srcX][srcY] = Piece.Empty;
   }

   void makingAllTilesBetweenEmpty(int srcX, int srcY, int tgtX, int tgtY){
       if(srcX > tgtX && srcY > tgtY){
           for(int i = srcX - 1, j = srcY - 1; i >= tgtX + 1; i--, j--){
               pieces[i][j] = Piece.Empty;
           }
       }
       else if(srcX > tgtX && srcY < tgtY){
           for(int i = srcX - 1, j = srcY + 1; i >= tgtX + 1; i--, j++){
               pieces[i][j] = Piece.Empty;
           }
       }
       else if(srcX < tgtX && srcY < tgtY){
           for(int i = srcX + 1, j = srcY + 1; i <= tgtX - 1 ; i++, j++){
               pieces[i][j] = Piece.Empty;
           }
       }
       else{
           for(int i = srcX + 1, j = srcY - 1; i <= tgtX - 1; i++, j--){
               pieces[i][j] = Piece.Empty;
           }
       }
   }


}
