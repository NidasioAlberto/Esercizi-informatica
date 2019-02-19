import express = require( "express" )
const app = express()
const port = 8080

app.get('/', (req, res) => {
    let data = {
        text: 'Ciao Andrea'
    }

    res.send(data)
})

app.post('/api/create', (req, res) => {
    console.log(req)
    res.send(req.body)
})

app.listen( port, () => {
    console.log(`server started at http://localhost:${ port }`)
})