package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.*;

import java.awt.Point;

public class ChessBoard {
    private static int N_COTE = 8;
    private Piece board[][];
    private Move lastMove;
    ChessGame chessGame;
    Point whiteKing;
    Point blackKing;
    private String checkTextMessage = "Echec";

    public ChessBoard(ChessGame chessGame){
        if(chessGame == null){
            throw new RuntimeException("We need une partie to play.");
        }
        this.board = new Piece[N_COTE][N_COTE];
        this.chessGame = chessGame;
    }

    public boolean isCheck(PlayerColor playerColor){
        if(playerColor == PlayerColor.WHITE){
            return isUnderAttack(whiteKing, PlayerColor.BLACK);
        }else{
            return isUnderAttack(blackKing, PlayerColor.WHITE);
        }
    }

    public boolean isUnderAttack(Point piece, PlayerColor opponentColor){
        for(int y = 0; y < N_COTE; ++y){
            for(int x = 0; x < N_COTE; ++x){
                Piece possibleOpponent = board[y][x];
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
        if(board[pos.y][pos.x] == null){
            return false;
        }
        board[pos.y][pos.x] = null;
        return true;
    }
    
    // TODO : à supprimer
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
        if(isCheck(piece.getColor() == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE)){
            chessGame.displayMessage(checkTextMessage);
        }else{
            chessGame.displayMessage("");
        }
    }

    public boolean isCellEmpty(Point pos){
        if(Move.inBound(pos, N_COTE)){
            return board[pos.y][pos.x] == null;
        }
        return false;
    }

    public static int getDimension(){
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
