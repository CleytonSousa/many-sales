import userRoutes from "./src/modules/user/routes/UserRoutes.js";
import * as db from "./src/config/db/initialData.js"
import checkToken from "./src/config/auth/CheckToken.js"
import express from "express";
import {config} from "dotenv";

config()
const app = express();
const env = process.env;

const PORT = env.PORT || 8080;
db.createInitialData();

app.use(express.json())

app.use(userRoutes)

app.get("/check", (req, res) => {
    res.status(200).json({
        service: "auth-api",
        status: "up",
    })
})

app.listen(PORT, () => {
    console.warn("Server online on " + PORT)
})