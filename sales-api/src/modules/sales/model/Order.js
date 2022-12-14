import mongoose from "mongoose";

const Schema = mongoose.Schema;
const model = mongoose.model;

const OrderSchema = new Schema({
    products: {
        type: Array,
        required: true
    },
    user: {
        type: Object,
        required: true
    },
    status: {
        type: String,
        required: true
    },
    created_at:{
        type: Date,
        required: true
    },
    updated_at:{
        type: Date,
        required: true
    }
})

export default model("Order", OrderSchema)