package quintaib;

public interface VoteActionListener {
    /**
     * Questa funzione viene eseguita quando l'utente vuole votare un candidato
     */
    void onVoteAction(int nCandidato);

    /**
     * Questa funzione viene eseguita se l'utente vuole visualizzare il vincitore
     */
    void mostraVincitore();
}