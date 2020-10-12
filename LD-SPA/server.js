const express = require('express');
const path = require('path');
const ngApp = express();
ngApp.use(express.static(path.join(__dirname,'dist')));
ngApp.get('*', function (request, response) {
    response.sendFile(path.join(__dirname, '/dist/index.html'));
});
ngApp.listen(process.env.PORT || 8080);