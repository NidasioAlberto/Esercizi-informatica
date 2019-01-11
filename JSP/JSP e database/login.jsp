<%@ page import="java.sql.*"%>
<%
    //se disponibili recupero i dati
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    boolean credenzialiErrate = false;
    boolean errore = false;

    if(username != null && password != null) {
        try {
            //contatto il database per verificare le credenziali
            String url = "jdbc:mysql://217.61.60.117/AlbertoNidasio"; 
            Connection connection = DriverManager.getConnection(url, "AlbertoNidasio", "Alberto-07");

            String query = "select count(id) as isOk from utenti where nome = '" + username + "' and password = '" + password + "';";
            //out.println(query);

            //eseguo la query
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            //controllo il risultato
            result.next();
            if(result.getInt("isOk") == 1) {
                //recupero i dati dell'utente
                query = "select id, nome, cognome, livello from utenti where nome = '" + username + "' and password = '" + password + "';";
                result = statement.executeQuery(query);
                result.next();

                //salvo nella sessione i dati dell'utente
                session.setAttribute("id", result.getInt("id"));
                session.setAttribute("nome", result.getString("nome"));
                session.setAttribute("cognome", result.getString("cognome"));
                session.setAttribute("livello", result.getInt("livello"));

                //reindirizzo l'utente alla home
                response.sendRedirect("home.jsp");
            } else {
                //segno positivo il flag di credenziali errate per mostrare un messaggio
                credenzialiErrate = true;
            }
        } catch(Exception e) {
            //segno positivo il flag di errore per mostrare un messaggio
            errore = true;
        }
    }
%>
<html>
    <head>
        <title>Pagina di login</title>
    </head>
    <body>
        <div style="display: flex; justify-content: center; padding-top: 100px;">
            <form action="login.jsp" method="POST">
                <% if(credenzialiErrate || errore) { %>
                    <table style="border: 1px solid; background-color: red;">
                <% } else { %>
                    <table style="border: 1px solid; background-color: yellow;">
                <% } %>
                    <tr>
                        <td>
                            Nome
                        </td>
                        <td>
                            <input type="text" name="username">
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Password
                        </td>
                        <td>
                            <input type="password" name="password">
                        </td>
                    </tr>
                    <tr>
                        <td colspan=2>
                            <input type="submit" value="Accedi" style="width: 100%">
                        </td>
                    </tr>
                    <% if(credenzialiErrate) { %>
                        <tr>
                            <td colspan=2 style="background-color: red; text-align: center;">
                                Credenziali sbagliate
                            </td>
                        </tr>
                    <% } %>
                    <% if(errore) { %>
                        <tr>
                            <td colspan=2 style="text-align: center;">
                                Errore durante l'accesso al database
                            </td>
                        </tr>
                    <% } %>
                </table>
            </form>
        </div>
    </body>
</html>