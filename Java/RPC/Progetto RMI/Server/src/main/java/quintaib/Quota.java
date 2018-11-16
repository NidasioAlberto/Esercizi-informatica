package quintaib;

import java.io.Serializable;

/**
 * Contiene il candidato interessato, il numero dei suoi voti e il numero di voti totali.
 */
public class Quota implements Serializable {
    public Candidato candidato;
    public int votiTotali;
    public int votiCandidato;
}