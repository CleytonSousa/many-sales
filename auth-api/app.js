import express from "express";

const app = express();
const env = process.env;

const PORT = env.PORT || 8080;

app.get("/check", (req, res) => {
    res.status(200).json({
        service: "auth-api",
        status: "up",
    })
})

app.listen(PORT, () => {
    console.warn("Server online on " + PORT)
})