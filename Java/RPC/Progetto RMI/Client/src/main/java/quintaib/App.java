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
        super();
        
        //mi collego al server
        try {
            service = (CommunicationProtocol) Naming.lookup("sistema-votazioni");

            System.out.println("Server collegato");
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
            closeApp();
        }

        cards = new JPanel(new CardLayout());

        cards.add(createLoginPanel(), "login");
        cards.add(createVotePanel(), "vote");

        this.add(cards);

        showLoginPanel();

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Crea il Panel di login che chiede all'utente nome e cognome per accedere alla votazione
     */
    public JPanel createLoginPanel() {
        LoginPanel loginPanel = new LoginPanel(new LoginActionListener(){
            @Override
            public void onLoginEvent(String nome, String cognome) {
                System.out.println("Nome: " + nome + " cognome: " + cognome);

                try {
                    nUtente = service.ottieniIdUtente(nome, cognome);

                    System.out.println("Id utente: " + nUtente);

                    //controllo se l'utente è stato trovato
                    if(nUtente >= 0) {
                        //mi sposto sulla pagina per il voto
                        showVotePanel();
                    } else {
                        //altrimenti mostro un errore
                        JOptionPane.showMessageDialog(getParent(), "Utente non riconosciuto", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                    closeApp();
                }
            }
        });

        return(loginPanel);
    }
    
    public void showLoginPanel() {
        ((CardLayout) cards.getLayout()).show(cards, "login");
    }
    
    public void showVotePanel() {
        ((CardLayout) cards.getLayout()).show(cards, "vote");
    }

    public JPanel createVotePanel() {
        try {
            //System.out.println(((Candidato[]) service.ottieniElencoCandidati()).length);
            Candidato[] candidati = (Candidato[]) service.ottieniElencoCandidati().clone();

            VotePanel votePanel = new VotePanel(candidati, new VoteActionListener(){
                @Override
                public void onVoteAction(int nCandidato) {
                    System.out.println("Candidato votato: " + nCandidato);

                    //voto
                    try {
                        service.vota(nCandidato, nUtente);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                        closeApp();
                    }
                }

                @Override
                public void mostraVincitore() {
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

                        //mostro un messaggio
                        JOptionPane.showMessageDialog(getParent(), "Il vincitore è " +
                        quote[vincitore].candidato.nome + " " +
                        quote[vincitore].candidato.cognome + " con " +
                        quote[vincitore].votiCandidato + " voti su " +
                        quote[vincitore].votiTotali);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
                        closeApp();
                    }
                }
            });

            return(votePanel);
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(getParent(), "Impossibile collegarsi al server", "Warning", JOptionPane.WARNING_MESSAGE);
            closeApp();
            return(null);
        }
    }

    void closeApp() {
        System.exit(0); //esco dal programma (senza segnalare nessun errore per non allarmare ancora di più l'utente)
    }
}
