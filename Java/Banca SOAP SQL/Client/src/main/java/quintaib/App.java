package quintaib;

import java.awt.CardLayout;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class App extends JFrame {
    DatabaseServiceInterface databaseService;

    LoginPanel loginPanel;
    HomePanel homePanel;

    JPanel cards;

    //user data
    Utente utente;

    public static void main( String[] args ) throws Exception {
        App app = new App();
    }

    public App() throws Exception {
        //collegamento al server
        URL wsdlURL = new URL("http://localhost:8888/database?wsdl");
		QName qname = new QName("http://quintaib/", "DatabaseServiceService"); 
		
        Service service = Service.create(wsdlURL, qname);
        
        databaseService = service.getPort(DatabaseServiceInterface.class);

        cards = new JPanel(new CardLayout());

        loginPanel = creaLoginPanel();
        cards.add(loginPanel, "login");
        homePanel = creaHomePanel();
        cards.add(homePanel, "home");
        mostraLoginPanel();

        this.add(cards);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    public LoginPanel creaLoginPanel() {
        //preparo i panel
        return(new LoginPanel(new LoginActionListener() {
            @Override
            public void onLoginEvent(int idUtente, String password) {
                System.out.println("Id: " + idUtente + ", password: " + password);

                //eseguo il login
                try {
                    utente = databaseService.login(idUtente, password);

                    System.out.println("Login completato");

                    //mostro il pannello home
                    mostraHomePanel();
                } catch(Exception e) {
                    loginPanel.mostraAvviso("Credenziali errate");
                }
            }

            @Override
            public void onSigninEvent(String nome, String cognome, String password) {
                System.out.println("Nome: " + nome + ", cognome: " + cognome + ", password: " + password);
            }

            @Override
            public void refresh() {
                pack();
            }
        }));
    }

    public HomePanel creaHomePanel() {
        return(new HomePanel(new HomeActionListener(){
            @Override
            public void refresh() {
                try {
                    //recupero i dati
                    ContoCorrente[] contiCorrenti = databaseService.listaContiCorrenti(utente.idUtente);

                    //mostro i dati nella tabella
                    homePanel.aggiornaTabella(contiCorrenti);
                } catch(Exception e) {
                    e.printStackTrace();
                    homePanel.mostraAvviso("Errore durante il recupero dei conti correnti");
                }
            }
        
            @Override
            public void logout() {
                //mostro il login panel
                mostraLoginPanel();
            }
        }));
    }

    public void mostraLoginPanel() {
        ((CardLayout) cards.getLayout()).show(cards, "login");
        pack();
    }

    public void mostraHomePanel() {
        ((CardLayout) cards.getLayout()).show(cards, "home");

        try {
            //recupero i dati
            ContoCorrente[] contiCorrenti = databaseService.listaContiCorrenti(utente.idUtente);

            //mostro i dati nella tabella
            homePanel.aggiornaTabella(contiCorrenti);
        } catch(Exception e) {
            e.printStackTrace();
            homePanel.mostraAvviso("Errore durante il recupero dei conti correnti");
        }

        pack();
    }
}
