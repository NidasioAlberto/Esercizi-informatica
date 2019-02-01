<%@ page language="Java" %>
<%@ page import="java.sql.*"%>
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
            String url = "jdbc:mysql://localhost:3306/Database calciatori"; 
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
<!-- Main -->
<%
    String query;

    String colonne[][] = {
        { "id", "squadra", "nome", "cognome", "dataInizio", "dataFine", "numeroMaglia", "titolare" },
        { "#", "Squadra", "Nome", "Cognome", "Data inizio", "Data fine", "Numero maglia", "Titolare" }
    };

    String nome = null;
    String cognome = null;

    //provo a recuperare i dati
    try {
        nome = request.getParameter("nome");
        cognome = request.getParameter("cognome");
    } catch(Exception e) {
        //Errore !
    }

    //imposto la query
    if(nome != null && cognome != null) {
        query = "select A.id, C.nome squadra, B.nome nome, B.cognome cognome, A.dataInizio, A.dataFine, A.numeroMaglia, case when A.titolare = 0 then 'No' else 'Si' end titolare from carriere A join calciatori B on A.idCalciatore = B.id join squadre C on A.idSquadra = C.id where B.nome like '" + nome + "' and B.cognome like '" + cognome + "' order by A.dataInizio;";
    } else {
        query = "select A.id, C.nome squadra, B.nome nome, B.cognome cognome, A.dataInizio, A.dataFine, A.numeroMaglia, case when A.titolare = 0 then 'No' else 'Si' end titolare from carriere A join calciatori B on A.idCalciatore = B.id join squadre C on A.idSquadra = C.id order by A.dataInizio;";
    }
%>
<html>
    <head>
        <title>Carriere di un giocatore</title>

        <!-- CSS Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    
        <!-- CSS -->
        <link rel="stylesheet" href="main.css">
    </head>
    <body>
        <div class="container main-content">
            <div class="row justify-content-center">
                <div class="card">
                    <div class="card-body">
                        <h3 class="card-title">Carriere di un giocatore</h3>
                        <h6 class="card-subtitle mb-2 text-muted">Dato nome e cognome di un calciatore mostra le carrire in ordine cronologico</h6>
                        <form action="query1.jsp" method="GET" class="zero-margin-bottom">
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text" style="width: 100px">Nome</div>
                                </div>
                                <%
                                    if(nome != null) {
                                        out.print("<input type=\"text\" class=\"form-control\" name=\"nome\" placeholder=\"Nome\" value=\"" + nome + "\"required>");
                                    } else {
                                        out.print("<input type=\"text\" class=\"form-control\" name=\"nome\" placeholder=\"Nome\" required>");
                                    }
                                %>
                            </div><div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text" style="width: 100px">Cognome</div>
                                </div>
                                <%
                                    if(cognome != null) {
                                        out.print("<input type=\"text\" class=\"form-control\" name=\"cognome\" placeholder=\"Cognome\" value=\"" + cognome + "\"required>");
                                    } else {
                                        out.print("<input type=\"text\" class=\"form-control\" name=\"cognome\" placeholder=\"Cognome\" required>");
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