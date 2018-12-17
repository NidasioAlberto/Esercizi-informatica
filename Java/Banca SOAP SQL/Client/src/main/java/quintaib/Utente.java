package quintaib;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Utente", namespace = "http://quintaib/")
public class Utente implements Serializable {
    String nome;
    String cognome;
    int livello;

    @Override
    public String toString() {
        return("Nome: " + nome + ", cognome: " + cognome + ", livello: " + livello);
    }
}