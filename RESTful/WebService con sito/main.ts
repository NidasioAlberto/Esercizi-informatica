import express = require('express')
var app = express()
app.use(express.json())

var students: Student[] = []

//web apge
app.use('/', express.static('website'))

//add a student
app.post('/students', (req, res) => {
    console.log('/students POST')

    //check if the data are ok
    if(isAStudent(req.body)) {
        //grap the data from the request
        let student: Student = req.body

        //add the new student
        students.push(student)

        res.sendStatus(200)
    } else if(isArrayOfStudents(req.body.students)) {
        //retrieve the students
        let newStudents: Student[] = req.body.students

        //add the students into the array
        students = students.concat(newStudents)

        res.sendStatus(200)
    } else {
        res.sendStatus(400)
    }
})

//return all the students
app.get('/students', (req, res) => {
    console.log('/students GET')

    res.send(students)
})

//edit a give student
app.post('/students/:index', (req, res) => {
    console.log('/students/:index GET')

    //check the data
    if(isAStudent(req.body) && !isNaN(req.params.index)) {
        let student: Student = req.body
        let index: number = parseInt(req.params.index)

        //check if the index is valid
        if(index >= 0 || index < students.length) {
            //change the data
            students[index] = student

            res.sendStatus(200)
        } else {
            res.sendStatus(400)
        }
    } else {
        res.sendStatus(400)
    }

    console.log(req.body)
    console.log(req.params)
    res.sendStatus(200)
})

app.listen(80, () => {
    console.log('listening on port 80')
})

//types
interface Student {
    name: string,
    surname: string,
    age?: number
}

/**
 * Checks if a give object is a student
 * @param obj the object to test
 */
function isAStudent(obj: any): obj is Student {
    return obj.name && obj.surname
}

/**
 * Check if an object is an array of students
 * @param arr the array to check
 */
function isArrayOfStudents(arr: any): arr is Student[] {
    let itIs: boolean = true

    if(arr instanceof Array) {
        arr.forEach(element => {
            if(!isAStudent(element)) {
                itIs = false
                return false
            }
        })
    } else {
        itIs = false
    }

    return(itIs)
}