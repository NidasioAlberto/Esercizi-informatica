<%@ page language="Java" %>
<%@ page import="java.sql.*"%>
<!-- Mostra header -->
<%!
    public void mostraTitolo(JspWriter out, String nome) {
        try {
            //apertura titolo
            out.print("<div class=\"row justify-content-center title\">");

            //titolo
            out.print("<h1>Editor database - <span class=\"badge badge-primary\">" + nome + "</span></h1>");

            //chiusura titolo
            out.print("</div>");
        } catch(Exception e) {
            //Error !
        }
    }
%>
<!-- Mostra tabelle -->
<%!
    public void mostraTabelle(JspWriter out, String[] nomi) {
        try {
            //lista
            out.print("<div class=\"col-md-auto\"><div class=\"list-group\" id=\"list-tab\" role=\"tablist\">");

            //contenuto
            for(int i = 0; i < nomi.length; i++) {
                out.print("<a href=\"?table=" + i +"\" class=\"list-group-item list-group-item-action\">" + nomi[i] + "</a>");
            }

            //chiusura list
            out.print("</div></div>");
        } catch(Exception e) {
            //Error !
        }
    }
%>
<!-- Mostra interfaccia -->
<%!
    public void mostraInterfaccia(JspWriter out, String[][] colonne, String query) {
        try {
            //griglia
            out.print("<div class=\"col-md-auto\"><div class=\"tab-content\" id=\"nav-tabContent\">");

/*
            //header
            out.print("<div class=\"container title\"><div class=\"row justify-content-between\">");

            //titolo
            out.print("<div class=\"col-md-auto title-col\"><h3>" + nome + "</h3></div>");

            //bottone aggiorna 1
            out.print("<div class=\"col-md-auto title-col\"><button type=\"button\" class=\"btn btn-primary\">Aggiorna</button></div>");

            //chiusura header
            out.print("</div></div>");
*/

            //tabella
            mostraTabella(out, colonne, query);

            //bottone aggiorna 2
            out.print("<button type=\"button\" class=\"btn btn-block btn-primary\">Aggiorna</button>");

            //chiusura griglia
            out.print("</div></div>");
        } catch(Exception e) {
            //Error !
        }
    }
%>
<!-- Mostra tabella -->
<%!
    public void mostraTabella(JspWriter out, String[][] colonne, String query) {
        try {
            //tabella
            out.print("<table class=\"table\">");

            //head
            out.print("<thead class=\"thead-dark\"><tr>");

            for(int i = 0; i < colonne[1].length; i++) {
                out.print("<th scope=\"col\">" + colonne[1][i] + "</th>");
            }

            //chiusura head
            out.print("</tr></thead>");

            //contenuto
            mostraContenutoTabella(out, colonne, query);

            //chiusura tabella
            out.print("</table>");
        } catch(Exception e) {
            //Error !
        }
    }
%>
<!-- Mostra contenuto tabella -->
<%!
    public void mostraContenutoTabella(JspWriter out, String[][] colonne, String query) {
        try {
            //contatto il database
            String url = "jdbc:mysql://217.61.60.117/AlbertoNidasio"; 
            Connection connection = DriverManager.getConnection(url, "AlbertoNidasio", "Alberto-07");

            //eseguo la query
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            //corpo tabella
            out.print("<tbody>");

            //controllo il risultato
            while(result.next()) {
                //riga tabella
                out.print("<tr>");

                for(int i = 0; i < colonne[0].length; i++) {
                    out.print("<td>" + result.getString(colonne[0][i]) + "</td>");
                }

                //chiusura riga tabella
                out.print("</tr>");
            }

            //chisura corpo tabella
            out.print("</tbody>");
        } catch(Exception e) {
            //Error !
        }
    }
%>
<!-- Dati -->
<%
    //nomi tabelle
    String[] nomiTabelle = { "Utenti", "Conti correnti", "Operazioni", "Tipi operazioni" };

    //colonne
    String[][][] nomiColonne = {
        {
            { "id", "nome", "cognome", "livello", "password" },
            { "#", "Nome", "Cognome", "Livello", "Password" },
        },
        {
            { "idContoCorrente", "idUtente", "bilancio", "descrizione" },
            { "#", "Utente", "Bilancio", "Descrizione" },
        },
        {
            { "idOperazione", "idContoCorrente", "data", "importo", "causale", "idTipoOperazione" },
            { "#", "Conto corrente", "Data", "Importo", "Causale", "Tipo operazione" },
        },
        {
            { "id", "nome" },
            { "#", "Nome" },
        }
    };

    //query
    String[] query = {
        "SELECT * FROM utenti;",
        "SELECT * FROM contiCorrenti;",
        "SELECT * FROM operazioni;",
        "SELECT * FROM tipiOperazioni;"
    };

    //controllo su quale pagina siamo
    int pagina = 0;
    try {
        pagina = Integer.parseInt(request.getParameter("table"));
    } catch(Exception e) {
        //la pagina è già a 0 per default
    }
%>
<html>
    <head>
        <title>Editor database</title>

        <meta charset='utf-8'>

        <!-- CSS Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">

        <!-- CSS -->
        <link rel="stylesheet" href="main.css">
    </head>
    <body>
        <!-- Pagina -->
        <div class="container main-content">
            <% mostraTitolo(out, nomiTabelle[pagina]); %>
            <div class="row justify-content-center">
                <% mostraTabelle(out, nomiTabelle); %>
                <% mostraInterfaccia(out, nomiColonne[pagina], query[pagina]); %>
            </div>
        </div>


        <!-- Libreire bootstrap -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    </body>
</html>