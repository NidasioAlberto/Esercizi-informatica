function pulisci() {
    console.log('pulisco i parametri')
    var clean_uri = location.protocol + "//" + location.host + location.pathname
    window.history.replaceState({}, document.title, clean_uri)
    location.reload()
}