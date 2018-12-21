//callback bottone aggiungi utente
function aggiungiUtente() {
    console.log('wiowowow')
    location.href = "aggiungi utente.jsp"
}

//callback bottone visualizza utenti
function visualizzaUtenti() {
    location.href = "lista.jsp"
}

//bottone trona alla pagina home
function tornaAllaHome() {
    location.href = "home.jsp"
}

//callback bottone logout
function logout() {
    //rimuovo tutti i cookie e vado alla pagina di login
    //deleteAllCookies() non funziona :(

    location.href = "login.jsp"
}