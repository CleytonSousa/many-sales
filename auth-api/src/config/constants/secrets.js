import {config} from "dotenv";
config()

export const apiSecret = process.env.API_SECRET
    ? process.env.API_SECRET
    : "bGFzYW5oYS1iYXRhZnJpdGEtY29jYWNvbGE="