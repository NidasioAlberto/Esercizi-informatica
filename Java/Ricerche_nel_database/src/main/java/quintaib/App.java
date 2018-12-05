package quintaib;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

public class App extends JFrame {
    GestoreConnessione gestoreConnessione;

    MainPanel mainPanel;

    public static void main(String[] args) {
        //mostro l'interfaccia
        App app = new App();
    }

    public App() {
        //preparo l'iterfaccia grafica
        mainPanel = new MainPanel(new FilterActionListener(){
            @Override
            public void applicaFiltri(String titolo, int anno, String genere) {
                System.out.println("Applica filtri:\ntitolo: " + titolo + "\nanno: " + anno + "\ngenere: " + genere);

                try {
                    Film[] film = gestoreConnessione.ottieniDati(titolo, anno, genere);

                    mainPanel.aggiornaTabella(film);
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        
            @Override
            public void annullaFiltri() {
                System.out.println("Annulla filtri");
                
                try {
                    Film[] film = gestoreConnessione.ottieniDati(null, 0, null);

                    mainPanel.aggiornaTabella(film);
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        this.add(mainPanel);
        this.pack();
        //this.setResizable(false);
        this.setVisible(true);

        //inzializzo il gestore della connessione
        gestoreConnessione = new GestoreConnessione();

        //connetto il gestore al database
        try {
            gestoreConnessione.connettiDatabase();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
