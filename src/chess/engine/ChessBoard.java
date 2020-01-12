package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;
import java.sql.SQLOutput;
import java.util.List;

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

        boolean b;
        if(playerColor == PlayerColor.WHITE){
            b = isUnderAttack(whiteKing, PlayerColor.BLACK);
            System.out.println("===========WHITE KING IN DANGER");
        }else{
            b = isUnderAttack(blackKing, PlayerColor.WHITE);
            System.out.println("===========NIGGA KING IN DANGER");
        }
        return b;
    }

    private boolean isUnderAttack(Point piece, PlayerColor opponentColor){
        for(int y = 0; y < N_COTE; ++y){
            for(int x = 0; x < N_COTE; ++x){
                Piece possibleOpponent = board[y][x];
                // Est-ce qu'il s'agit d'un adversaire (pouvant donc attaque pièce) ?
                if(possibleOpponent != null && possibleOpponent.getColor() == opponentColor){
                    List<Move> moves = possibleOpponent.getMoves(new Point(x, y), true);
                    for(Move move : moves){

                        System.out.println("MOVE " + move.getFrom() + " --> " + move.getTo() + " king @ " + piece);
                        if(move.equals(piece)){
                            System.out.println("CHECK BY "  + possibleOpponent + " @ " + move.getFrom() + " --> " + move.getTo());
                            return true;
                        }
                        //System.out.println(move.getFrom() + " -> " + move.getTo() + "looking for ->" + piece + "by --> " + possibleOpponent);
                    }
                }
            }
        }
        return false;
    }

    public boolean removePieceAt(Point pos){
        if(board[pos.y][pos.x] == null){
            return false;
        }
        board[pos.y][pos.x] = null;
        return true;
    }
    
    // todo : à supprimer
    public void display(){
        for(int j = N_COTE - 1 ; j >= 0; --j){
            StringBuilder str = new StringBuilder();
            for(int i = 0; i < N_COTE; ++i){
                if(board[j][i] == null){
                    str.append(" ");
                }else{
                    Piece p = board[j][i];
                    if(p.getColor() == PlayerColor.WHITE){
                        str.append("B");

                    }else{
                        str.append("N");

                    }
                }
                str.append("|");
            }
            System.out.println(str);
        }
    }

    public void placePieceAt(Piece piece, Point pos){
        board[pos.y][pos.x] = piece;

        // Gardons en référence la position du roi.
        if(piece.getClass() == King.class){
            if(piece.getColor() == PlayerColor.WHITE){
                whiteKing = new Point(pos);
            }else{
                blackKing = new Point(pos);
            }
        }
    }

    public boolean isCellEmpty(Point pos){
        //TOOD: redondant avec Move.Inbound !!
        if((pos.x >= 0 && pos.x < N_COTE) && (pos.y >= 0 && pos.y < N_COTE)){
            return board[pos.y][pos.x] == null;
        }
        return false;
    }

    public int getDimension(){
        return N_COTE;
    }

    public Piece getCellAt(Point pos){
        return board[pos.y][pos.x];
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
            board[side.position + deltaPlayer][i] = new Pawn(pieceColor, this);
        }

        // Rook
        board[side.position][0] = new Rook(pieceColor, this);
        board[side.position][N_COTE - 1] = new Rook(pieceColor, this);

        // Knight
        board[side.position][1] = new Knight(pieceColor, this);
        board[side.position][N_COTE - 2] = new Knight(pieceColor, this);

        // Bishop
        board[side.position][2] = new Bishop(pieceColor, this);
        board[side.position][N_COTE - 3] = new Bishop(pieceColor, this);

        // King
        board[side.position][4] = new King(pieceColor, this);
        if(player.getColor() == PlayerColor.WHITE){
            whiteKing = new Point(4, side.position);
        }else{
            blackKing = new Point(4, side.position);
        }

        // Queen
        board[side.position][3] = new Queen(pieceColor, this);
    }
}
