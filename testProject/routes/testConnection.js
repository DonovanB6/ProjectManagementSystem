const express = require('express');
var router = express.Router();
const sql = require('mssql');

const config = {
      server: '/wssu.database.windows.net',
      database: 'ProjectManagement',
      user: 'dinno6@wssu',
      password: 'Ota170#N!',
      options: {
        encrypt: true, // for Azure databases
        trustServerCertificate: true // for Azure databases
      }
    }

const pool = new sql.ConnectionPool(config);

  router.get('/student', (req, res) => {
    pool.connect((err) => {
      if (err) {
        console.error(err);
        res.status(500).send('Error connecting to database');
      } else {
        const name = req.query.name;
        console.log(name);
        const request = new sql.Request(pool);
        request.query('SELECT * FROM Project WHERE sname = ' + name, (err, result) => {
          if (err) {
            console.error(err);
            res.status(500).send('Error executing query');
          } else {
            res.render(result.recordset);
          }
          pool.close();
        });
      }
    });
  });

  router.get("/action", function(req, res, next) {
    pool.connect((err) => {
      if (err) {
        console.error(err);
        res.status(500).send('Error connecting to database');
      } else {
        const name = req.query.name;
        console.log(name);
        const request = new sql.Request(pool);
        request.query('SELECT * FROM Project', (err, result) => {
          if (err) {
            console.error(err);
            res.status(500).send('Error executing query');
          } else {
            res.send(result.recordset);
          }
          pool.close();
        });
      }
    });
  })

module.exports = router;

// app.listen(3000, () => {
//   console.log(app.path())
//   console.log('Server started on port 3000');
// });

// INSERT INTO Student (sid, sname, major, level, byear) VALUES (11, 'Donovan Benson', 'Computer Science', 'SR', 2000)