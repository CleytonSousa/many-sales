import Order from "../../modules/sales/model/Order.js";

export const createInitialData = async() => {
    try{
        let existData = await Order.find();
        if(existData && existData.length > 0){
            console.info("Dropping data in Order schema!")
            await Order.collection.drop();
        }
        await Order.create({
            products: [
                {productId: 1001, quantity: 2},
                {productId: 1002, quantity: 1},
                {productId: 1003, quantity: 1},
            ],
            user: {
                id: "27n9g8po",
                name: "Teste user",
                email: "teste@gmail.com"
            },
            status: 'APPROVED',
            created_at: new Date(),
            updated_at: new Date()
        })

        await Order.create({
            products: [
                {productId: 1001, quantity: 2},
                {productId: 1003, quantity: 1},
            ],
            user: {
                id: "wmi7ed8yw9",
                name: "Teste user 2",
                email: "teste2@gmail.com"
            },
            status: 'REJECTED',
            created_at: new Date(),
            updated_at: new Date()
        })
        console.log("Ceated initial data");
        console.log(JSON.stringify(await Order.find(), undefined, 4))
    } catch (err){
        console.error("Erro createInitialData", err)
    }
}