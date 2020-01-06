package chess.engine;

import chess.PlayerColor;
import chess.engine.pieces.Side;

public class Player {
    private PlayerColor color;
    //NOTE: Ok alors j'ai rajouté une référence à board depuis player pour le checkMove des pièces.
    //      A voir si c'est une solution "propre".
    private Board board;
    private Side side;

    public Player(PlayerColor color, Side side, Board board){
        if(color == null){
            throw new RuntimeException("We need a color !");
        }
        if(side == null){
            throw new RuntimeException("We need a side !");
        }
        if(board == null){
            throw new RuntimeException("We need a board to play !");
        }
        this.color = color;
        this.side = side;
        this.board = board;
    }

    public PlayerColor getColor() {
        return color;
    }
    public Board getBoard(){
        return board;
    }
    public Side getSide(){
        return side;
    }
}
