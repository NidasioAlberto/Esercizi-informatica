<%
    int[][] griglia = new int[][]{{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
    int i, j;
    int turno = 0;

    for(i = 0; i < 3; i++) {
        for(j = 0; j < 3; j++) {
            try {
                griglia[i][j] = Integer.parseInt(request.getParameter(i + "-" + j));
            } catch(Exception e) {
                //...
            }
        }
    }

    //controllo se qualcuno ha vinto
    int vincitore = controllaVittoria(griglia);
    int[][] grigliaVincitore = controllaGrigliaVincitore(griglia);
%>
<%!
    public int controllaVittoria(int[][] griglia) {
        //orizzontale
        for(int i = 0; i < 3; i++) {
            if(griglia[i][0] == griglia[i][1] && griglia[i][1] == griglia[i][2] && griglia[i][0] != -1) {
                return(griglia[i][0]);
            }
        }

        //verticale
        for(int i = 0; i < 3; i++) {
            if(griglia[0][i] == griglia[1][i] && griglia[1][i] == griglia[2][i] && griglia[0][i] != -1) {
                return(griglia[0][i]);
            }
        }

        //diagonale
        if(griglia[0][0] == griglia[1][1] && griglia[1][1] == griglia[2][2] && griglia[0][0] != -1) {
            return(griglia[0][0]);
        }
        if(griglia[0][2] == griglia[1][1] && griglia[1][1] == griglia[2][0] && griglia[0][2] != -1) {
            return(griglia[0][2]);
        }

        return(-1);
    }
%>

<%!
    public int[][] controllaGrigliaVincitore(int[][] griglia) {
        int[][] grigliaVincitore = new int[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                grigliaVincitore[i][j] = -1;
            }
        }

        //orizzontale
        for(int i = 0; i < 3; i++) {
            if(griglia[i][0] != -1 && griglia[i][0] == griglia[i][1] && griglia[i][1] == griglia[i][2]) {
                for(int j = 0; j < 3; j++) grigliaVincitore[i][j] = 1;
            }
        }

        //verticale
        for(int i = 0; i < 3; i++) {
            if(griglia[0][i] != -1 && griglia[0][i] == griglia[1][i] && griglia[1][i] == griglia[2][i]) {
                for(int j = 0; j < 3; j++) grigliaVincitore[j][i] = 1;
            }
        }

        //diagonale
        if(griglia[0][0] != -1 && griglia[0][0] == griglia[1][1] && griglia[1][1] == griglia[2][2]) {
            for(int j = 0; j < 3; j++) grigliaVincitore[j][j] = 1;
        }
        if(griglia[0][2] != -1 && griglia[0][2] == griglia[1][1] && griglia[1][1] == griglia[2][0]) {
            for(int j = 0; j < 3; j++) grigliaVincitore[j][2 - j] = 1;
        }

        return(grigliaVincitore);
    }
%>

<%!
    public String carattere(int i, int j, int[][] griglia) {
        if(griglia[i][j] == 0) {
            return("X");
        } else if(griglia[i][j] == 1) {
            return("O");
        } else {
            return(" ");
        }
    }
%>
<html>
    <head>
        <title>Tris</title>

        <script>
            <% out.print("var turno = " + turno); %>
            <% out.print("var griglia = [["); %>
            <%
                for(i = 0; i < 3; i++) {
                    for(j = 0; j < 3; j++) {
                        out.print(griglia[i][j] + ", ");
                    }
                    out.print("], [");
                }
            %>
            <% out.print("]]"); %>
            <% out.print("var vincitore = " + vincitore); %>
        </script>

        <script>
            console.log(griglia)

            window.onload = () => {
                if(vincitore != -1) {
                    document.getElementById("main-button").value = (vincitore == 0 ? "X" : "O") + " ha vinto !"
                }
            }

            function registraClick(i, j) {
                var url = new URL(document.URL)

                //check if there is already a sign in the clicked spot
                if(griglia[i][j] == -1 && vincitore == -1) {
                    if(url.searchParams.get("turno") != null) {
                        turno = url.searchParams.get("turno")
                    }

                    griglia[i][j] = turno

                    url.searchParams.set("turno", (turno == 0 ? 1 : 0))

                    for(let i = 0; i < 3; i++) {
                        for(let j = 0; j < 3; j++) {
                            url.searchParams.set(i + "-" + j, griglia[i][j])
                        }
                    }

                    window.location.href = url.href
                }
            }

            function mainButtonPressed() {
                window.location.href = window.location.href.split("?")[0]
            }
        </script>

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
                        <h2 class="card-title">Tris</h2>
                        <table>
                            <% for(i = 0; i < 3; i++) { %>
                                <tr>
                                    <% for(j = 0; j < 3; j++) { %>
                                        <td>
                                            <% out.print("<input class='btn btn-outline-primary bottoni-tabella' id='" + i + "-" + j + "' type='button' value='" + carattere(i, j, griglia) + "' onclick='registraClick(" + i + ", " + j + ")'"); %>
                                            <%
                                                if(grigliaVincitore[i][j] == 1) out.print(" style='background-color: red;'");
                                            %>
                                            <% out.print(">"); %>
                                        </td>
                                    <% } %>
                                </tr>
                            <% } %>
                            <tr>
                                <td colspan=3 style="height: 50px;">
                                    <input id="main-button" type="button" value="Reset game" class='btn btn-primary btn-block' onclick="mainButtonPressed()">
                                </td>
                            </tr>
                        </table>
                        <a href="Tris originale.jsp" class="btn btn-primary btn-block" target="_blank" role="button">Pagina legacy</a>
                        <a href="/" class="btn btn-primary btn-block" role="button">Torna alla home</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Libreire bootstrap -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
    </body>
</html>