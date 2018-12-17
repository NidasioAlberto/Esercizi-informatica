package quintaib;

import java.io.Serializable;
import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Operazione", namespace = "http://quintaib/")
public class Operazione implements Serializable {
    int serialVersionUID = 123456789;

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