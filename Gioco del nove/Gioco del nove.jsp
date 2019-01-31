<%
    Integer[] posizioni = new Integer[9];
    boolean vittoria = false;

    //controllo se nella sessione ho salvato le posizioni
    try {
        Integer[] test = (Integer[]) session.getAttribute("posizioni");
        if(test == null) throw new Exception();
        else {
            posizioni = test;
        }
    } catch(Exception e) {
        generaPosizioni(posizioni);
        //altrimenti non le ho ancora salvate
        session.setAttribute("posizioni", posizioni);
    }
    

    //controllo se l'utente ha schiacciato un bottone
    try {
        int posizione = Integer.parseInt(request.getParameter("bottone"));

        gestisciBottone(posizione, posizioni);
    } catch(Exception e) {
        //parametro non presente
    }

    //controlliamo se ha vinto
    vittoria = controllaAllineamento(posizioni);
%>
<%! void generaPosizioni(Integer posizioni[]) {
    posizioni[0] = 1;
    posizioni[1] = 2;
    posizioni[2] = 3;
    posizioni[3] = 4;
    posizioni[4] = 5;
    posizioni[5] = 6;
    posizioni[6] = 7;
    posizioni[7] = -1;
    posizioni[8] = 8;
} %>
<%! void gestisciBottone(int posizione, Integer posizioni[]) {
    //controllo se nelle vicinanze c'è la cella vuota in cui spostare il numero selezionato altrimenti non possiamo fare niente
    boolean mossaValida = false;

    if(posizione == 0) {
        //controllo la cella a destra e quella sotto
        mossaValida = posizioni[1] == -1 || posizioni[3] == -1;
    } else if(posizione == 1) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[0] == -1 || posizioni[2] == -1 || posizioni[4] == -1;
    } else if(posizione == 2) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[1] == -1 || posizioni[5] == -1;
    } else if(posizione == 3) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[0] == -1 || posizioni[4] == -1 || posizioni[6] == -1;
    } else if(posizione == 4) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[1] == -1 || posizioni[3] == -1 || posizioni[5] == -1 || posizioni[7] == -1;
    } else if(posizione == 5) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[2] == -1 || posizioni[4] == -1 || posizioni[8] == -1;
    } else if(posizione == 6) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[3] == -1 || posizioni[7] == -1;
    } else if(posizione == 7) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[4] == -1 || posizioni[6] == -1 || posizioni[8] == -1;
    } else if(posizione == 8) {
        //controllo nelle cella a fianco e in quella sotto
        mossaValida = posizioni[5] == -1 || posizioni[7] == -1;
    }

    //se la mossa è valida scambio le posizioni
    if(mossaValida) {
        if(posizione == 0) {
            if(posizioni[1] == -1) {
                //scambio 0 con 1
                posizioni[1] = posizioni[0];
            } else if(posizioni[3] == -1) {
                //scambio 0 con 3
                posizioni[3] = posizioni[0];
            }
            posizioni[0] = -1;
        } else if(posizione == 1) {
            if(posizioni[0] == -1) {
                //scambio 1 con 0
                posizioni[0] = posizioni[1];
            } else if(posizioni[2] == -1) {
                //scambio 1 con 2
                posizioni[2] = posizioni[1];
            } else if(posizioni[4] == -1) {
                //scambio 1 con 4
                posizioni[4] = posizioni[1];
            }
            posizioni[1] = -1;
        } else if(posizione == 2) {
            if(posizioni[1] == -1) {
                //scambio 2 con 1
                posizioni[1] = posizioni[2];
            } else if(posizioni[5] == -1) {
                //scambio 1 con 5
                posizioni[5] = posizioni[2];
            }
            posizioni[2] = -1;
        } else if(posizione == 3) {
            if(posizioni[0] == -1) {
                //scambio 3 con 0
                posizioni[0] = posizioni[3];
            } else if(posizioni[4] == -1) {
                //scambio 3 con 4
                posizioni[4] = posizioni[3];
            } else if(posizioni[6] == -1) {
                //scambio 3 con 6
                posizioni[6] = posizioni[3];
            }
            posizioni[3] = -1;
        } else if(posizione == 4) {
            if(posizioni[1] == -1) {
                //scambio 4 con 1
                posizioni[1] = posizioni[4];
            } else if(posizioni[3] == -1) {
                //scambio 4 con 3
                posizioni[3] = posizioni[4];
            } else if(posizioni[5] == -1) {
                //scambio 4 con 5
                posizioni[5] = posizioni[4];
            } else if(posizioni[7] == -1) {
                //scambio 4 con 7
                posizioni[7] = posizioni[4];
            }
            posizioni[4] = -1;
        } else if(posizione == 5) {
            if(posizioni[2] == -1) {
                //scambio 5 con 2
                posizioni[2] = posizioni[5];
            } else if(posizioni[4] == -1) {
                //scambio 5 con 4
                posizioni[4] = posizioni[5];
            } else if(posizioni[8] == -1) {
                //scambio 5 con 8
                posizioni[8] = posizioni[5];
            }
            posizioni[5] = -1;
        } else if(posizione == 6) {
            if(posizioni[3] == -1) {
                //scambio 6 con 3
                posizioni[3] = posizioni[6];
            } else if(posizioni[7] == -1) {
                //scambio 6 con 7
                posizioni[7] = posizioni[6];
            }
            posizioni[6] = -1;
        } else if(posizione == 7) {
            if(posizioni[4] == -1) {
                //scambio 7 con 4
                posizioni[4] = posizioni[7];
            } else if(posizioni[6] == -1) {
                //scambio 7 con 6
                posizioni[6] = posizioni[7];
            } else if(posizioni[8] == -1) {
                //scambio 7 con 8
                posizioni[8] = posizioni[7];
            }
            posizioni[7] = -1;
        } else if(posizione == 8) {
            if(posizioni[5] == -1) {
                //scambio 5 con 5
                posizioni[5] = posizioni[8];
            } else if(posizioni[7] == -1) {
                //scambio 7 con 4
                posizioni[7] = posizioni[8];
            }
            posizioni[8] = -1;
        }
    }
} %>
<%! boolean controllaAllineamento(Integer posizioni[]) {
    return(posizioni[0] == 1 &&
        posizioni[1] == 2 &&
        posizioni[2] == 3 &&
        posizioni[3] == 4 &&
        posizioni[4] == 5 &&
        posizioni[5] == 6 &&
        posizioni[6] == 7 &&
        posizioni[7] == 8 &&
        posizioni[8] == -1);
} %>

<html>
    <head>
        <script>
            function gestisciBottone(posizione) {
                if(location.href.indexOf("bottone") > 0) {
                    location.href = location.href.substring(0, location.href.length-1) + posizione
                } else {
                    location.href += "?bottone=" + posizione
                }
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
                        <h2 class="card-title">Gioco del 9</h2>
                        <table class="tabella">
                            <% for(int i = 0; i < 3; i++) { %>
                                <tr>
                                    <% for(int j = 0; j < 3; j++) { %>
                                        <td>
                                            <button type="button" class="btn btn-outline-primary" onclick="gestisciBottone(<%= i*3 + j %>)" style="width: 100px; height: 100px;">
                                                <% out.print((posizioni[i*3 + j] != -1 ? posizioni[i*3 + j] : "")); %>
                                            </button>
                                        </td>
                                    <% } %>
                                </tr>
                            <% } %>
                        </table>
                        <% if(vittoria) { %>
                            <div class="alert alert-success messaggio-vincita" role="alert">
                                Hai vinto!
                            </div>
                        <% } %>
                        <a href="/" class="btn btn-primary btn-block bottone-torna-alla-home" role="button">Torna alla home</a> 
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