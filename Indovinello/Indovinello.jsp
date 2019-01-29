<%
    int random, numero;

    //recupero o creo il numero casuale
    try {
        //controllo se l'utente vuole cambiare il numero casuale
        String reset;
        try {
            //controllo se c'è un parametro reset
            reset = request.getParameter("reset");
            //se è nullo lo imposto a false
            if(reset == null) reset = "off";
        } catch(Exception e1) {
            //in caso di errore non cambio il numero casuale
            reset = "off";
        }

        //se l'utente non vuole cambiare il numero random requpero quello precedente dalla sessione
        if(reset.equals("off")) random = (int) session.getAttribute("random");
        //altrimenti ne creo uno nuove creando un'exception che eseguirà il codice nel blocco catch
        else throw new Exception();
    } catch(Exception e) {
        //creo un numero casuale
        random = (int) (Math.random() * 100);
        session.setAttribute("random", random);
    }

    //recupero il numero inserito dall'utente se disponibile
    try {
        numero = Integer.parseInt(request.getParameter("numero"));
    } catch(Exception e) {
        //se non è possibile recuperarlo l'utente ha appena aperto la pagina
        numero = -1;
    }
%>
<html>
    <head>
        <style>
            .submit {
                color: <%
                    //controllo i due numeri e coloro il bottone di conseguenza
                    if(numero != -1) {
                        if(numero == random) { //se combacia il colore del bottone è verde
                            out.print("green");
                        } else if(Math.abs(numero - random) < 10) { //se è vicino sarà arancio
                            out.print("orange");
                        } else { //altrimenti rosso
                            out.print("red");
                        }
                    }
                %>;
            }
            .main-content {
                margin-top: 5%;
            }

            .input-label {
                min-width: 100px;
            }

            .colonne-bottoni-form {
                padding: 0;
            }
        </style>
        
        <!-- CSS Bootstrap -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
    </head>
    <body>
        <div class="container main-content">
            <div class="row justify-content-center">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Benvenuto, prova a indovinare il numero segreto !</h5>
                        <form action="Indovinello.jsp" method="GET">
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">Numero</div>
                                </div>
                                <input type="number" class="form-control" name="numero" placeholder="Numero" value="<%
                                    //se l'utente ha inserito il numero nella pagina precedete lo reinserisco
                                    if(numero != -1) out.print(numero);
                                %>">
                            </div>
                            <div class="container">
                                <div class="row">
                                    <div class="col" style="padding-left: 0; padding-right: 4px;">
                                        <button name="reset" value="on" class="btn btn-primary mb-2 btn-block">Cambia numero</button>
                                    </div>
                                    <div class="col" style="padding-right: 0; padding-left: 4px;">
                                        <button type="submit" class="btn btn-primary mb-2 btn-block">Controlla</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <div class="alert <%
                            //imposto il colore in base al numero inserito
                            if(numero == -1) out.print("alert-secondary");
                            else if(numero == random) out.print("alert-success");
                            else if(Math.abs(numero - random) < 10) out.print("alert-warning");
                            else out.print("alert-danger");
                        %>" role="alert">
                            <%
                                //imposto il messaggio in base al numero inserito
                                if(numero == -1) out.print("Inserisci un numero e controlla se hai indovinato!");
                                else if(numero == random) out.print("Hai indovinato !");
                                else if(Math.abs(numero - random) < 10) out.print("Ci sei vicino !");
                                else out.print("Non ci sei proprio, riprova !");
                            %>
                        </div>
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