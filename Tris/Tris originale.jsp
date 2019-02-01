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
        <title>Tris</title>
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

        <style>
            td, input {
                width: 100px;
                height: 100px;
                border: 1px solid;
                text-align: center;
                background-color: yellow;
                font-size: 80px;
            }

            input {
                border: 0px;
            }
        </style>

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
    </head>
    <body>
        <div style="display: flex; justify-content: center; padding-top: 100px;">
            <table   style="border: 1px solid; background-color: green;">
                <% for(i = 0; i < 3; i++) { %>
                    <tr>
                        <% for(j = 0; j < 3; j++) { %>
                            <td>
                                <% out.print("<input id='" + i + "-" + j + "' type='button' value='" + carattere(i, j, griglia) + "' onclick='registraClick(" + i + ", " + j + ")'"); %>
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
                        <input id="main-button" type="button" value="Reset game" style="width: 100%; height: 100%; background-color: yellow; font-size: 30px;" onclick="mainButtonPressed()">
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>