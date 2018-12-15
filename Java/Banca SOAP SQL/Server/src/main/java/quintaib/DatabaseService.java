package quintaib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public Utente login(String username, String password) throws Exception {
        String query = "select * from utenti where nome = '" + username + "' and password = '" + password + "';";

        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);

        if(result.next()) {
            Utente utente = new Utente();

            utente.nome = result.getString("nome");
            utente.cognome = result.getString("cognome");
            utente.livello = result.getInt("livello");

            return(utente);
        } else {
            throw(new Exception());
        }
    }

    @Override
    public int registraUtente(String username, String password) throws Exception {
        return(-1);
    }

    @Override
    public ContoCorrente[] listaContiCorrenti(int idUtente) throws Exception {
        return(null);
    }

    @Override
    public void creaContoCorrente(int idUtente, String descrizione) throws Exception {
        //...
    }

    @Override
    public Operazione[] listaOperazioni(int idContoCorrente) throws Exception {
        return(null);
    }
}