import bcrypt from "bcrypt";
import {User} from "../../modules/user/model/User.js";

export async function createInitialData(){
    await User.sync({force: true});

    let password = await bcrypt.hash("123456", 10)

    let firstUser = User.create({
        name: "TESTE USER",
        email: "teste@gmail.com",
        password: password
    })

    let secondUser = User.create({
        name: "TESTE USER 2",
        email: "teste1@gmail.com",
        password: password
    })
}