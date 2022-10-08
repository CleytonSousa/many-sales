import {config} from "dotenv";
config();
const env = process.env;

export const MONGO_URL = env.MONGO_URL ? env.MONGO_URL : "mongodb://admin:123456@localhost:27017";
export const apiSecret = process.env.API_SECRET
    ? process.env.API_SECRET
    : "bGFzYW5oYS1iYXRhZnJpdGEtY29jYWNvbGE="