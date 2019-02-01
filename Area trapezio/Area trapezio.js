function calcola() {
    //recupero gli input
    var base1 = document.getElementById("base1")
    var base2 = document.getElementById("base2")
    var altezza = document.getElementById("altezza")
    var area = document.getElementById("area")

    //trasformo i dati in numeri
    var base1 = Number(base1.value)
    var base2 = Number(base2.value)
    var altezza = Number(altezza.value)

    //mostro nella console i dati
    console.log(base1)
    console.log(base2)
    console.log(altezza)

    //somma basi
    var tmp = base1 + base2

    //basi * altezza
    tmp = tmp * altezza

    //area
    tmp = tmp / 2

    //imposto il valore del risultato nella pagina
    area.value = tmp.toString()
}