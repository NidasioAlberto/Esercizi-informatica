"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const express = require("express");
const app = express();
const port = 8080;
app.get('/', (req, res) => {
    let data = {
        text: 'Ciao Andrea'
    };
    res.send(data);
});
app.post('/api/create', (req, res) => {
    console.log(req.body);
    res.send(req.body);
});
app.listen(port, () => {
    console.log(`server started at http://localhost:${port}`);
});
//# sourceMappingURL=esercizio-1.js.map