import express from "express";
import {connect} from "./src/config/db/mongoDbConfig.js"
import {createInitialData} from "./src/config/db/initialData.js";
import checkToken from "./src/config/auth/CheckToken.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
createInitialData();
app.use(checkToken)

app.get("/check", async(req, res) => {
    return res.status(200).json({
        service: "sales-api",
        status: "up"
    })
})
app.listen(PORT, () => {
    console.log("Server online on port " + PORT)
})