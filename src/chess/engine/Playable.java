package chess.engine;

import java.util.List;

// TODO: voir si utile ... car finalement tout pourrait etre mis dans la class Piece car elle abstraite et qu'aucune autre classe n'utilise
public interface Playable {

    //NOTE: Il faut bien évidamment partir que x & y sont des données valides.
    //      Càd dans les dimensions de la board.
    List<Move> getMoves(int x, int y);
}
