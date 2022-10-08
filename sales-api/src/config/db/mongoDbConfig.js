import mongoose from "mongoose";
import * as secrets from  "../secrets/secrets.js"

export const connect = () => {
    mongoose.connect(secrets.MONGO_URL, {
        useNewUrlParser: true
    });
    mongoose.connection.on("connected", () => {
        console.info("Connected with DB")
    })
    mongoose.connection.on("error", (e) => {
        console.log(e)
        console.error("cant connect with DB")
    })
}

