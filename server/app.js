const express = require("express")
const socketio = require("socket.io")
const http = require("http")

const app = express();
const PORT = 3000 || process.env.PORT
const server = http.createServer(app)
const io = socketio(server)
// Set static folder
app.use(express.static("public"));

// Socket setup

io.on("connection", socket => {
    socket.on("create", (room) => {
        socket.join("room_" + room)
        io.to("room_" + room).emit("notification", room + " has created room with id " + room)
    })

    socket.on("join", (id, room) => {
        if (io.sockets.adapter.rooms.get('room_' + room)) {
            socket.join("room_" + room)
            io.to("room_" + room).emit("notification", id + " has join room with id " + room)
        } else {
            io.to(id).emit("notification", "No room " + room + " found")
        }
    })

    socket.on("getAllRoomExists", (data) => {
        var rooms = Array.from(io.sockets.adapter.rooms);
        console.log(rooms)
        io.emit("sendAllRoomExists", rooms)
    })

    socket.on("getSetBomb", (id, room, cordX, cordY) => {
        io.to("room_" + room).emit("sendSetBomb", id, room, cordX, cordY)
    })
    socket.on("getPlayerMove", (id, room, direction, value) => {
        io.to("room_" + room).emit("sendPlayerMove", id, room, direction, value)
    })
    socket.on("getItemDropped", (id, room, type, cordX, cordY) => {
        io.to("room_" + room).emit("sendItemDropped", id, room, type, cordX, cordY)
    })
    
})

server.listen(PORT, () => console.log(`Server running on port ${PORT}`));