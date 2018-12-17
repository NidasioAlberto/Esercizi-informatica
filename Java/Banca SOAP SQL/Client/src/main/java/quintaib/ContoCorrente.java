package quintaib;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContoCorrente", namespace = "http://quintaib/")
public class ContoCorrente implements Serializable {
    int serialVersionUID = 123456789;

    int idContoCorrente;
    int idUtente;
    float bilancio;
    String descrizione;

    @Override
    public String toString() {
        return("Conto corrente " + idContoCorrente + " " + descrizione + ", bilancio: " + bilancio + ", utente: " + idUtente);
    }
}