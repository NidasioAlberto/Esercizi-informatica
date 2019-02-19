var express = require('express')
var app = express()

var dispositivi = [
    {
        id: 1,
        nome: "Telefono di Andrea"
    },
    {
        id: 2,
        nome: "Telefono di Alberto"
    },
    {
        id: 3,
        nome: "Telefono di Lorenzor"
    }
]

//richiesta per ottenere la lista di dispositivi
app.get('/dispositivi', function (req, res) {
    res.send(dispositivi)
})

//richiesta per ottenere i dati di un singolo dispositivo dato l'id
app.get('/dispositivi/:id', function (req, res) {
    //controllo i paramteri
    if(req.params.id > 0 && req.params.id < dispositivi.length) {
        res.send(dispositivi[req.params.id])
    } else {
        //rispondo con un errore
        res.status(400)
        res.end()
    }
})

var server = app.listen(8081, function () {
    var host = server.address().address
    var port = server.address().port
    
    console.log("Servizio disponibile sulla porta", port)
})