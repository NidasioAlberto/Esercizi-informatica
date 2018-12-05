package quintaib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GestoreConnessione {
    Connection conn;

    public GestoreConnessione() {

    }

    public void connettiDatabase() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://81.88.253.179/videoteca", "utente", "utente");
    }

    public Film[] ottieniDati(String titolo, int anno, String genere) throws SQLException {
        //preparo la query
        String query = "SELECT Titolo, Anno, Durata, Nazione, Nome FROM film JOIN genere ON film.IdGenere = genere.IdGenere";

        //aggiungo i filtri se disponibili
        if(genere != null) if(!genere.isEmpty()) {
            query += " AND Nome LIKE \"%" + genere + "%\"";
        }

        if(anno > 0) {
            query += " AND Anno = " + anno;
        }

        if(titolo != null) if(!titolo.isEmpty()) {
            query += " AND Titolo LIKE \"%" + titolo + "%\"";
        }

        query += ";";

        System.out.println(query);

        //eseguo la query
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(query);

        ArrayList<Film> film = new ArrayList<>();

        while(result.next()) {
            Film tmp = new Film();

            tmp.titolo = result.getString("Titolo");
            tmp.anno = result.getInt("Anno");
            tmp.durata = result.getInt("Durata");
            tmp.nazione = result.getString("Nazione");
            tmp.genere = result.getString("Nome");

            film.add(tmp);
        }

        return(film.toArray(new Film[film.size()]));
    }
}