<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>
<%
    int id = -1;
    String nome = null;
    String cognome = null;
    int livello = -1;

    ArrayList<String> nomi = new ArrayList();
    ArrayList<String> cognomi = new ArrayList();
    ArrayList<Integer> livelli = new ArrayList();
    
    try {
        //recupero le informazioni dalla sessione
        id = (int) session.getAttribute("id");
        nome = (String) session.getAttribute("nome");
        cognome = (String) session.getAttribute("cognome");
        livello = (int) session.getAttribute("livello");

        //controllo se i dati dell'utente sono presenti
        if(!(id >= 0 && nome != null && cognome != null && (livello > 0 && livello <= 3))) {
            throw(new Exception());
        } else {
            //controllo il livello dell'utente
            if(livello < 2) {
                throw(new Exception());
            } else {
                //preparo la connessione
                //contatto il database per verificare le credenziali
                String url = "jdbc:mysql://217.61.60.117/AlbertoNidasio"; 
                Connection connection = DriverManager.getConnection(url, "AlbertoNidasio", "Alberto-07");

                String query = "select nome, cognome, livello from utenti";
                //out.println(query);

                //eseguo la query
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(query);
                
                //salvo i dati
                while(result.next()) {
                    nomi.add(result.getString("nome"));
                    cognomi.add(result.getString("cognome"));
                    livelli.add(result.getInt("livello"));
                }
            }
        }
    } catch(Exception e) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
    <head>
        <title>Lista utenti</title>
        <script type="text/javascript" src="javascript.js" ></script>
    </head>
    <body>
        <div style="display: flex; justify-content: center; padding-top: 100px;">
            <% if(livello == 2) { %>
                <table style="border: 1px solid; background-color: green;">
            <% } else { %>
                <table style="border: 1px solid; background-color: orange;">
            <% } %>
                <tr>
                    <td>
                        Nome
                    </td>
                    <td>
                        Cognome
                    </td>
                    <td>
                        Livello
                    </td>
                </tr>
                <% for(int i = 0; i < nomi.size(); i++) { %>
                    <tr>
                        <td>
                            <%= nomi.get(i) %>
                        </td>
                        <td>
                            <%= cognomi.get(i) %>
                        </td>
                        <td>
                            <%= livelli.get(i) %>
                        </td>
                    </tr>
                <% } %>
                <% if(livello == 3) { %>
                    <tr>
                        <td colspan=3>
                            <button style="width: 100%;" onclick="aggiungiUtente()">Aggiungi utente</button>
                        </td>
                    </tr>
                <% } %>
                <tr>
                    <td colspan=3>
                        <button style="width: 100%;" onclick="tornaAllaHome()">Torna alla home</button>
                    </td>
                </tr>
                <tr>
                    <td colspan=3>
                        <button style="width: 100%;" onclick="logout()">Logout</button>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>