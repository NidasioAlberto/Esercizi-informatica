package quintaib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.jws.WebService;

@WebService(endpointInterface = "quintaib.DatabaseServiceInterface")
public class DatabaseService implements DatabaseServiceInterface {
    Connection conn;

    public DatabaseService(String ip, String database, String username, String password) {
        //inizializzo la connessione al database
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, username, password);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Utente login(int id, String password) throws Exception {
        System.out.println("Richiesta di login");

        String query = "select * from utenti where id = " + id + " and password = '" + password + "';";

        System.out.println(query);

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            Utente utente = new Utente();

            result.next();

            utente.idUtente = id;
            utente.nome = result.getString("nome");
            utente.cognome = result.getString("cognome");
            utente.livello = result.getInt("livello");

            System.out.println(utente.toString());

            return(utente);
        } catch(Exception e) {
            e.printStackTrace();
            throw(new Exception());
        }
    }

    @Override
    public int registraUtente(String nome, String congnome, String password) throws Exception {
        System.out.println("Richiesta registrazione utente");
        
        //per ora si possono registrare solo utenti di livello 1
        String query = "insert into utenti(nome, cognome, password) values('" + nome + "', '" + congnome + "', '" + password + "')";

        try {
            Statement statement = conn.createStatement();
            //creo il nuovo utente
            statement.executeUpdate(query);

            //query = "select id from utenti where nome = '' and cognome = '' and password = ''"
            query = "select @@IDENTITY as id;";

            //recupero il suo id
            ResultSet result = statement.executeQuery(query);
            result.next();
            int id = result.getInt("id");

            System.out.println("Id: " + id);

            return(id);
        } catch(Exception e) {
            e.printStackTrace();
            throw(new Exception());
        }
    }

    @Override
    public ContoCorrente[] listaContiCorrenti(int idUtente) throws Exception {
        System.out.println("Richiesta conti correnti");

        //preparo la query per recuperare i conti correnti
        String query = "select * from contiCorrenti where idUtente = " + idUtente + ";";

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            ArrayList<ContoCorrente> contiCorrenti = new ArrayList<>();

            while(result.next()) {
                ContoCorrente contoCorrente = new ContoCorrente();

                contoCorrente.idContoCorrente = result.getInt("idContoCorrente");
                contoCorrente.idUtente = idUtente;
                contoCorrente.bilancio = result.getFloat("bilancio");
                contoCorrente.descrizione = result.getString("descrizione");

                contiCorrenti.add(contoCorrente);

                System.out.println(contoCorrente.toString());
            }

            return(contiCorrenti.toArray(new ContoCorrente[contiCorrenti.size()]));
        } catch(Exception e) {
            e.printStackTrace();
            throw(new Exception());
        }
    }

    @Override
    public void creaContoCorrente(int idUtente, String descrizione) throws Exception {
        System.out.println("Richiesta creazione conto corrente");

        //preparo la query
        String query = "insert into contiCorrenti(idUtente, descrizione) values(" + idUtente + ", '" + descrizione + "')";

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch(Exception e) {
            e.printStackTrace();
            throw(new Exception());
        }
    }

    @Override
    public Operazione[] listaOperazioni(int idContoCorrente) throws Exception {
        System.out.println("Richiesta lista operazioni");

        //preparo la query
        String query = "select idOperazione, idContoCorrente, data, importo, causale, corretta, nome tipoOperazione from operazioni join tipiOperazioni on operazioni.idTipoOperazione = tipiOperazioni.id where idContoCorrente = " + idContoCorrente + ";";

        System.out.println(query);

        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            ArrayList<Operazione> operazioni = new ArrayList<>();

            while(result.next()) {
                Operazione operazione = new Operazione();

                operazione.idOperazione = result.getInt("idOperazione");
                operazione.idContoCorrente = idContoCorrente;
                operazione.data = result.getDate("data").toString();
                operazione.importo = result.getFloat("importo");
                operazione.causale = result.getString("causale");
                operazione.corretta = result.getBoolean("corretta");
                operazione.tipoOperazione = result.getString("tipoOperazione");

                operazioni.add(operazione);

                System.out.println(operazione.toString());
            }

            return(operazioni.toArray(new Operazione[operazioni.size()]));
        } catch(Exception e) {
            e.printStackTrace();
            throw(new Exception());
        }
    }
}