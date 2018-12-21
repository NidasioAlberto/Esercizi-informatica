<%
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
        if(!(id >= 0 && nome != null && cognome != null && (livello > 0 && livello <= 3))) {
            throw(new Exception());
        }
    } catch(Exception e) {
        response.sendRedirect("login.jsp");
    }
%>

<html>
    <head>
        <title>Pagina home</title>
        <script type="text/javascript" src="javascript.js" ></script>
    </head>
    <body>
        <div style="display: flex; justify-content: center; padding-top: 100px;">
            <% if(livello == 1) { %>
                <table style="border: 1px solid;">
            <% } else if(livello == 2) { %>
                <table style="border: 1px solid; background-color: green;">
            <% } else { %>
                <table style="border: 1px solid; background-color: orange;">
            <% } %>
            <tr>
                <td>
                    <%= nome %>
                </td>
                <td>
                    <%= cognome %>
                </td>
            </tr>
            <tr>
                <td>
                    Livello
                </td>
                <td>
                    <%= livello %>
                </td>
            </tr>
            <% if(livello == 3) { %>
                <tr>
                    <td colspan=2>
                        <button style="width: 100%;" onclick="aggiungiUtente()">Aggiungi utente</button>
                    </td>
                </tr>
            <% } %>
            <% if(livello >= 2) { %>
                <tr>
                    <td colspan=2>
                        <button style="width: 100%;" onclick="visualizzaUtenti()">Visualizza utenti</button>
                    </td>
                </tr>
            <% } %>
            <tr>
                <td colspan=2>
                    <button style="width: 100%;" onclick="logout()">Logout</button>
                </td>
            </tr>
            </table>
        </div>
    </body>
</html>