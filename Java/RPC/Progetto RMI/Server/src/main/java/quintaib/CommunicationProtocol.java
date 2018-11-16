package quintaib;

import java.rmi.Remote;

public interface CommunicationProtocol extends Remote {
    /**
     * Questo metodo permette al client di votare un candidato. I dati da fornire sono il numero del candidato (ho preferito mettere un numero per semplificare il sistema di conteggio, questo potrebbe essere utile per implementare un databsse sql dove i candidati hanno un id univoco) e un numero che identifica l'utente che sta votando (stesso discorso). Può esserci un'eccezione se il candidato o l'utente non esistono e se l'utente ha già votato.
     */
    public void vota(int nCandidato, int nUtente) throws Exception;

    /**
     * Questo metodo permette di ottenere la quota di voti di un singolo candidato. L'oggetto restituito dalla funizone è un'istanza della classe Quota (vedi sotto). Può esserci un'eccezione se il candidato non esiste.
     */
    public Quota[] classifica() throws Exception;

    /**
     * Permette di ottenere tutti i candidati che è possibile votare. I dati restituiti sono un array di oggetti Candidato (vedi sotto).
     */
    public Candidato[] ottieniElencoCandidati() throws Exception;
    
    /**
     * Permette di ottenere l'id utente da utilizzare nel metodo vota. Per semplificare il programma in caso l'utente non sia presente verrà creato e aggiunto nella lista di utenti e non è previsto un sistema di accesso tramite password, sarà un miglioramento futuro.
     */
    public int ottieniIdUtente(String nome, String cognome) throws Exception;
}