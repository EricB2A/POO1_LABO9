package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.Side;

public class Player {
    private PlayerColor color;
    //NOTE: Ok alors j'ai rajouté une référence à board depuis player pour le checkMove des pièces.
    //      A voir si c'est une solution "propre".
    private ChessGame chessGame;
    private Side side;

    public Player(PlayerColor color, Side side, ChessGame chessGame){
        if(color == null){
            throw new RuntimeException("We need a color !");
        }
        if(side == null){
            throw new RuntimeException("We need a side !");
        }
        if(chessGame == null){
            throw new RuntimeException("We need a board to play !");
        }
        this.color = color;
        this.side = side;
        this.chessGame = chessGame;
    }

    public PlayerColor getColor() {
        return color;
    }
    public ChessGame getChessGame(){
        return chessGame;
    }
    public Side getSide(){
        return side;
    }
}
