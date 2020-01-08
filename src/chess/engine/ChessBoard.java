package chess.engine;

import chess.ChessView;
import chess.engine.pieces.*;

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

    public boolean removePieceAt(int posX, int posY){
        System.out.println("Removing piece...");
        System.out.println(board[posX][posY] == null);
        
        if(board[posX][posY] == null){
            return false;
        }
        board[posX][posY] = null;
        return true;
    }

    public void placePieceAt(Piece piece, int posX, int posY){
        System.out.println("Moving piece...");
        board[posX][posY] = piece;
    }

    public boolean isCellEmpty(int posX, int posY){
        return board[posX][posY] == null;
    }

    public int getDimension(){
        return N_COTE;
    }

    public Piece getCellAt(int posX, int posY){
        return board[posX][posY];
    }

    public Move getLastMove(){
        return lastMove;
    }
    
    protected void setLastMove(Move move){
        this.lastMove = move;
    }

    protected void setUpTeam(Player player){
        // Par soucis de lisibilité.
        Side side = player.getSide();

        // Nous permet de décaler les pions d'une rangée vers le centre de l'échiquier.
        int deltaPlayer = side == Side.TOP ? 1 : -1;
        // Pawn
        for(int i = 0; i < N_COTE; ++i){
            board[i][side.position + deltaPlayer] = new Pawn(player, this);
        }

        // Rook
        board[0][side.position] = new Rook(player, this);
        board[N_COTE - 1][side.position] = new Rook(player, this);

        // Knight
        board[1][side.position] = new Knight(player, this);
        board[N_COTE - 2][side.position] = new Knight(player, this);

        // Bishop
        board[2][side.position] = new Bishop(player, this);
        board[N_COTE - 3][side.position] = new Bishop(player, this);

        // King
        board[3][side.position] = new King(player, this);

        // Queen
        board[4][side.position] = new Queen(player, this);
    }
}
