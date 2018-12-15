package quintaib;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface DatabaseServiceInterface {
    /**
     * Permette all'utente di autenticarsi
     * 
     * @param username
     * @param password
     * @return l'utente se l'autenticazione è andata a buon fine altrimenti
     *         lancia un errore
     * @throws Exception l'utente non è autenticato o ci sono problemi con il
     *                   database
     */
    @WebMethod
    public Utente login(String username, String password) throws Exception;

    /**
     * Permette all'utente di registrarsi
     * 
     * @param username
     * @param password
     * @return l'id dell'utente se l'autenticazione è andata a buon fine altrimenti
     *         lancia un errore
     * @throws Exception l'utente non è autenticato o ci sono problemi con il
     *                   database
     */
    @WebMethod
    public int registraUtente(String username, String password) throws Exception;

    /**
     * Permette di ottenere la lista di conti correnti dato un l'id di un utente
     * 
     * @param idUtente
     * @return un array di conti correnti
     * @throws Exception
     */
    @WebMethod
    public ContoCorrente[] listaContiCorrenti(int idUtente) throws Exception;

    /**
     * Permette all'utente di create un nuovo conto corrente
     * 
     * @param idUtente
     * @param descrizione una piccola descrizione del conto corrente per
     *                    identificarlo dagli altri
     * @throws Exception
     */
    @WebMethod
    public void creaContoCorrente(int idUtente, String descrizione) throws Exception;

    /**
     * Permette di oottenre la lista di operazioni rispetto a un conto corrente
     * specificato
     * 
     * @param idContoCorrente
     * @return un array di operazioni
     * @throws Exception
     */
    @WebMethod
    public Operazione[] listaOperazioni(int idContoCorrente) throws Exception;
}