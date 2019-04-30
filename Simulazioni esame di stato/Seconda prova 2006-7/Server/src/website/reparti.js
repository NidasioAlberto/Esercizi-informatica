var repartoCorrente = undefined
var reparti

window.onload = () => {
    console.log('ciao')

    recuperaReparti()

    recuperaLibri()

    recuperaStatistiche()
}

function recuperaReparti() {
    let richiesta = new XMLHttpRequest();

    richiesta.open('GET', '/api/reparti')

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            reparti = JSON.parse(event.srcElement.responseText)
            console.log(reparti)
            for(reparto in reparti) {
                //console.log(json[reparto])
                $('#lista-reparti').append("<button type=\"button\" class=\"list-group-item list-group-item-action d-flex justify-content-between align-items-center\" onclick=\"aggiornaPagina(" + reparti[reparto].id + ")\">" + reparti[reparto].nome + (reparti[reparto].numeroLibri > 0 ? "<span class=\"badge badge-primary badge-pill\">" + reparti[reparto].numeroLibri : "") + "</span></button>");
            }
        }
    }

    richiesta.send()
}

function aggiornaPagina(idReparto) {
    repartoCorrente = idReparto
    recuperaLibri()
    recuperaStatistiche()
}

function recuperaLibri() {
    if(repartoCorrente == undefined) {
        //recupero tutti i libri
        recuperaTuttiILibri()
    } else {
        //recupero i libri di un solo reparto (QUERY 1)
        recuperaLibriReparto(repartoCorrente)
    }
}

function recuperaTuttiILibri() {
    let richiesta = new XMLHttpRequest();

    richiesta.open('GET', '/api/libri')

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            let libri = JSON.parse(event.srcElement.responseText)
            console.log(libri)

            $('#lista-libri').bootstrapTable('destroy')
            $('#lista-libri').bootstrapTable({
                columns: [{
                        field:'id',
                        title: '#'
                    }, {
                        field:'titolo',
                        title: 'Titolo'
                    }, {
                        field:'autore',
                        title: 'Autore'
                    }, {
                        field:'prezzoDiCopertina',
                        title: 'Prezzo'
                    },  {
                        field:'editore',
                        title: 'Editore'
                    }, {
                        field:'annoDiPubblicazione',
                        title: 'Anno'
                    }],
                data: libri
            })
        }
    }

    richiesta.send()
}

function recuperaLibriReparto(idReparto) {
    console.log('recuperaLibriReparto', idReparto)
    let richiesta = new XMLHttpRequest();

    richiesta.open('GET', '/api/libri?reparto=' + idReparto)

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            let libri = JSON.parse(event.srcElement.responseText)
            console.log(libri)

            $('#lista-libri').bootstrapTable('destroy')
            $('#lista-libri').bootstrapTable({
                columns: [{
                        field:'id',
                        title: '#'
                    }, {
                        field:'titolo',
                        title: 'Titolo'
                    }, {
                        field:'autore',
                        title: 'Autore'
                    }, {
                        field:'prezzoDiCopertina',
                        title: 'Prezzo'
                    }, {
                        field:'editore',
                        title: 'Editore'
                    }, {
                        field:'annoDiPubblicazione',
                        title: 'Anno'
                    }],
                data: libri
            })
        }
    }

    richiesta.send()
}

function recuperaStatistiche() {
    if(repartoCorrente == undefined) {
        //recupero tutti i libri
        recuperaStatisticheDiTutti()
    } else {
        //recupero i libri di un solo reparto (QUERY 1)
        recuperaStatisticheReparto(repartoCorrente)
    }
}

function recuperaStatisticheDiTutti() {
    let richiesta = new XMLHttpRequest();

    richiesta.open('GET', '/api/statistiche')

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            statistiche = JSON.parse(event.srcElement.responseText)
            console.log(statistiche)

            $('#statistiche').empty()
            for(index in statistiche) {
                $('#statistiche').append("<span class=\"badge badge-pill badge-primary\" style=\"margin: 4px;\">" + statistiche[index].nome + " <span class=\"badge badge-light\">" + statistiche[index].numeroLibri + "</span></span>");
            }
        }
    }

    richiesta.send()
}

function recuperaStatisticheReparto(idReparto) {
    let richiesta = new XMLHttpRequest();

    richiesta.open('GET', '/api/statistiche?reparto=' + idReparto)

    richiesta.onreadystatechange = (event) => {
        if(event.srcElement.status == 200 && event.srcElement.readyState == 4) {
            statistiche = JSON.parse(event.srcElement.responseText)
            console.log(statistiche)

            $('#statistiche').empty()
            for(index in statistiche) {
                $('#statistiche').append("<span class=\"badge badge-pill badge-primary\" style=\"margin: 4px;\">" + statistiche[index].nome + " <span class=\"badge badge-light\">" + statistiche[index].numeroLibri + "</span></span>");
            }
        }
    }

    richiesta.send()
}