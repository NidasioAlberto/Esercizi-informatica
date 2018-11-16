package quintaib;

public interface LoginActionListener {
    /**
     * Questa funzione verrà eseguita quando l'utente vorrà accedere
     */
    void onLoginEvent(String nome, String cognome);
}