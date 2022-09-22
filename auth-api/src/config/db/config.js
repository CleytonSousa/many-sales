import {Sequelize} from "sequelize";

const sequelize = new Sequelize("auth-db", "admin", "123456", {
    host: "localhost",
    dialect: "postgres",
    quoteIdentifiers: false,
    port: 5432,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        freezeTableName: true
    }
});

sequelize.authenticate()
    .then(() => console.log("Connection successful!"))
    .catch((err) => {
        console.error("Connection fail :C");
        console.error(err.message);
    })

export default sequelize;