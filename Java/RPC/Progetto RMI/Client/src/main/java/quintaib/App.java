package quintaib;

import java.awt.CardLayout;
import java.rmi.Naming;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class App extends JFrame {
    CommunicationProtocol service;
    int nUtente;
    JPanel cards;

    public static void main( String[] args ) {
        App app = new App();
    }

    public App() {        
        //provo a collegarmi al server
        try {
            service = (CommunicationProtocol) Naming.lookup("sistema-votazioni");
            System.out.println("Server collegato");
        } catch(Exception e) {
            //se non è possibile contattare il server mostro un messaggio di errore e blocco il programma
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
            closeApp();
        }

        //preparo l'interfaccia grafica

        //utilizzo un card layout per poter cambiare JPanel facilmente
        cards = new JPanel(new CardLayout());

        //aggiungo i JPanel al card layout
        cards.add(createLoginPanel(), "login");
        cards.add(createVotePanel(), "vote");

        //aggiungo il card lòayout al JFrame ovvero questa classe
        this.add(cards);

        //all'inizio del programma, quando l'utente non è ancora autenticato, mostro la pagina di login
        showLoginPanel();

        //rendo visibile l'interfaccia grafica
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Crea il JPanel di login che chiede all'utente nome e cognome per accedere alla votazione
     */
    public JPanel createLoginPanel() {
        //creo un'istanza della classe LoginPanel e imposto l'interfaccia LoginActionListenr
        LoginPanel loginPanel = new LoginPanel(new LoginActionListener(){
            @Override
            public void onLoginEvent(String nome, String cognome) {
                System.out.println("Nome: " + nome + " cognome: " + cognome);

                //provo a richiedere al server l'id dell'utente in base al nome e al cognome inseriti dall'utente
                try {
                    nUtente = service.ottieniIdUtente(nome, cognome);
                    System.out.println("Id utente: " + nUtente);

                    //controllo se l'utente è stato trovato
                    if(nUtente >= 0) {
                        //mi sposto sulla pagina per il voto
                        showVotePanel();
                    } else {
                        //altrimenti mostro un errore, l'utente dovrà evidentemente modificare i dati inseriti
                        JOptionPane.showMessageDialog(getParent(), "Utente non riconosciuto", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch(Exception e) {
                    //per qualsiasi errore di connessione mostro un errore e chiudo il programma
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                    closeApp();
                }
            }
        });

        return(loginPanel);
    }
    
    /**
     * Creal il JPanel che permetterà all'utente di votare
     */
    public JPanel createVotePanel() {
        try {
            //recupero i candidati dal server
            Candidato[] candidati = (Candidato[]) service.ottieniElencoCandidati().clone();

            //creo un'istanza della classe VotePanel e imposto l'interfaccia VoteActionListener
            VotePanel votePanel = new VotePanel(candidati, new VoteActionListener(){
                @Override
                public void onVoteAction(int nCandidato) {
                    System.out.println("Candidato votato: " + nCandidato);

                    //provo a somministrare il voto dell'utente al server
                    try {
                        service.vota(nCandidato, nUtente);
                    } catch (Exception e) {
                        //se c'è un errore con il server mostro un messaggio di errore e chiudo l'app
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                        closeApp();
                    }
                }

                @Override
                public void mostraVincitore() {
                    //provo a recuperare la classifica dei candidati
                    try {
                        Quota[] quote = service.classifica();

                        //trovo il candidato con più voti
                        int vincitore = 0, maxVoti = 0;
                        for(int i = 0; i < quote.length; i++) {
                            if(quote[i].votiCandidato > maxVoti) {
                                maxVoti = quote[i].votiCandidato;
                                vincitore = i;
                            }
                        }

                        //mostro un messaggio con il vincitore, i suoi voti e i voti totali
                        JOptionPane.showMessageDialog(getParent(),"Il vincitore è " +
                            quote[vincitore].candidato.nome + " " +
                            quote[vincitore].candidato.cognome + " con " +
                            quote[vincitore].votiCandidato + " voti su " +
                            quote[vincitore].votiTotali);
                    } catch (Exception e) {
                        //se c'è un errore con il server mostro un messaggio di errore e chiudo l'app
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                        closeApp();
                    }
                }
            });

            return(votePanel);
        } catch(Exception e) {
            //se c'è un errore con il server mostro un messaggio di errore e chiudo l'app
            e.printStackTrace();
            JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
            closeApp();
            return(null);
        }
    }

    /**
     * Mostra il JPanel per fare il login
     */
    public void showLoginPanel() {
        ((CardLayout) cards.getLayout()).show(cards, "login");
    }
    
    /**
     * Mostra il JPanel per votare
     */
    public void showVotePanel() {
        ((CardLayout) cards.getLayout()).show(cards, "vote");
    }

    /**
     * Conclude l'esecuzione del programma
     */
    void closeApp() {
        System.exit(0); //esco dal programma (senza segnalare nessun errore per non allarmare ancora di più l'utente)
    }
}
