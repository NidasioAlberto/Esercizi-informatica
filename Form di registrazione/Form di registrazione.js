function registra() {
    console.log('registrando ...')

    //recuper i dati
    const nome = document.getElementById('nome').value
    const cognome = document.getElementById('cognome').value
    const indirizzo = document.getElementById('indirizzo').value
    const dataDiNascita = document.getElementById('dataDiNascita').value
    const email = document.getElementById('email').value
    const telefono = document.getElementById('telefono').value
    const sedeCampeggio = document.getElementById('sedeCampeggio').value

    console.log('dati:', nome, cognome, indirizzo, dataDiNascita, email, telefono, sedeCampeggio)

    //controllo i dati
    const cNome = /^[A-Z][a-z]+$/g.test(nome)
    const cCognome = /^[A-Z][a-z]+$/g.test(cognome)
    const cIndirizzo = /^(([A-Z]([a-z]+)) )+\d+$/g.test(indirizzo)
    const cDataDiNascita = dataDiNascita != ''
    const cEmail = /^(\w|\d|\.)+@(\w|\d|\.)+$/g.test(email)
    const cTelefono = /^\d+$/g.test(telefono)
    //const cSedeCampeggio = true //sempre vero perchè viene scelta da una select

    if(cNome && cCognome && cIndirizzo && cDataDiNascita && cEmail && cTelefono) {
        console.log('i dati sono validi')

        document.getElementById('dialogDatiSbagliati').style.display = "block"
        document.getElementById('dialogDatiCorretti').style.display = "none"
    } else {
        console.log('i dati non sono validi')

        if(!cNome) console.log('nome non valido')
        if(!cCognome) console.log('cognome non valido')
        if(!cIndirizzo) console.log('indirizzo non valido')
        if(!cDataDiNascita) console.log('data di nascita non valida')
        if(!cEmail) console.log('email non valida')
        if(!cTelefono) console.log('telefono non valido')

        document.getElementById('dialogDatiCorretti').style.display = "block"
        document.getElementById('dialogDatiSbagliati').style.display = "none"
    }
}

function nascondiAlert() {
    document.getElementById('dialogDatiCorretti').style.display = "none"
    document.getElementById('dialogDatiSbagliati').style.display = "none"
}