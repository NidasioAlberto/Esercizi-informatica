window.onload = () => {
    const request = new XMLHttpRequest()

    request.onload = (event) => {
        var data = JSON.parse(request.responseText)

        mostraContenuti(data)
    }

    request.open('GET', 'url')
    request.send()
}

