import * as STATUS from "../../../config/constants/httpStatus.js";
import {apiSecret} from "../../../config/constants/secrets.js";
import UserRepository from "../repository/UserRepository.js";
import UserException from "../exception/UserException.js"
import jwt from "jsonwebtoken"
import bcrypt from "bcrypt";

class UserService {
    async findByEmail(req){
        try{
            const { email } = req.params;
            const { authUser } = req;
            this.validRequestData(email);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            this.validateAuthenticatedUser(user, authUser);
            return {
                status: STATUS.SUCCESS,
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email
                }
            }
        } catch (err){
            return {
                status: err.status ? err.status : STATUS.INTERNAL_SERVER_ERROR,
                message: err.message
            }
        }
    }

    validRequestData(email){
        if(!email){
            throw new UserException(STATUS.BAD_REQUEST, "User email not provided!")
        }
    }

    validateUserNotFound(user){
        if(!user){
            throw new Error("User not found!", STATUS.BAD_REQUEST)
        }
    }

    validateAuthenticatedUser(user, authUser) {
        if (!authUser || user.id !== authUser.id) {
            throw new UserException(
                STATUS.FORBIDDEN,
                "You cannot see this user data."
            );
        }
    }

    async getAccessToken(req){
        try{
            const {email, password} = req.body;
            this.validRequestGetAccessToken(email, password);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            await this.validatePassword(password, user.password);
            const userAuth = {
                id: user.id,
                name: user.name,
                email: user.email
            }
            const token = jwt.sign({userAuth}, apiSecret,{expiresIn: '1d'})
            return {
                status: STATUS.SUCCESS,
                token: token
            }
        } catch (err){
            return {
                status: err.status ? err.status : STATUS.INTERNAL_SERVER_ERROR,
                message: err.message
            }
        }


    }

    validRequestGetAccessToken(email, password){
        if(!password || !email){
            throw new Error("Email or password not provided!")
        }
    }

    async validatePassword(password, hashPassword){
        if(!await bcrypt.compare(password, hashPassword)){
            throw new Error("Wrong password!")
        }
    }
}

export default new UserService();