package quintaib;

import java.io.Serializable;

public class ContoCorrente implements Serializable {
    int idContoCorrente;
    int idUtente;
    float bilancio;
    String descrizione;

    @Override
    public String toString() {
        return("Conto corrente " + idContoCorrente + " " + descrizione + ", bilancio: " + bilancio + ", utente: " + idUtente);
    }
}