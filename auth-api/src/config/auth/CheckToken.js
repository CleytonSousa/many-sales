import jwt from "jsonwebtoken";
import {promisify} from "util";

import AuthException from "./AuthException.js";

import * as secrets from "../constants/secrets.js"
import * as status from "../constants/httpStatus.js"
import * as STATUS from "../constants/httpStatus.js";

const bearer = "bearer";

export default async(req, res, next) => {
    try {
        const { authorization } = req.headers;
        if(!authorization){
            throw new AuthException(status.UNAUTHORIZED, "Access token not found")
        }
        let accesstoken = authorization;
        if(accesstoken.includes(bearer)){
            accesstoken = accesstoken.replace(bearer, "")
        }

        const decoded = await  promisify(jwt.verify)(
            accesstoken,
            secrets.apiSecret
        )
        req.authUser = decoded.userAuth;
        return next();
    } catch (err){
        return res.status(err.status || 500).json({
            status: err.status ? err.status : STATUS.INTERNAL_SERVER_ERROR,
            message: err.message
        })
    }
}