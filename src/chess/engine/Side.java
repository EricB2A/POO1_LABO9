package chess.engine;

public enum Side {
    TOP(7), // Première rangée. On commence l'indexation à 0 car nous ne sommes pas de animaux.
    //TODO: trouver un moyen pour pouvoir utiliser "N_COTE - 1".
    BOTTOM(0); // Dernière rangée.

    Side(int i) {
        this.position = i;
    }
    public int position;
}