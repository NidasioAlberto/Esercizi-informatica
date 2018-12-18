package quintaib;

public interface LoginActionListener {
    /**
     * Questa funzione verrà eseguita quando l'utente vorrà accedere
     */
    void onLoginEvent(int idUtente, String password);

    void onSigninEvent(String nome, String cognome, String password);

    void refresh();
}