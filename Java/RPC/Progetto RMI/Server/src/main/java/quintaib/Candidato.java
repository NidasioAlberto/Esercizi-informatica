package quintaib;

import java.io.Serializable;

/**
 * Contiene i dati di un singolo candidato, per ora sono nome, cognome e id.
 */
public class Candidato implements Serializable {
    public String nome;
    public String cognome;
    public int id;
}