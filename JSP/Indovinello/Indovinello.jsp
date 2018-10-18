<%
    int random, numero;

    //recupero o creo il numero casuale
    try {
        //controllo se l'utente vuole cambiar eil numero casuale
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
        </style>
    </head>
    <body>
        <form action="Indovinello.jsp" method="GET">
            <input type="number" name="numero" value="<%
                //se l'utente ha inserito il numero nella pagina precedete lo reinserisco
                if(numero != -1) out.print(numero);
            %>">
            <input type="checkbox" name="reset">
            <input type="submit" value="<%
                //imposto il messaggio del bottone in base al numero inserito nella pagina precendete
                if(numero == -1) out.print("Controlla !");
                else if(numero == random) out.print("Hai indovinato !");
                else if(Math.abs(numero - random) < 10) out.print("Ci sei vicino !");
                else out.print("Non ci sei proprio, riprova !");
            %>" class="submit">
        </form>
        <% if(numero == -1) { %>
            <p>
                Benvenuto, prova a indovinare il numero segreto !
                <br>
                Spunta la casella se vuoi cambiare numero segreto
            </p>
        <% } %>
    </body>
</html>