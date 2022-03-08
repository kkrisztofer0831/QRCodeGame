const http = require('http')
const fs = require('fs')
const port = 3000

const server = http.createServer(function(req, res){
    res.writeHead(200, {'Content-type': 'text/html'})
    fs.readFile('GameWebsite.htm', function(error, data){
        if(error){
            res.writeHead(404)
            res.writeHead('Error: File not found')
        } else{
            res.write(data) // All the info of inside our .html file
        }
        res.end()
    })    
})

server.listen(port, function(error){
    if(error){
        console.log("Something went wrong!", error)
    } else{
        console.log("Server is listening at port " + port)
    }
})