package quintaib;

import java.io.Serializable;
import java.sql.Date;

public class Operazione implements Serializable {
    int idOperazione;
    int idContoCorrente;
    Date data;
    float importo;
    String causale;
    boolean corretta;
    String tipoOperazione;

    @Override
    public String toString() {
        return("Operazione: " + idContoCorrente + ", conto corrente: " + idContoCorrente + ", data: " + data.toString() + ", importo: " + importo + ", causale: " + causale + ", corretta: " + corretta + ", tipo operazione: " + tipoOperazione);
    }
}