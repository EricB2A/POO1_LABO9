import chess.ChessController;
import chess.ChessView;
import chess.engine.ChessGame;
import chess.views.console.ConsoleView;
import chess.views.gui.GUIView;

class StudentChess {

  public static void main(String[] args) {

    // 1. Création du contrôleur pour gérer le jeu d'échec
    ChessController controller = new ChessGame(); // Board est une classe faite et nommée par les étudiant

    // 2. Création de la vue désirée
    ChessView view = new GUIView(controller); // GUI
    //ChessView view = new ConsoleView(controller); // ou console

    // 3. Lancement du programme.
    controller.start(view);

  }

}
