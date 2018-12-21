<%@ page import="java.sql.*"%>
<%
    boolean errore = false;

    int id = -1;
    String nome = null;
    String cognome = null;
    int livello = -1;
    
    try {
        //recupero le informazioni dalla sessione
        id = (int) session.getAttribute("id");
        nome = (String) session.getAttribute("nome");
        cognome = (String) session.getAttribute("cognome");
        livello = (int) session.getAttribute("livello");

        //controllo se i dati dell'utente sono presenti
        if(!(id >= 0 && nome != null && cognome != null && (livello == 3))) {
            throw(new Exception());
        }
    } catch(Exception e) {
        response.sendRedirect("login.jsp");
    }

    //se disponibili recuper i dati del nuovo utente
    String n_nome = (String) request.getParameter("username");
    String n_cognome = (String) request.getParameter("cognome"); 
    String n_livello = (String) request.getParameter("livello");
    String n_password = (String) request.getParameter("password");

    if(n_nome != null && n_cognome != null && n_cognome != null && n_livello != null) {
        //aggiungo l'utente nel database
        try {
            //contatto il database per verificare le credenziali
            String url = "jdbc:mysql://217.61.60.117/AlbertoNidasio"; 
            Connection connection = DriverManager.getConnection(url, "AlbertoNidasio", "Alberto-07");

            String query = "insert into utenti(nome, cognome, livello, password) values('" + n_nome + "', '" + n_cognome + "', '" + n_livello + "', '" + n_password + "');";
            //out.println(query);

            //eseguo la query
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            //se nessuna eccezione è stata lanciata allora l'elemento è stato aggiunto
            response.sendRedirect("lista.jsp");
        } catch(Exception e) {
            //segno positivo il flag di errore per mostrare un messaggio
            errore = true;
        }
    }
%>

<html>
    <head>
        <title>Aggiungi utente</title>
        <script type="text/javascript" src="javascript.js" ></script>
    </head>
    <body>
        <div style="display: flex; justify-content: center; padding-top: 100px;">
            <form action="aggiungi utente.jsp" method="POST">
                <% if(errore) { %>
                    <table style="border: 1px solid; background-color: red;">
                <% } else { %>
                    <table style="border: 1px solid; background-color: orange;">
                <% } %>
                    <tr>
                        <td>
                            Nome
                        </td>
                        <td>
                            <input type="text" name="username" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Cognome
                        </td>
                        <td>
                            <input type="text" name="cognome" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Livello
                        </td>
                        <td>
                            <input type="number" min="1" max="3" name="livello" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password
                        </td>
                        <td>
                            <input name="password" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;">
                            <input type="submit" value="Aggiungi utente" style="width: 100%">
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>
                            <button style="width: 100%;" onclick="visualizzaUtenti()">Visualizza utenti</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>
                            <button style="width: 100%;" onclick="tornaAllaHome()">Torna alla pagina home</button>
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>
                            <button style="width: 100%;" onclick="logout()">Logout</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>