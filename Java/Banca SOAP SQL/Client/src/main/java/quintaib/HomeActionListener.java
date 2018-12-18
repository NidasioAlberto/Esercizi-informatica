package quintaib;

public interface HomeActionListener {
    /**
     * Permette all'utente di ricaricare la lista di conti correnti
     */
    public void refresh();

    /**
     * Permette all'utente di eseguire il logout
     */
    public void logout();
}