package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;

public class ChessBoard {
    private int N_COTE;
    private Piece board[][];
    private Move lastMove;
    Point whiteKing;
    Point blackKing;

    public ChessBoard(int nCote){
        if(nCote < 0 ){
            throw new RuntimeException("Size of the damier doit être positif.");
        }
        this.N_COTE = nCote;
        this.board = new Piece[nCote][nCote];
    }

    public boolean isCheck(PlayerColor playerColor){
        //TODO: peut-être factorisé, mais en perdant en lisibilité.

        if(playerColor == PlayerColor.WHITE){
            System.out.println("WHITE KING IS AT " + whiteKing);
            return isUnderAttack(whiteKing, PlayerColor.BLACK);
        }else{
            System.out.println("NIGGA KING IS AT " + blackKing);
            return isUnderAttack(blackKing, PlayerColor.WHITE);
        }
    }

    private boolean isUnderAttack(Point piece, PlayerColor opponentColor){
        for(int x = 0; x < N_COTE; ++x){
            for(int y = 0; y < N_COTE; ++y){
                Piece possibleOpponent = board[x][y];
                // Est-ce qu'il s'agit d'un adversaire (pouvant donc attaque pièce) ?
                if(possibleOpponent != null && possibleOpponent.getColor() == opponentColor){
                    for(Move move : possibleOpponent.getMoves(new Point(x, y), true)){
                        if(move.equals(piece)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
        // Gardons en référence la position du roi.
        if(piece.getClass() == King.class){
            if(piece.getColor() == PlayerColor.WHITE){
                whiteKing = pos;
            }else{
                blackKing = pos;
            }
        }
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
        int deltaPlayer = side == Side.BOTTOM ? 1 : -1;

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
        if(player.getColor() == PlayerColor.WHITE){
            whiteKing = new Point(3, side.position);;
        }else{
            blackKing = new Point(3, side.position);;
        }

        // Queen
        board[4][side.position] = new Queen(pieceColor, this);
    }
}
