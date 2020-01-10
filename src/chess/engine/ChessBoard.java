package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;

public class ChessBoard {
    private int N_COTE;
    private Piece board[][];
    private Move lastMove;

    public ChessBoard(int nCote){
        if(nCote < 0 ){
            throw new RuntimeException("Size of the damier doit être positif.");
        }
        this.N_COTE = nCote;
        this.board = new Piece[nCote][nCote];
    }

    public boolean removePieceAt(Point pos){
        if(board[pos.x][pos.y] == null){
            return false;
        }
        board[pos.x][pos.y] = null;
        return true;
    }
    // todo : à supprimer
    public void display(){
        for(int i = 0 ; i < N_COTE; ++i){
            StringBuilder str = new StringBuilder();
            for(int j = 0; j < N_COTE; ++j){
                if(board[j][i] == null){
                    str.append(" ");
                }else{
                    str.append("X");
                }
                str.append("|");
            }
            System.out.println(str);
        }
    }

    public void placePieceAt(Piece piece, Point pos){
        System.out.println("Moving piece...");
        board[pos.x][pos.y] = piece;
    }

    public boolean isCellEmpty(Point pos){
        return board[pos.x][pos.y] == null;
    }

    public int getDimension(){
        return N_COTE;
    }

    public Piece getCellAt(Point pos){
        return board[pos.x][pos.y];
    }

    public Move getLastMove(){
        return lastMove;
    }
    
    protected void setLastMove(Move move){
        this.lastMove = move;
    }

    protected void setUpTeam(Player player, Side side){
        PieceColor pieceColor = new PieceColor(player.getColor(), side);

        // Nous permet de décaler les pions d'une rangée vers le centre de l'échiquier.
        int deltaPlayer = side == Side.TOP ? 1 : -1;

        // Pawn
        for(int i = 0; i < N_COTE; ++i){
            board[i][side.position + deltaPlayer] = new Pawn(pieceColor, this);
        }

        // Rook
        board[0][side.position] = new Rook(pieceColor, this);
        board[N_COTE - 1][side.position] = new Rook(pieceColor, this);

        // Knight
        board[1][side.position] = new Knight(pieceColor, this);
        board[N_COTE - 2][side.position] = new Knight(pieceColor, this);

        // Bishop
        board[2][side.position] = new Bishop(pieceColor, this);
        board[N_COTE - 3][side.position] = new Bishop(pieceColor, this);

        // King
        board[3][side.position] = new King(pieceColor, this);

        // Queen
        board[4][side.position] = new Queen(pieceColor, this);
    }
}
