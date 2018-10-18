<html>
    <body>
        <form action="Correttore.jsp" method="POST">
            <h4> Nome </h4>
            <input type="text" name="nome">
            <input type="submit" value="invia !">
        </form>

        <%
            out.print("il tuo nome era: " + session.getAttribute("nome") + "<br>");

            String[] vettore = (String[]) session.getAttribute("vettore");

            if(vettore != null) {
                for(int i = 0; i < vettore.length; i++) {
                    out.print(vettore[i] + "<br>");
                }
            }
        %>
    </body>
</html>