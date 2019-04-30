import express = require('express')
import cors = require('cors')
import { Database } from './utils/database'

//preparo express
const app: express.Application = express()
app.use(express.json())
app.use(cors({origin: '*'}))

//preparo il database
const database = new Database({
    host     : '217.61.60.117',
    user     : 'AlbertoNidasio',
    password : 'Alberto-07',
    database : 'AndreaMandonico',
    dateStrings: true
})

//rendo disponibile la pagina web nella root dell'url
app.use('/', express.static('website'))

app.get('/api/reparti', async (req, res) => {
    try {
        return res.send(await database.ottieniReparti())
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

app.get('/api/libri', async (req, res) => {
    try {
        return res.send(await database.ottieniLibri(req.query.reparto))
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

app.get('/api/statistiche', async (req, res) => {
    try {
        return res.send(await database.ottieniStatistiche(req.query.reparto))
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

app.post('/api/ordini', async (req, res) => {
    try {
        return res.send(await database.ottieniOrdini())
    } catch(errore) {
        return res.status(500).send(errore.message)
    }
})

//apro il server sulla porta 80
app.listen(80, () => console.log('Server in ascolto sulla porta 80'))