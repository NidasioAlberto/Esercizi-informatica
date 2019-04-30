var datiLogin

function login() {
    //recupero i dati di login
    let nome = $('#nome').val()
    let cognome = $('#cognome').val()
    let password = $('#password').val()

    if(nome != undefined && cognome != undefined && password != undefined) {
        datiLogin = { nome, cognome, password }

        recuperaOrdini()

        /*
            //nascondo il div se il login è andato a buon fine
        } else {
            //mostro un avviso
            $('#avviso').show()
        }*/
    }

}

function recuperaOrdini() {
    let richiesta = new XMLHttpRequest();

    richiesta.open('POST', '/api/ordini')
    richiesta.setRequestHeader('Content-Type', 'application/json')

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            let ordini = JSON.parse(event.srcElement.responseText)
            console.log(ordini)
        
            $('#login').hide()
            $('#avviso').hide()

            $('#lista-ordini').bootstrapTable('destroy')
            $('#lista-ordini').bootstrapTable({
                columns: [{
                        field:'id',
                        title: '#'
                    }, {
                        field:'fattura',
                        title: 'Fattura'
                    }, {
                        field:'data',
                        title: 'Data'
                    }, {
                        field:'stato',
                        title: 'Stato'
                    },  {
                        field:'giorniMancanti',
                        title: 'Giorni mancanti'
                    }, {
                        field:'idUtentw',
                        title: 'Utente'
                    }],
                data: ordini
            })
        }
    }

    richiesta.send(JSON.stringify(datiLogin))

    return false
}