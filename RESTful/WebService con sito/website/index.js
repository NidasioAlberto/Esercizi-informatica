window.addEventListener("load", loadData);

function loadData() {
    //retrive the data from the database
    const http = new XMLHttpRequest()
    const url = 'students'
    http.open('GET', url)
    
    http.onload = (e) => {
        let data = JSON.parse(http.responseText)
        console.log(data)

        //set the table content
        $('#table').bootstrapTable({
            columns: [{
              field: 'name',
              title: 'Nome'
            }, {
              field: 'surname',
              title: 'Cognome'
            }, {
              field: 'age',
              title: 'Et&agrave;'
            }],
            data
        })
    }

    http.send()
}