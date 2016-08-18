var express = require('express');
var path = require('path');
var app = express();

var rootPath = path.normalize(__dirname);
app.use(express.static(rootPath + '/dist'));
app.get('*', function(eq, res) {
  res.sendFile(rootPath + '/dist/index.html');
});

app.listen(process.env.PORT || 80);

