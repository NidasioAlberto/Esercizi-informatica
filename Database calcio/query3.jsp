<%@ page language="Java" %>
<%@ page import="java.sql.*"%>
<%
    Class.forName("com.mysql.jdbc.Driver");
%>
<!-- Mostra tabella -->
<%!
    public void mostraTabella(JspWriter out, String[][] colonne, String query) {
        try {
            //tabella
            out.print("<table class=\"table zero-margin-bottom\">");

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
            String url = "jdbc:mysql://217.61.60.117/Database calciatori"; 
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
<!-- Motra opzioni -->
<%!
    public void mostraOpzioni(JspWriter out, String query, String name, String messaggio, String selezione) {
        try {
            //contatto il database
            String url = "jdbc:mysql://217.61.60.117/Database calciatori"; 
            Connection connection = DriverManager.getConnection(url, "AlbertoNidasio", "Alberto-07");

            //eseguo la query
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            //apertura select
            out.print("<select class=\"custom-select\" name=\"" + name + "\" required>");

            //messaggio
            if(selezione == null) {
                out.print("<option value=\"\" selected>" + messaggio + "</option>");
            } else {
                out.print("<option value=\"\">" + messaggio + "</option>");
            }

            //controllo il risultato
            while(result.next()) {
                if(result.getString(1).equals(selezione)) {
                    out.print("<option value=\"" + result.getString(1) + "\" selected>" + result.getString(1) + "</option>");
                } else {
                    out.print("<option value=\"" + result.getString(1) + "\">" + result.getString(1) + "</option>");
                }
            }

            //chiusura select
            out.print("</select>");
        } catch(Exception e) {
            //Errore !
        }
    }
%>
<!-- Main -->
<%
    String query;

    String colonne[][] = {
        { "id", "nome", "cognome", "ruolo", "eta", "nazionalita" },
        { "#", "Nome", "Cognome", "Ruolo", "Et&agrave;", "Nazionalit&agrave;" }
    };

    String ruolo = null;
    String eta = null;

    String queryRuoli = "select distinct A.ruolo from calciatori A;";
    String querySquadre = "select A.nome from squadre A";

    //provo a recuperare i dati
    try {
        ruolo = request.getParameter("ruolo");
        eta = request.getParameter("eta");
    } catch(Exception e) {
        //Errore !
    }

    //imposto la query
    if(ruolo != null && eta != null) {
        query = "select * from calciatori where ruolo like '" + ruolo + "' and eta < " + eta + ";";
    } else {
        query = "select * from calciatori;";
    }
%>
<html>
    <head>
        <!-- Global site tag (gtag.js) - Google Analytics -->
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-108263321-3"></script>
        <script>
            window.dataLayer = window.dataLayer || [];
            function gtag(){dataLayer.push(arguments);}
            gtag('js', new Date());

            gtag('config', 'UA-108263321-3');
        </script>
        
        <title>Giocatori dato ruolo e squadra</title>

        <!-- CSS Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    
        <!-- CSS -->
        <link rel="stylesheet" href="Database calcio.css">
    </head>
    <body>
        <div class="container main-content">
            <div class="row justify-content-center">
                <div class="card">
                    <div class="card-body">
                        <h3 class="card-title">Giocatori dato ruolo ed et&agrave;</h3>
                        <h6 class="card-subtitle mb-2 text-muted">Dato ruolo ed et&agrave; mostra chi ha giocato in quel ruolo ed &egrave; pi&ugrave; giovane dell'et&agrave;</h6>
                        <form action="query3.jsp" method="GET" class="zero-margin-bottom">
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text" style="width: 100px">Ruolo</div>
                                </div>
                                <% mostraOpzioni(out, queryRuoli, "ruolo", "Selezione un ruolo", ruolo); %>
                            </div><div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text" style="width: 100px">Et&agrave;</div>
                                </div>
                                <%
                                    if(eta != null) {
                                        out.print("<input type=\"number\" class=\"form-control\" name=\"eta\" placeholder=\"Et&agrave;\" value=\"" + eta + "\"required>");
                                    } else {
                                        out.print("<input type=\"number\" class=\"form-control\" name=\"eta\" placeholder=\"Et&agrave;\" required>");
                                    }
                                %>
                            </div>
                            <div class="container">
                                <div class="row">
                                    <div class="col" style="padding-left: 0; padding-right: 4px;">
                                        <button type="button" class="btn btn-primary mb-2 btn-block" onclick="pulisci()">Pulisci</button>
                                    </div>
                                    <div class="col" style="padding-right: 0; padding-left: 4px;">
                                        <button type="submit" class="btn btn-primary mb-2 btn-block">Invia</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <% mostraTabella(out, colonne, query); %>
                        <div class="container main-content zero-margin">
                            <div class="row">
                                <div class="col" style="padding-left: 0; padding-right: 4px;">
                                    <a href="index.html" class="btn btn-primary btn-block" role="button" style="margin-top: 8px;">Torna indietro</a>  
                                </div>
                                <div class="col" style="padding-right: 0; padding-left: 4px;">
                                    <a href="/" class="btn btn-primary btn-block" role="button" style="margin-top: 8px;">Torna alla home</a>                        
                                </div>
                            </div>
                        </div>
                    </div>
                </div>                  
            </div>
        </div>

        <!-- codice js -->
        <script src="main.js"></script>

        <!-- Libreire bootstrap -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    </body>
</html>