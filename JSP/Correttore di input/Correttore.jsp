<%
    String nome = request.getParameter("nome");

    //sessioni !

    session.setAttribute("nome", nome);

    String[] vettore = new String[]{"ciao", "hey"};

    session.setAttribute("vettore", vettore);
%>