var sezioneCorrente = 0 // 0 per record, 1 per barchette e 2 per sensori
var record

window.addEventListener('load', () => {
    $('#home').on('click', (e) => {
        if(sezioneCorrente != 0) {
            sezioneCorrente = 0
            console.log('home')
        }
    })

    $('#nuova-barchetta').on('click', (e) => {
        if(sezioneCorrente != 1) {
            sezioneCorrente = 1
            console.log('nuova-barchetta')

            caricaSensori()
        }
    })

    $('#nuovo-sensore').on('click', (e) => {
        if(sezioneCorrente != 2) {
            sezioneCorrente = 2
            console.log('nuovo-record')
        }
    })

    $('#lista-barchette').on('click', (e) => {
        if(sezioneCorrente != 3) {
            sezioneCorrente = 3
            console.log('lista-barchette')

            caricaBarchette()
        }
    })

    $('#lista-record').on('click', (e) => {
        if(sezioneCorrente != 4) {
            sezioneCorrente = 4
            console.log('lista-record')

            caricaRecord()
        }
    })
})

//funzione per caricare le barchette nella tabella
function caricaBarchette() {
    const http = new XMLHttpRequest()
    const url = 'http://localhost/api/barchette'
    http.open('GET', url)
    http.onload = (e) => {
        var data = JSON.parse(http.responseText)
        $('#tabella-barchette').bootstrapTable({
            columns: [{
                    field:'id',
                    title: '#'
                }, {
                    field:'nome',
                    title: 'Nome'
                }, {
                    field:'versioneDesign',
                    title: 'V. Design'
                }, {
                    field:'alimentazioneFotovoltaica',
                    title: 'Fotovoltaico'
                }, {
                    field:'note',
                    title: 'Note'
                }],
            data
        })
    }
    http.send()
}

//funzione per caricare i record nella tabella
function caricaRecord() {
    //retrive the data from the database
    const http = new XMLHttpRequest()
    const url = 'http://localhost/api/record'
    http.open('GET', url)
    
    http.onload = (e) => {
        record = JSON.parse(http.responseText)
        $('#tabella-record').bootstrapTable({
            columns: [{
                    field:'id',
                    title: '#'
                }, {
                    field:'latitudine',
                    title: 'Latitudine'
                }, {
                    field:'longitudine',
                    title: 'Longitudine'
                }, {
                    field:'dateTime',
                    title: 'Data e ora'
                }, {
                    field:'altitudine',
                    title: 'Altitudine'
                }, {
                    field:'satelliti',
                    title: 'N. satelliti'
                }, {
                    field:'hdop',
                    title: 'HDOP'
                }],
            data: record
        })

        $("#tabella-record").find('tr').click( function(){
            var row = parseInt($(this).find('td:first').text())
            let recordAttuale = record.find(recordSingolo => recordSingolo.id == row)
            let messaggio = recordAttuale.datiSensori.map(dato => dato.nomeSensore + ': ' + dato.valore).join('\n')
            
            alert(messaggio);
        });
    }
    http.send()
}

//funzione per caricare i sensori nella creazione di una nuova barchetta
function caricaSensori() {
    const http = new XMLHttpRequest()
    const url = 'http://localhost/api/sensori'
    http.open('GET', url)

    http.onload = (e) => {
        var data = JSON.parse(http.responseText)

        console.log(data)
        var selezione = document.getElementById('barchetta-sensori')
        selezione.innerHTML = ''
        
        data.forEach(sensore => {
            var nuovaOpzione = document.createElement('option')
            nuovaOpzione.innerHTML = sensore['nome'] + ': ' + sensore['note']
            nuovaOpzione.value = sensore.id
            selezione.options.add(nuovaOpzione)
        })
    }
    http.send()	
}

//funzione per aggiungere una nuova barchetta
function aggiungiBarchetta() {
    let id = document.getElementById('barchetta-id').value
    let nome = document.getElementById('barchetta-nome').value
    let versioneDesign = parseInt(document.getElementById('barchetta-versione').value)
    let alimentazioneFotovoltaica = (document.getElementById('barchetta-fotovoltaico').value == 'true')
    let note = document.getElementById('barchetta-note').value
    let sensori = $('#barchetta-sensori').val()

    //controllo i dati
    if(id == '' || nome == '' || versioneDesign == undefined || note == '' || !(sensori instanceof Array)) {
        //mostro un avviso all'utente
        alert('Compilare tutti i campi!')
    } else {
        const http = new XMLHttpRequest()
        const url = 'http://localhost/api/barchette'
        http.open('POST', url)
        http.setRequestHeader('Content-Type', 'application/json')

        var barchetta = { barchetta: { id, nome, versioneDesign, alimentazioneFotovoltaica, note }, sensori }

        http.onload = (e) => {
            document.getElementById('barchetta-id').value = ''
            document.getElementById('barchetta-nome').value = ''
            document.getElementById('barchetta-versione').value = 0
            document.getElementById('barchetta-fotovoltaico').value = 'false'
            document.getElementById('barchetta-note').value = ''
        }
        
        http.send(JSON.stringify(barchetta))
    }	
}

//funzione per registrare un nuovo sensore
function aggiungiSensore() {
    let id = document.getElementById('sensore-id').value
    let nome = document.getElementById('sensore-nome').value
    let note = document.getElementById('sensore-nota').value
    let unita = document.getElementById('sensore-unita').value

    //controllo i dati
    if(id == '' || nome == '' || note == '' || unita == '') {
        //mostro un avviso all'utente
        alert('Compilare tutti i campi!')
    } else{
        const http = new XMLHttpRequest()
        const url = 'http://localhost/api/sensori'
        http.open('POST', url)
        http.setRequestHeader('Content-Type', 'application/json')

        var sensore = { id, nome, note, unita }

        http.onload = (e) => {
            document.getElementById('sensore-id').value = ''
            document.getElementById('sensore-nome').value = ''
            document.getElementById('sensore-nota').value = ''
            document.getElementById('sensore-unita').value = ''
        }

        http.send(JSON.stringify(sensore))
    }	
}