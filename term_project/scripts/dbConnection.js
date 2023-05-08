
const sql = require('mssql');

const config = {
    server: '/wssu.database.windows.net',
    database: 'ProjectManagement',
    user: 'dinno6@wssu',
    password: 'Ota170#N!',
    port: 1433,
    encrypt: true
};

sql.connect(config, function (err) {
if (err) console.log(err);
else console.log('Connected to Azure database');
})


