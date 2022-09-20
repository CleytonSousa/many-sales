import express from "express";

const app = express();
const env = process.env;

const PORT = env.PORT || 8082;

app.get("/check", (req, res) => {
    return res.status(200).json({
        service: "sales-api",
        status: "up"
    })
})

app.listen(PORT, () => {
    console.log("Server online on port " + PORT)
})