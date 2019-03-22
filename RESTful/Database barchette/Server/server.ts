import express = require('express')
import cors = require('cors')

import { Database } from './utils/database'
import { Barchetta, Sensore } from './utils/tipi'
import { controlloArrayIdSensori, controlloRecord, controlloBarchetta, controlloSensore } from './utils/controllo_dati'

//preparo il database
const database = new Database({
    host     : '217.61.60.117',
    user     : 'AlbertoNidasio',
    password : 'Alberto-07',
    database : 'DatabaseBarchette',
    dateStrings: true
})

//preparo express
const app = express()
app.use(express.json())
app.use(cors({origin: '*'}))

//rendo disponibile la pagina web nella root dell'url
app.use('/', express.static('website'))

//SEZIONE RECORD

//lista record con i dati dei sensori
app.get('/api/record', async (req, res) => {
    console.log(req.method, req.url, req.query, req.body)

    try {
        return res.send(await database.ottieniRecord(req.query.limite))
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

//creazione di un record
app.post('/api/record', async (req, res) => {
    console.log(req.method, req.url, req.query, req.body)

    let dati = req.body

    //controlli i dati
    if(controlloRecord(dati)) try {
        //salvo i dati
        await database.salvaRecord(dati)

        return res.sendStatus(200)
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
    else return res.status(400).send('Dati inseriti errati')
})

//SEZIONE BARCHETTE

//lista barchette
app.get('/api/barchette', async (req, res) => {
    console.log(req.method, req.url, req.body)

    try {
        return res.send(await database.ottieniBarchette())
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

//creazione di una barchetta con sensori
app.post('/api/barchette', async (req, res) => {
    console.log(req.method, req.url, req.query, req.body)

    let dati: {
        barchetta: Barchetta,
        sensori: string[]
    } = req.body

    //controlli i dati
    if(controlloBarchetta(dati.barchetta) && controlloArrayIdSensori(dati.sensori)) try {
        //creo la barchetta
        await database.salvaBarchetta(dati.barchetta, dati.sensori)

        return res.sendStatus(200)
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
    else return res.status(400).send('Dati inseriti errati')
})

//SEZIONE SENSORI

//lista sensori
app.get('/api/sensori', async (req, res) => {
    console.log(req.method, req.url, req.body)

    try {
        return res.send(await database.ottieniSensori())
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

//creazione di un sensore
app.post('/api/sensori', async (req, res) => {
    console.log(req.method, req.url, req.query, req.body)

    let dati: Sensore = req.body

    //controlli i dati
    if(controlloSensore(dati)) try {
        //creo la barchetta
        await database.salvaSensore(dati)

        return res.sendStatus(200)
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
    else return res.status(400).send('Dati inseriti errati')
})

//apro il server sulla porta 80
app.listen(80, () => console.log('Server in ascolto sulla porta 80'))